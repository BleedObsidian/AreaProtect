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

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

/**
 * This class allows you to load and access WorldGuard's API.
 * 
 * @author Jesse Prescott
 */
public class WorldGuard {
    private WorldGuardPlugin worldGuard;

    /**
     * Load WorldGuard plugin.
     * 
     * @param plugin
     *            - JavaPlugin.
     * @return If successfully loaded.
     */
    public boolean load(JavaPlugin plugin) {
        Plugin worldGuard = plugin.getServer().getPluginManager()
                .getPlugin("WorldGuard");

        if (worldGuard == null || !(worldGuard instanceof WorldGuardPlugin)) {
            return false;
        } else {
            this.worldGuard = (WorldGuardPlugin) worldGuard;
            return true;
        }
    }

    /**
     * Get WorldGuard plugin.
     * 
     * @return WorldGuard plugin.
     */
    public WorldGuardPlugin getWorldGuardPlugin() {
        return this.worldGuard;
    }
}
