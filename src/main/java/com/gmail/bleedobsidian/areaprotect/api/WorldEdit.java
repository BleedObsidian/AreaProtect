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

package com.gmail.bleedobsidian.areaprotect.api;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

/**
 * This class allows you to load and access WorldEdit's API.
 * 
 * @author Jesse Prescott
 */
public class WorldEdit {
    private WorldEditPlugin worldEdit;

    /**
     * Load WorldEdit plugin.
     * 
     * @param plugin
     *            - JavaPlugin.
     * @return If successfully loaded.
     */
    public boolean load(JavaPlugin plugin) {
        Plugin worldEdit = plugin.getServer().getPluginManager()
                .getPlugin("WorldEdit");

        if (worldEdit == null || !(worldEdit instanceof WorldEditPlugin)) {
            return false;
        } else {
            this.worldEdit = (WorldEditPlugin) worldEdit;
            return true;
        }
    }

    /**
     * Get WorldEdit plugin.
     * 
     * @return WorldEdit plugin.
     */
    public WorldEditPlugin getWorldEditPlugin() {
        return this.worldEdit;
    }
}
