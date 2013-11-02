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
import com.gmail.bleedobsidian.areaprotect.Group;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configuration.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.logger.PlayerLogger;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
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
            Flag.greeting(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("farewell")) {
            Flag.farewell(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("pvp")) {
            Flag.pvp(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("chest-access")) {
            Flag.chestAccess(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("entry")) {
            Flag.entry(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("send-chat")) {
            Flag.sendChat(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("receive-chat")) {
            Flag.receiveChat(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("use")) {
            Flag.use(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("mob-damage")) {
            Flag.mobDamage(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("mob-spawning")) {
            Flag.mobSpawning(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("creeper-explosion")) {
            Flag.creeperExplosion(areaProtect, language, player, args);
        } else if (flag.equalsIgnoreCase("enderman-grief")) {
            Flag.endermanGrief(areaProtect, language, player, args);
        } else {
            PlayerLogger.message(
                    player,
                    language.getMessage("Player.Flag.No-Flag", new String[] {
                            "%flag%", flag }));
            return;
        }
    }

    private static void greeting(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        if (!player.hasPermission("areaprotect.ap.flag.greeting")) {
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

        if (value.equalsIgnoreCase("true")) {
            ApplicableRegionSet regions = Flag.getAreasStandingIn(worldGuard,
                    player);

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
                    if (region.getFlag(DefaultFlag.GREET_MESSAGE) == null) {
                        String areaName = region.getId().split("/")[2];
                        Group group = areaProtect.getGroupManager().getGroup(
                                player);

                        region.setFlag(DefaultFlag.GREET_MESSAGE, group
                                .getDefaultFlags().getGreetingMessage(areaName));
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
                                "Greeting" }));
                continue;
            }
        } else if (value.equalsIgnoreCase("false")) {
            ApplicableRegionSet regions = Flag.getAreasStandingIn(worldGuard,
                    player);

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
                    if (region.getFlag(DefaultFlag.GREET_MESSAGE) != null) {
                        region.setFlag(DefaultFlag.GREET_MESSAGE, null);
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
                                "Greeting" }));
                continue;
            }
        } else {
            ApplicableRegionSet regions = Flag.getAreasStandingIn(worldGuard,
                    player);

            if (regions.size() == 0) {
                PlayerLogger.message(player,
                        language.getMessage("Player.Flag.Not-In-Area"));
                return;
            }

            String message = "";
            String greetingMessage = "&b[AreaProtect]:&g ";
            for (ProtectedRegion region : regions) {
                if (region.isOwner(localPlayer)
                        && region.getId().contains("areaprotect")
                        || player
                                .hasPermission("areaprotect.ap.flag.bypass.owner")
                        && region.getId().contains("areaprotect")) {
                    if (region.getFlag(DefaultFlag.GREET_MESSAGE) != null) {
                        for (int i = 2; i < args.length; i++) {
                            message += args[i] + " ";
                        }

                        greetingMessage += message;

                        region.setFlag(DefaultFlag.GREET_MESSAGE,
                                greetingMessage);
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
                                "%flag%", "Greeting", "%value%", message }));
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

    private static void farewell(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void pvp(AreaProtect areaProtect, LanguageFile language,
            Player player, String[] args) {
        return;
    }

    private static void chestAccess(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void entry(AreaProtect areaProtect, LanguageFile language,
            Player player, String[] args) {
        return;
    }

    private static void sendChat(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void receiveChat(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void use(AreaProtect areaProtect, LanguageFile language,
            Player player, String[] args) {
        return;
    }

    private static void mobDamage(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void mobSpawning(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void creeperExplosion(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
    }

    private static void endermanGrief(AreaProtect areaProtect,
            LanguageFile language, Player player, String[] args) {
        return;
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
