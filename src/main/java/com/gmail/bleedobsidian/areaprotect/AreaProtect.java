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

package com.gmail.bleedobsidian.areaprotect;

import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.bleedobsidian.areaprotect.commands.APCommandExecutor;
import com.gmail.bleedobsidian.areaprotect.configuration.ConfigFile;
import com.gmail.bleedobsidian.areaprotect.logger.PluginLogger;
import com.gmail.bleedobsidian.areaprotect.managers.GroupManager;
import com.gmail.bleedobsidian.areaprotect.metrics.Graphs;
import com.gmail.bleedobsidian.areaprotect.metrics.Metrics;

public class AreaProtect extends JavaPlugin {
    private ConfigFile config;
    private ConfigFile groups;

    private GroupManager groupManager;

    private WorldGuard worldGuard;
    private Vault vault;

    private Metrics metrics;

    @Override
    public void onEnable() {
        // Setup Logger
        PluginLogger.setJavaPlugin(this);

        // Load Configuration Files
        try {
            this.config = new ConfigFile("config.yml");
            this.groups = new ConfigFile("groups.yml");

            this.config.load(this);
            this.groups.load(this);
        } catch (IOException e) {
            PluginLogger.error("Failed to load configuration files.", e);
            return;
        }

        // Set Language
        String locale = this.config.getFileConfiguration().getString("Locale");

        if (Language.exists(locale)) {
            Language.setLangauge(locale + ".yml", this);
        } else {
            PluginLogger.warning("Failed to find locale: " + locale
                    + ", using en-us instead.", true);
            Language.setLangauge("en-us.yml", this);
        }

        // Check For Update
        if (this.config.getFileConfiguration().getBoolean(
                "Updates.Check-For-Update")) {
            if (Update.isNewVersionAvailable(this)) {
                PluginLogger.warning(Language.getLanguageFile().getMessage(
                        "Console.Update-Available"));
            }
        }

        // Load WorldGuard
        this.worldGuard = new WorldGuard();

        if (this.worldGuard.load(this)) {
            PluginLogger.info(Language.getLanguageFile().getMessage(
                    "Console.WorldGuard.Successful"));
        } else {
            PluginLogger.error(Language.getLanguageFile().getMessage(
                    "Console.WorldGuard.Unsuccessful"));
            return;
        }

        // Load Vault
        if (this.config.getFileConfiguration().getBoolean("Use-Vault")) {
            this.vault = new Vault();

            if (this.vault.load(this)) {
                PluginLogger.info(Language.getLanguageFile().getMessage(
                        "Console.Vault.Successful"));
            } else {
                PluginLogger.warning(
                        Language.getLanguageFile().getMessage(
                                "Console.Vault.Unsuccessful"), true);
            }
        }

        // Metrics
        try {
            this.metrics = new Metrics(this);

            if (!metrics.isOptOut()) {
                Graphs graphs = new Graphs(metrics, this.config);

                graphs.createGraphs();

                if (metrics.start()) {
                    PluginLogger.info(Language.getLanguageFile().getMessage(
                            "Console.Metrics.Successful"));
                } else {
                    PluginLogger.warning(
                            Language.getLanguageFile().getMessage(
                                    "Console.Metrics.Unsuccessful"), true);
                }
            } else {
                PluginLogger.warning(Language.getLanguageFile().getMessage(
                        "Console.Metrics.Disabled"));
            }
        } catch (IOException e) {
            PluginLogger.warning(
                    Language.getLanguageFile().getMessage(
                            "Console.Metrics.Unsuccessful"), true);
        }

        // Load Groups
        this.groupManager = new GroupManager(groups);
        this.groupManager.loadGroups();

        PluginLogger.info(Language.getLanguageFile().getMessage(
                "Console.Loaded-Groups"));

        // Register command
        this.getCommand("ap").setExecutor(new APCommandExecutor(this));

        PluginLogger.info(Language.getLanguageFile().getMessage(
                "Console.Enabled",
                new String[] { "%Version%", this.getVersion() }));
    }

    @Override
    public void onDisable() {
        PluginLogger.info(Language.getLanguageFile().getMessage(
                "Console.Disabled",
                new String[] { "%Version%", this.getVersion() }));
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public WorldGuard getWorldGuard() {
        return this.worldGuard;
    }

    public Vault getVault() {
        return this.vault;
    }
}
