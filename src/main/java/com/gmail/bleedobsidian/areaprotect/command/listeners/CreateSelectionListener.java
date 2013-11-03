/*
 * Copyright (C) 2013 Jesse Prescott <BleedObsidian@gmail.com>
 *
 * AreaProtect is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/gpl.html>.
 */

package com.gmail.bleedobsidian.areaprotect.command.listeners;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Group;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.Selection;
import com.gmail.bleedobsidian.areaprotect.configurations.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;
import com.gmail.bleedobsidian.areaprotect.managers.interfaces.SelectionListener;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

public class CreateSelectionListener implements SelectionListener {
    private AreaProtect areaProtect;
    private String regionName;

    public CreateSelectionListener(AreaProtect areaProtect, String regionName) {
        this.areaProtect = areaProtect;
        this.regionName = regionName;
    }

    public void selectionMade(Player player, Selection selection) {
        LanguageFile language = Language.getLanguageFile();

        WorldGuardPlugin worldGuard = this.areaProtect.getWorldGuard()
                .getWorldGuardPlugin();

        Location a = selection.getLocationA();
        Location b = selection.getLocationB();

        RegionManager regionManager = worldGuard.getRegionManager(selection
                .getWorld());
        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);

        BlockVector blockA = new BlockVector(a.getBlockX(), a.getBlockY(),
                a.getBlockZ());
        BlockVector blockB = new BlockVector(b.getBlockX(), b.getBlockY(),
                b.getBlockZ());

        ProtectedCuboidRegion region = new ProtectedCuboidRegion(regionName,
                blockA, blockB);
        DefaultDomain defaultDomain = new DefaultDomain();

        CuboidSelection worldEditSelection = new CuboidSelection(
                player.getWorld(), a, b);

        int height = worldEditSelection.getHeight();
        int length = worldEditSelection.getLength();
        int width = worldEditSelection.getWidth();

        Group group = areaProtect.getGroupManager().getGroup(player);

        int maxHeight = group.getMaximumHeight();
        int maxLength = group.getMaximumLength();
        int maxWidth = group.getMaximumWidth();

        // If height is too big
        if (height > maxHeight
                && !player
                        .hasPermission("areaprotect.ap.group.bypass.area-size")) {
            PlayerLogger.message(player, language.getMessage(
                    "Player.Create.Max-Height", new String[] { "%Max%",
                            "" + maxHeight, "%Selected%", "" + height }));
            return;
        }

        // If length is too big
        if (length > maxLength
                && !player
                        .hasPermission("areaprotect.ap.group.bypass.area-size")) {
            PlayerLogger.message(player, language.getMessage(
                    "Player.Create.Max-Length", new String[] { "%Max%",
                            "" + maxLength, "%Selected%", "" + length }));
            return;
        }

        // If width is too big
        if (width > maxWidth
                && !player
                        .hasPermission("areaprotect.ap.group.bypass.area-size")) {
            PlayerLogger.message(player, language.getMessage(
                    "Player.Create.Max-Width", new String[] { "%Max%",
                            "" + maxWidth, "%Selected%", "" + width }));
            return;
        }

        // If region overlaps
        if (regionManager.overlapsUnownedRegion(region, localPlayer)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Create.Overlaps"));
            return;
        }

        // If doesn't have enough money
        if (areaProtect.getVault() != null
                && !player.hasPermission("areaprotect.ap.group.bypass.price")) {
            if (group.isPayPerBlock()) {
                double balance = areaProtect.getVault().getEconomy()
                        .getBalance(player.getName());
                double price = group.getPrice() * worldEditSelection.getArea();

                if (balance < price) {
                    if (price > 1) {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Not-Enough-Per-Block",
                                new String[] {
                                        "%Price%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNamePlural() }));
                        return;
                    } else {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Not-Enough-Per-Block",
                                new String[] {
                                        "%Price%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNameSingular() }));
                        return;
                    }
                }
            }
        }

        String areaName = regionName.split("/")[2];

        defaultDomain.addPlayer(localPlayer);
        region.setOwners(defaultDomain);
        region.setFlags(group.getDefaultFlags().toWorldGuardFlags(areaName));

        regionManager.addRegion(region);

        // Take money
        if (areaProtect.getVault() != null
                && !player.hasPermission("areaprotect.ap.group.bypass.price")) {
            if (group.isPayPerBlock()) {
                double price = group.getPrice() * worldEditSelection.getArea();

                EconomyResponse response = areaProtect.getVault().getEconomy()
                        .withdrawPlayer(player.getName(), price);
                if (response.transactionSuccess()) {
                    if (price > 1) {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Took-Money", new String[] {
                                        "%Amount%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNamePlural() }));
                    } else {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Took-Money", new String[] {
                                        "%Amount%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNameSingular() }));
                    }
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Create.Error"));
                    return;
                }
            } else {
                double price = group.getPrice();

                EconomyResponse response = areaProtect.getVault().getEconomy()
                        .withdrawPlayer(player.getName(), price);
                if (response.transactionSuccess()) {
                    if (price > 1) {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Took-Money", new String[] {
                                        "%Amount%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNamePlural() }));
                    } else {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Took-Money", new String[] {
                                        "%Amount%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNameSingular() }));
                    }
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Create.Error"));
                    return;
                }
            }
        }

        try {
            regionManager.save();
        } catch (ProtectionDatabaseException e) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Create.Error"));
            return;
        }

        PlayerLogger.message(
                player,
                Language.getLanguageFile().getMessage(
                        "Player.Create.Successful",
                        new String[] { "%Area_Name%", areaName }));
    }

    public void selectionCanceled(Player player) {
        PlayerLogger
                .message(
                        player,
                        Language.getLanguageFile().getMessage(
                                "Player.Create.Canceled"));
        return;
    }
}
