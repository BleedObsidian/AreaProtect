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

package com.gmail.bleedobsidian.areaprotect.commands;

import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configuration.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RemoveOwner {
    public static void removeOwner(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        if (!player.hasPermission("areaprotect.ap.removeowner")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        if (!(args.length >= 2)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.RemoveOwner.Usage"));
            return;
        }

        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();
        RegionManager regionManager = worldGuard.getRegionManager(player
                .getWorld());
        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);

        String memberName;

        if (args.length == 2) {
            ApplicableRegionSet regions = regionManager
                    .getApplicableRegions(player.getLocation());

            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.RemoveOwner.Not-In-Area"));
                return;
            }

            memberName = args[1];

            for (ProtectedRegion region : regions) {
                if (!region.isOwner(localPlayer)
                        && !player
                                .hasPermission("areaprotect.ap.removeowner.bypass.owner")) {
                    PlayerLogger
                            .message(player, language
                                    .getMessage("Player.RemoveOwner.Not-Owner"));
                    return;
                }

                if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player, language
                            .getMessage("Player.RemoveOwner.Not-Areaprotect"));
                    return;
                }

                if (!areaProtect.getServer().getOfflinePlayer(memberName)
                        .hasPlayedBefore()) {
                    PlayerLogger.message(player, language.getMessage(
                            "Player.RemoveOwner.No-Player", new String[] {
                                    "%player%", memberName }));
                    return;
                }

                DefaultDomain owners = region.getOwners();

                if (!owners.contains(memberName)) {
                    PlayerLogger.message(player, language.getMessage(
                            "Player.RemoveOwner.No-Owner", new String[] {
                                    "%player%", memberName }));
                    return;
                }

                owners.removePlayer(memberName);
                region.setOwners(owners);
            }
        } else {
            String areaName = "areaprotect/" + player.getName() + "/" + args[1];

            ProtectedRegion region = regionManager.getRegion(areaName);

            if (region == null) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.RemoveOwner.Invalid-Name", new String[] {
                                "%Area_Name%", args[1] }));
                return;
            }

            memberName = args[2];

            if (!areaProtect.getServer().getOfflinePlayer(memberName)
                    .hasPlayedBefore()) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.RemoveOwner.No-Player", new String[] {
                                "%player%", memberName }));
                return;
            }

            DefaultDomain owners = region.getOwners();

            if (!owners.contains(memberName)) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.RemoveOwner.No-Owner", new String[] {
                                "%player%", memberName }));
                return;
            }

            owners.removePlayer(memberName);
            region.setOwners(owners);
        }

        try {
            regionManager.save();
            PlayerLogger.message(player, language.getMessage(
                    "Player.RemoveOwner.Successful", new String[] { "%player%",
                            memberName }));
            return;
        } catch (ProtectionDatabaseException e) {
            PlayerLogger.message(player,
                    language.getMessage("Player.RemoveOwner.Error"));
            return;
        }
    }
}
