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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configurations.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PluginLogger;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Convert {
    public static void convert(CommandSender sender, AreaProtect areaProtect) {
        LanguageFile language = Language.getLanguageFile();

        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();
        OfflinePlayer[] players = areaProtect.getServer().getOfflinePlayers();

        PluginLogger.warning(language.getMessage("Player.Convert.Converting"));

        for (OfflinePlayer player : players) {
            List<World> worlds = areaProtect.getServer().getWorlds();

            for (World world : worlds) {
                RegionManager regionManager = worldGuard
                        .getRegionManager(world);
                Map<String, ProtectedRegion> regions = new HashMap<String, ProtectedRegion>(
                        regionManager.getRegions());

                for (Map.Entry<String, ProtectedRegion> entry : regions
                        .entrySet()) {

                    if (entry.getKey().contains(
                            player.getName().toLowerCase() + "-")) {
                        if (entry.getValue() instanceof ProtectedCuboidRegion) {
                            ProtectedCuboidRegion region = (ProtectedCuboidRegion) entry
                                    .getValue();

                            String newAreaName = "areaprotect/"
                                    + player.getName() + "/"
                                    + region.getId().split("-")[1];

                            ProtectedCuboidRegion newRegion = new ProtectedCuboidRegion(
                                    newAreaName, region.getMinimumPoint(),
                                    region.getMaximumPoint());
                            newRegion.setFlags(region.getFlags());
                            newRegion.setOwners(region.getOwners());
                            newRegion.setMembers(region.getMembers());

                            regionManager.addRegion(newRegion);
                            regionManager.removeRegion(region.getId());
                        }
                    }
                }

                try {
                    regionManager.save();
                } catch (ProtectionDatabaseException e) {
                    PluginLogger.warning(language
                            .getMessage("Player.Convert.Error"));
                    return;
                }
            }
        }

        PluginLogger.warning(language.getMessage("Player.Convert.Converted"));
    }
}
