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

package com.gmail.bleedobsidian.areaprotect.command;

import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Group;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configuration.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class Create {
    public static void create(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        // If doesn't have permission
        if (!player.hasPermission("areaprotect.ap.create")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        // If not enough arguments
        if (!(args.length == 2)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Create.Usage"));
            return;
        }

        RegionManager regionManager = areaProtect.getWorldGuard()
                .getWorldGuardPlugin().getRegionManager(player.getWorld());
        LocalPlayer localPlayer = areaProtect.getWorldGuard()
                .getWorldGuardPlugin().wrapPlayer(player);

        // If area limit reached
        int currentAmountOfAreas = regionManager
                .getRegionCountOfPlayer(localPlayer);
        int maxAmountOfAreas = areaProtect.getGroupManager().getGroup(player)
                .getMaximumAreas();
        if (currentAmountOfAreas >= maxAmountOfAreas
                && !player
                        .hasPermission("areaprotect.ap.group.bypass.area-limit")) {
            PlayerLogger.message(player, language.getMessage(
                    "Player.Create.Max-Areas", new String[] { "%Current%",
                            "" + currentAmountOfAreas, "%Max%",
                            "" + maxAmountOfAreas }));
            return;
        }

        // If already creating an area
        if (areaProtect.getSelectionManager().isPendingSelection(player)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Create.Selecting"));
            PlayerLogger.message(player,
                    language.getMessage("Player.Create.Cancel"));
            return;
        }

        // If invalid area name
        if (args[1].contains("/")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Create.Invalid-Name"));
            return;
        }

        String regionName = "areaprotect/" + player.getName() + "/" + args[1];

        // If area name is in use
        if (regionManager.hasRegion(regionName)) {
            PlayerLogger.message(player, language.getMessage(
                    "Player.Create.Name-Used", new String[] { "%Area_Name%",
                            args[1] }));
            return;
        }

        // If not pay per block and doesn't have enough money
        if (areaProtect.getVault() != null
                && !player.hasPermission("areaprotect.ap.group.bypass.price")) {
            Group group = areaProtect.getGroupManager().getGroup(player);

            if (!group.isPayPerBlock()) {
                double balance = areaProtect.getVault().getEconomy()
                        .getBalance(player.getName());
                double price = group.getPrice();

                if (balance < price) {
                    if (price > 1) {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Not-Enough", new String[] {
                                        "%Price%",
                                        "" + price,
                                        "%Currency_Name%",
                                        areaProtect.getVault().getEconomy()
                                                .currencyNamePlural() }));
                        return;
                    } else {
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Create.Not-Enough", new String[] {
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

        areaProtect.getSelectionManager().addPendingSelection(
                new CreateSelectionListener(areaProtect, regionName), player);

        PlayerLogger.message(player,
                language.getMessage("Player.Create.Select"));
        PlayerLogger.message(player,
                language.getMessage("Player.Create.Cancel"));

        return;
    }
}
