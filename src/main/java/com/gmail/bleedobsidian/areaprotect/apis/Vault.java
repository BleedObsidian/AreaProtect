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

package com.gmail.bleedobsidian.areaprotect.apis;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class allows you to load and access vault's economy plugins.
 * 
 * @author Jesse Prescott
 */
public class Vault {
    private Economy economy;

    /**
     * Load vault and an economy plugin.
     * 
     * @param plugin
     *            - JavaPlugin.
     * @return If successfully loaded.
     */
    public boolean load(JavaPlugin plugin) {
        if (Vault.isAccessible(plugin)) {
            RegisteredServiceProvider<Economy> economyProvider = plugin
                    .getServer().getServicesManager()
                    .getRegistration(Economy.class);

            if (economyProvider != null) {
                this.economy = economyProvider.getProvider();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Get economy plugin.
     * 
     * @return Economy plugin.
     */
    public Economy getEconomy() {
        return this.economy;
    }

    /**
     * If Vault is accessible.
     * 
     * @param plugin
     *            - JavaPlugin.
     * @return If Vault is accessible.
     */
    public static boolean isAccessible(JavaPlugin plugin) {
        return plugin.getServer().getPluginManager().getPlugin("Vault") == null ? false
                : true;
    }
}
