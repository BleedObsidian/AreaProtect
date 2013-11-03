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

package com.gmail.bleedobsidian.areaprotect.manager;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.FlagSet;
import com.gmail.bleedobsidian.areaprotect.Group;
import com.gmail.bleedobsidian.areaprotect.configuration.ConfigFile;

public class GroupManager {
    private ConfigFile groupFile;

    private HashMap<String, Group> groups = new HashMap<String, Group>();
    private Group defaultGroup;

    public GroupManager(ConfigFile groupFile) {
        this.groupFile = groupFile;
    }

    public void loadGroups() {
        Set<String> groupNames = this.groupFile.getFileConfiguration()
                .getConfigurationSection("Groups").getKeys(false);

        for (String groupName : groupNames) {
            ConfigurationSection groupSection = this.groupFile
                    .getFileConfiguration().getConfigurationSection(
                            "Groups." + groupName);

            int maximumAreas = groupSection.getInt("Maximum-Areas");
            int maximumHeight = groupSection.getInt("Maximum-Height");
            int maximumLength = groupSection.getInt("Maximum-Length");
            int maximumWidth = groupSection.getInt("Maximum-Width");
            boolean payPerBlock = groupSection.getBoolean("Pay-Per-Block");
            double price = groupSection.getDouble("Price");

            FlagSet defaultFlags = new FlagSet();

            defaultFlags.setGreeting(groupSection
                    .getBoolean("Flags.Greeting.Enabled"));
            defaultFlags.setGreetingMessage(groupSection
                    .getString("Flags.Greeting.Message"));
            defaultFlags.setFarewell(groupSection
                    .getBoolean("Flags.Farewell.Enabled"));
            defaultFlags.setFarewellMessage(groupSection
                    .getString("Flags.Farewell.Message"));
            defaultFlags.setPVP(groupSection.getBoolean("Flags.PvP"));
            defaultFlags.setChestAccess(groupSection
                    .getBoolean("Flags.Chest-Access"));
            defaultFlags.setEntry(groupSection.getBoolean("Flags.Entry"));
            defaultFlags
                    .setSendChat(groupSection.getBoolean("Flags.Send-Chat"));
            defaultFlags.setReceiveChat(groupSection
                    .getBoolean("Flags.Receive-Chat"));
            defaultFlags.setUse(groupSection.getBoolean("Flags.Use"));
            defaultFlags.setMobDamage(groupSection
                    .getBoolean("Flags.Mob-Damage"));
            defaultFlags.setMobSpawning(groupSection
                    .getBoolean("Flags.Mob-Spawning"));
            defaultFlags.setCreeperExplosion(groupSection
                    .getBoolean("Flags.Creeper-Explosion"));
            defaultFlags.setEndermanGrief(groupSection
                    .getBoolean("Flags.Enderman-Grief"));

            Group group = new Group(groupName, maximumAreas, maximumHeight,
                    maximumLength, maximumWidth, payPerBlock, price,
                    defaultFlags);

            if (groupName.equalsIgnoreCase("Default")) {
                this.defaultGroup = group;
            } else {
                this.groups.put(groupName, group);
            }
        }
    }

    public Group getGroup(String name) {
        return this.groups.get(name);
    }

    public Group getGroup(Player player) {
        for (String groupName : this.groups.keySet()) {
            if (player.hasPermission("areaprotect.group." + groupName)) {
                return this.groups.get(groupName);
            }
        }

        return this.defaultGroup;
    }

    public Group getGroup(OfflinePlayer player) {
        for (String groupName : this.groups.keySet()) {
            if (player.getPlayer().hasPermission(
                    "areaprotect.group." + groupName)) {
                return this.groups.get(groupName);
            }
        }

        return this.defaultGroup;
    }

    public HashMap<String, Group> getGroups() {
        return this.groups;
    }

    public Group getDefaultGroup() {
        return this.defaultGroup;
    }
}
