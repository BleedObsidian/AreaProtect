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
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.databases.ProtectionDatabaseException;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class RemoveMember {
    public static void removeMember(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        if (!player.hasPermission("areaprotect.ap.removemember")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        if (!(args.length >= 2)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.RemoveMember.Usage"));
            return;
        }

        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();

        RegionManager regionManager = worldGuard.getRegionManager(player
                .getWorld());
        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
        ApplicableRegionSet regions = regionManager.getApplicableRegions(player
                .getLocation());

        String memberName;

        if (args.length == 2) {
            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.RemoveMember.Not-In-Area"));
                return;
            }

            memberName = args[1];

            for (ProtectedRegion region : regions) {
                if (!region.isOwner(localPlayer)
                        && !player
                                .hasPermission("areaprotect.ap.removemember.bypass.owner")) {
                    PlayerLogger.message(player, language
                            .getMessage("Player.RemoveMember.Not-Owner"));
                    return;
                }

                if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player, language
                            .getMessage("Player.RemoveMember.Not-Areaprotect"));
                    return;
                }

                if (!areaProtect.getServer().getOfflinePlayer(memberName)
                        .hasPlayedBefore()) {
                    PlayerLogger.message(player, language.getMessage(
                            "Player.RemoveMember.No-Player", new String[] {
                                    "%player%", memberName }));
                    return;
                }

                DefaultDomain members = region.getMembers();

                if (!members.contains(memberName)) {
                    PlayerLogger.message(player, language.getMessage(
                            "Player.RemoveMember.No-Member", new String[] {
                                    "%player%", memberName }));
                    return;
                }

                members.removePlayer(memberName);
                region.setMembers(members);
            }
        } else {
            String areaName = "areaprotect/" + player.getName() + "/" + args[1];

            ProtectedRegion region = regionManager.getRegion(areaName);

            if (region == null) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.RemoveMember.Invalid-Name", new String[] {
                                "%Area_Name%", args[1] }));
                return;
            }

            memberName = args[2];

            if (!areaProtect.getServer().getOfflinePlayer(memberName)
                    .hasPlayedBefore()) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.RemoveMember.No-Player", new String[] {
                                "%player%", memberName }));
                return;
            }

            DefaultDomain members = region.getMembers();

            if (!members.contains(memberName)) {
                PlayerLogger.message(player, language.getMessage(
                        "Player.RemoveMember.No-Member", new String[] {
                                "%player%", memberName }));
                return;
            }

            members.removePlayer(memberName);
            region.setMembers(members);
        }

        try {
            regionManager.save();
            PlayerLogger.message(player, language.getMessage(
                    "Player.RemoveMember.Successful", new String[] {
                            "%player%", memberName }));
            return;
        } catch (ProtectionDatabaseException e) {
            PlayerLogger.message(player,
                    language.getMessage("Player.RemoveMember.Error"));
            return;
        }
    }
}
