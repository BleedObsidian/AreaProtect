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
import com.gmail.bleedobsidian.areaprotect.Group;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configurations.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.StringFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Flag {
    public static void flag(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        if (!(args.length >= 3)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Flag.Usage"));
            return;
        }

        String flag = args[1];

        if (flag.equalsIgnoreCase("greeting")) {
            Group playerGroup = areaProtect.getGroupManager().getGroup(player);

            Flag.setStringFlag(areaProtect, language, player, args,
                    DefaultFlag.GREET_MESSAGE, playerGroup, "Greeting",
                    "areaprotect.ap.flag.greeting");
        } else if (flag.equalsIgnoreCase("farewell")) {
            Group playerGroup = areaProtect.getGroupManager().getGroup(player);

            Flag.setStringFlag(areaProtect, language, player, args,
                    DefaultFlag.FAREWELL_MESSAGE, playerGroup, "Farewell",
                    "areaprotect.ap.flag.farewell");
        } else if (flag.equalsIgnoreCase("pvp")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.PVP, "PvP", "areaprotect.ap.flag.pvp");
        } else if (flag.equalsIgnoreCase("chest-access")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.CHEST_ACCESS, "Chest-Access",
                    "areaprotect.ap.flag.chest-access");
        } else if (flag.equalsIgnoreCase("entry")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.ENTRY, "Entry", "areaprotect.ap.flag.entry");
        } else if (flag.equalsIgnoreCase("send-chat")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.SEND_CHAT, "Send-Chat",
                    "areaprotect.ap.flag.send-chat");
        } else if (flag.equalsIgnoreCase("receive-chat")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.RECEIVE_CHAT, "Receive-Chat",
                    "areaprotect.ap.flag.receive-chat");
        } else if (flag.equalsIgnoreCase("use")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.USE, "Use", "areaprotect.ap.flag.use");
        } else if (flag.equalsIgnoreCase("mob-damage")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.MOB_DAMAGE, "Mob-Damage",
                    "areaprotect.ap.flag.mob-damage");
        } else if (flag.equalsIgnoreCase("mob-spawning")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.MOB_SPAWNING, "Mob-Spawning",
                    "areaprotect.ap.flag.mob-spawning");
        } else if (flag.equalsIgnoreCase("creeper-explosion")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.CREEPER_EXPLOSION, "Creeper-Explosion",
                    "areaprotect.ap.flag.creeper-explosion");
        } else if (flag.equalsIgnoreCase("enderman-grief")) {
            Flag.setStateFlag(areaProtect, language, player, args,
                    DefaultFlag.ENDER_BUILD, "Enderman-Grief",
                    "areaprotect.ap.flag.enderman-grief");
        } else {
            PlayerLogger.message(
                    player,
                    language.getMessage("Player.Flag.No-Flag", new String[] {
                            "%flag%", flag }));
            return;
        }
    }

    private static void setStringFlag(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args,
            StringFlag flag, Group playerGroup, String flagName,
            String flagPermission) {
        if (!player.hasPermission(flagPermission)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Flag.Permission"));
            return;
        }

        String value = args[2];
        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();
        RegionManager regionManager = worldGuard.getRegionManager(player
                .getWorld());
        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
        ApplicableRegionSet regions = Flag.getAreasStandingIn(worldGuard,
                player);

        if (value.equalsIgnoreCase("true")) {
            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Flag.Not-In-Area"));
                return;
            }

            for (ProtectedRegion region : regions) {
                if (region.isOwner(localPlayer)
                        && region.getId().contains("areaprotect")
                        || player
                                .hasPermission("areaprotect.ap.flag.bypass.owner")
                        && region.getId().contains("areaprotect")) {
                    if (region.getFlag(flag) == null) {
                        String areaName = region.getId().split("/")[2];

                        if (flag.equals(DefaultFlag.GREET_MESSAGE)) {
                            region.setFlag(flag, playerGroup.getDefaultFlags()
                                    .getGreetingMessage(areaName));
                        } else if (flag.equals(DefaultFlag.FAREWELL_MESSAGE)) {
                            region.setFlag(flag, playerGroup.getDefaultFlags()
                                    .getFarewellMessage(areaName));
                        }
                    }
                } else if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Areaprotect"));
                    return;
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Owner"));
                    return;
                }

                PlayerLogger.message(player, language.getMessage(
                        "Player.Flag.Enabled", new String[] { "%flag%",
                                flagName }));
                continue;
            }
        } else if (value.equalsIgnoreCase("false")) {
            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Flag.Not-In-Area"));
                return;
            }

            for (ProtectedRegion region : regions) {
                if (region.isOwner(localPlayer)
                        && region.getId().contains("areaprotect")
                        || player
                                .hasPermission("areaprotect.ap.flag.bypass.owner")
                        && region.getId().contains("areaprotect")) {
                    if (region.getFlag(flag) != null) {
                        region.setFlag(flag, null);
                    }
                } else if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Areaprotect"));
                    return;
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Owner"));
                    return;
                }

                PlayerLogger.message(player, language.getMessage(
                        "Player.Flag.Disabled", new String[] { "%flag%",
                                flagName }));
                continue;
            }
        } else {
            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Flag.Not-In-Area"));
                return;
            }

            String rawMessage = "";
            String finalMessage = "&b[AreaProtect]:&g ";
            for (ProtectedRegion region : regions) {
                if (region.isOwner(localPlayer)
                        && region.getId().contains("areaprotect")
                        || player
                                .hasPermission("areaprotect.ap.flag.bypass.owner")
                        && region.getId().contains("areaprotect")) {
                    if (region.getFlag(flag) != null) {
                        for (int i = 2; i < args.length; i++) {
                            rawMessage += args[i] + " ";
                        }

                        finalMessage += rawMessage;

                        region.setFlag(flag, finalMessage);
                    }
                } else if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Areaprotect"));
                    return;
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Owner"));
                    return;
                }

                PlayerLogger.message(
                        player,
                        language.getMessage("Player.Flag.Set", new String[] {
                                "%flag%", flagName, "%value%", rawMessage }));
                continue;
            }
        }

        if (Flag.save(regionManager)) {
            return;
        } else {
            PlayerLogger.message(player,
                    language.getMessage("Player.Flag.Error"));
            return;
        }
    }

    private static void setStateFlag(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args,
            StateFlag flag, String flagName, String flagPermission) {
        if (!player.hasPermission(flagPermission)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Flag.Permission"));
            return;
        }

        String value = args[2];
        WorldGuardPlugin worldGuard = areaProtect.getWorldGuard()
                .getWorldGuardPlugin();
        RegionManager regionManager = worldGuard.getRegionManager(player
                .getWorld());
        LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
        ApplicableRegionSet regions = Flag.getAreasStandingIn(worldGuard,
                player);

        if (value.equalsIgnoreCase("true")) {
            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Flag.Not-In-Area"));
                return;
            }

            for (ProtectedRegion region : regions) {
                if (region.isOwner(localPlayer)
                        && region.getId().contains("areaprotect")
                        || player
                                .hasPermission("areaprotect.ap.flag.bypass.owner")
                        && region.getId().contains("areaprotect")) {

                    region.setFlag(flag, StateFlag.State.ALLOW);
                } else if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Areaprotect"));
                    return;
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Owner"));
                    return;
                }

                PlayerLogger.message(player, language.getMessage(
                        "Player.Flag.Enabled", new String[] { "%flag%",
                                flagName }));
                continue;
            }
        } else if (value.equalsIgnoreCase("false")) {
            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Flag.Not-In-Area"));
                return;
            }

            for (ProtectedRegion region : regions) {
                if (region.isOwner(localPlayer)
                        && region.getId().contains("areaprotect")
                        || player
                                .hasPermission("areaprotect.ap.flag.bypass.owner")
                        && region.getId().contains("areaprotect")) {
                    region.setFlag(flag, StateFlag.State.DENY);
                } else if (!region.getId().contains("areaprotect")) {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Areaprotect"));
                    return;
                } else {
                    PlayerLogger.message(player,
                            language.getMessage("Player.Flag.Not-Owner"));
                    return;
                }

                PlayerLogger.message(player, language.getMessage(
                        "Player.Flag.Disabled", new String[] { "%flag%",
                                flagName }));
                continue;
            }
        } else {
            PlayerLogger.message(
                    player,
                    language.getMessage("Player.Flag.No-Value", new String[] {
                            "%flag%", flagName }));
            return;
        }

        if (Flag.save(regionManager)) {
            return;
        } else {
            PlayerLogger.message(player,
                    language.getMessage("Player.Flag.Error"));
            return;
        }
    }

    private static ApplicableRegionSet getAreasStandingIn(
            WorldGuardPlugin worldGuard, Player player) {
        return worldGuard.getRegionManager(player.getWorld())
                .getApplicableRegions(player.getLocation());
    }

    private static boolean save(RegionManager regionManager) {
        try {
            regionManager.save();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
