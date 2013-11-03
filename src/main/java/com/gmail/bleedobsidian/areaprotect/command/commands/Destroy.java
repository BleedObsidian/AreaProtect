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

package com.gmail.bleedobsidian.areaprotect.command.commands;

import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configurations.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Destroy {
    public static void destroy(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        if (!player.hasPermission("areaprotect.ap.destroy")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();

        RegionManager regionManager = worldGuard.getRegionManager(player
                .getWorld());
        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
        ApplicableRegionSet regions = regionManager.getApplicableRegions(player
                .getLocation());

        if (args.length == 1) {
            if (regions.size() != 0) {
                for (ProtectedRegion region : regions) {
                    if (region.isOwner(localPlayer)
                            && region.getId().contains("areaprotect")
                            || player
                                    .hasPermission("areaprotect.ap.destroy.bypass.owner")
                            && region.getId().contains("areaprotect")) {
                        regionManager.removeRegion(region.getId());

                        String areaName = region.getId().split("/")[2];
                        PlayerLogger.message(player, language.getMessage(
                                "Player.Destroy.Successful", new String[] {
                                        "%Area_Name%", areaName }));
                    } else if (!region.getId().contains("areaprotect")) {
                        PlayerLogger.message(player, language
                                .getMessage("Player.Destroy.Not-Areaprotect"));
                        return;
                    } else {
                        PlayerLogger
                                .message(player, language
                                        .getMessage("Player.Destroy.Not-Owner"));
                        return;
                    }
                }
            } else {
                PlayerLogger.message(player,
                        language.getMessage("Player.Destroy.Not-In-Area"));
                return;
            }
        } else if (args.length == 2) {
            String regionName = "areaprotect/" + player.getName() + "/"
                    + args[1];

            if (regionManager.hasRegion(regionName)) {
                regionManager.removeRegion(regionName);

                PlayerLogger.message(player, language.getMessage(
                        "Player.Destroy.Successful", new String[] {
                                "%Area_Name%", args[1] }));
            } else {
                PlayerLogger.message(player, language.getMessage(
                        "Player.Destroy.Invalid-Name", new String[] {
                                "%Area_Name%", args[1] }));
                return;
            }
        } else {
            PlayerLogger.message(player,
                    language.getMessage("Player.Destroy.Usage"));
            return;
        }

        try {
            regionManager.save();
            return;
        } catch (ProtectionDatabaseException e) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Destory.Error"));
            return;
        }
    }
}
