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

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configuration.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Info {
    public static void info(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        if (!(args.length >= 2)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Info.Usage"));
            return;
        }

        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();

        RegionManager regionManager = worldGuard.getRegionManager(player
                .getWorld());

        String command = args[1];

        if (command.equalsIgnoreCase("area")) {
            Info.area(worldGuard, regionManager, player, args, language);
        } else if (command.equalsIgnoreCase("player")) {
            Info.player(worldGuard, regionManager, player, args, language,
                    areaProtect);
        } else {
            PlayerLogger.message(player,
                    language.getMessage("Player.Info.Usage"));
            return;
        }
    }

    private static void area(WorldGuardPlugin worldGuard,
            RegionManager regionManager, Player player, String[] args,
            LanguageFile language) {
        if (!player.hasPermission("areaprotect.ap.info.area")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        if (args.length == 2) {
            ApplicableRegionSet regions = regionManager
                    .getApplicableRegions(player.getLocation());

            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Info.Not-In-Area"));
                return;
            }

            for (ProtectedRegion region : regions) {
                if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Info.Not-Areaprotect"));
                    return;
                }

                if (region.isOwner(player.getName())
                        || player
                                .hasPermission("areaprotect.ap.info.area.bypass.owner")) {
                    String ownersList = "", membersList = "";
                    DefaultDomain owners = region.getOwners();
                    DefaultDomain members = region.getMembers();

                    for (String playerName : owners.getPlayers()) {
                        ownersList += playerName + " ";
                    }

                    for (String playerName : members.getPlayers()) {
                        membersList += playerName + " ";
                    }

                    PlayerLogger.message(player,
                            language.getMessage("Player.Info.Area-Message1"));
                    PlayerLogger.message(player, language.getMessage(
                            "Player.Info.Area-Message2",
                            new String[] { "%Area_Name%",
                                    region.getId().split("/")[2] }));
                    PlayerLogger.message(player, language.getMessage(
                            "Player.Info.Area-Message3", new String[] {
                                    "%Owners%", ownersList }));
                    PlayerLogger.message(player, language.getMessage(
                            "Player.Info.Area-Message4", new String[] {
                                    "%Members%", membersList }));
                    PlayerLogger.message(player,
                            language.getMessage("Player.Info.Area-Message5"));
                    return;
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Info.Not-Owner"));
                    return;
                }
            }
        } else {
            String areaName = "areaprotect/" + player.getName() + "/" + args[2];

            if (!regionManager.hasRegion(areaName)) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.Destroy.Invalid-Name", new String[] {
                                "%Area_Name%", args[2] }));
                return;
            }

            ProtectedRegion region = regionManager.getRegion(areaName);

            if (region.isOwner(player.getName())
                    || player
                            .hasPermission("areaprotect.ap.info.area.bypass.owner")) {
                String ownersList = "", membersList = "";
                DefaultDomain owners = region.getOwners();
                DefaultDomain members = region.getMembers();

                for (String playerName : owners.getPlayers()) {
                    ownersList += playerName + " ";
                }

                for (String playerName : members.getPlayers()) {
                    membersList += playerName + " ";
                }

                PlayerLogger.message(player,
                        language.getMessage("Player.Info.Area-Message1"));
                PlayerLogger.message(player, language.getMessage(
                        "Player.Info.Area-Message2", new String[] {
                                "%Area_Name%", region.getId().split("/")[2] }));
                PlayerLogger.message(player, language.getMessage(
                        "Player.Info.Area-Message3", new String[] { "%Owners%",
                                ownersList }));
                PlayerLogger.message(player, language.getMessage(
                        "Player.Info.Area-Message4", new String[] {
                                "%Members%", membersList }));
                PlayerLogger.message(player,
                        language.getMessage("Player.Info.Area-Message5"));
                return;
            } else {
                PlayerLogger.message(player,
                        language.getMessage("Player.Info.Not-Owner"));
                return;
            }
        }
    }

    private static void player(WorldGuardPlugin worldGuard,
            RegionManager regionManager, Player player, String[] args,
            LanguageFile language, AreaProtect areaProtect) {
        if (!player.hasPermission("areaprotect.ap.info.player")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        if (args.length == 2) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Info.Player-Message1"));
            PlayerLogger.message(player, language.getMessage(
                    "Player.Info.Player-Message2", new String[] { "%Player%",
                            player.getName() }));
            PlayerLogger.message(player, language.getMessage(
                    "Player.Info.Player-Message3", new String[] {
                            "%Group%",
                            areaProtect.getGroupManager().getGroup(player)
                                    .getName() }));
            PlayerLogger
                    .message(
                            player,
                            language.getMessage(
                                    "Player.Info.Player-Message4",
                                    new String[] {
                                            "%Current%",
                                            ""
                                                    + regionManager
                                                            .getRegionCountOfPlayer(worldGuard
                                                                    .wrapPlayer(player)),
                                            "%Max%",
                                            ""
                                                    + areaProtect
                                                            .getGroupManager()
                                                            .getGroup(player)
                                                            .getMaximumAreas() }));
            PlayerLogger.message(player,
                    language.getMessage("Player.Info.Player-Message5"));
            return;
        } else {
            if (!player.hasPermission("areaprotect.ap.info.player.other")) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Info.Player-Permission"));
                return;
            }

            OfflinePlayer playerTo = areaProtect.getServer().getOfflinePlayer(
                    args[2]);

            if (!playerTo.hasPlayedBefore()) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.Info.No-Player", new String[] { "%player%",
                                args[2] }));
                return;
            }

            PlayerLogger.message(player,
                    language.getMessage("Player.Info.Player-Message1"));
            PlayerLogger.message(player, language.getMessage(
                    "Player.Info.Player-Message2", new String[] { "%Player%",
                            playerTo.getName() }));
            PlayerLogger.message(player, language.getMessage(
                    "Player.Info.Player-Message3", new String[] {
                            "%Group%",
                            areaProtect.getGroupManager().getGroup(playerTo)
                                    .getName() }));
            PlayerLogger.message(player, language.getMessage(
                    "Player.Info.Player-Message4",
                    new String[] {
                            "%Current%",
                            ""
                                    + regionManager
                                            .getRegionCountOfPlayer(worldGuard
                                                    .wrapPlayer(playerTo
                                                            .getPlayer())),
                            "%Max%",
                            ""
                                    + areaProtect.getGroupManager()
                                            .getGroup(playerTo)
                                            .getMaximumAreas() }));
            PlayerLogger.message(player,
                    language.getMessage("Player.Info.Player-Message5"));
            return;
        }
    }
}
