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

import com.gmail.bleedobsidian.areaprotect.api.Vault;
import com.gmail.bleedobsidian.areaprotect.api.WorldEdit;
import com.gmail.bleedobsidian.areaprotect.api.WorldGuard;
import com.gmail.bleedobsidian.areaprotect.command.APCommandExecutor;
import com.gmail.bleedobsidian.areaprotect.configurations.ConfigFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PluginLogger;
import com.gmail.bleedobsidian.areaprotect.managers.GroupManager;
import com.gmail.bleedobsidian.areaprotect.managers.SelectionManager;
import com.gmail.bleedobsidian.areaprotect.metrics.Graphs;
import com.gmail.bleedobsidian.areaprotect.metrics.Metrics;

public class AreaProtect extends JavaPlugin {
    private ConfigFile config;
    private ConfigFile groups;

    private GroupManager groupManager;
    private SelectionManager selectionManager;

    private WorldEdit worldEdit;
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

        if (locale != null) {
            if (Language.exists(locale)) {
                Language.setLangauge(locale + ".yml", this);
            } else {
                PluginLogger.warning("Failed to find locale: " + locale
                        + ", using en-us instead.", true);
                Language.setLangauge("en-us.yml", this);
            }
        } else {
            PluginLogger
                    .warning(
                            "Old AreaProtect config file detected, please delete it and restart the server.",
                            true);
            return;
        }

        // Check For Update
        if (this.config.getFileConfiguration().getBoolean(
                "Updates.Check-For-Update")) {
            String apiKey = this.config.getFileConfiguration().getString(
                    "Updates.API-Key");
            Updater updater;

            if (apiKey != null) {
                if (apiKey.equals("")
                        || apiKey.equalsIgnoreCase("your_api_key")) {
                    updater = new Updater(this, 46167);
                } else {
                    updater = new Updater(this, 46167, apiKey);
                }
            } else {
                updater = new Updater(this, 46167);
            }

            if (updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE) {
                PluginLogger
                        .warning("A new version of AreaProtect is available!");
            } else if (updater.getResult() != Updater.UpdateResult.NO_UPDATE) {
                if (updater.getResult() == Updater.UpdateResult.ERROR_APIKEY) {
                    PluginLogger.warning("Failed to check for updates!", true);
                    PluginLogger.warning("Your API key may be wrong.", true);
                } else if (updater.getResult() == Updater.UpdateResult.ERROR_SERVER) {
                    PluginLogger.warning("Failed to check for updates!", true);
                    PluginLogger.warning("Failed to connect to server!", true);
                } else if (updater.getResult() == Updater.UpdateResult.ERROR_VERSION) {
                    PluginLogger.warning("Failed to check for updates!", true);
                    PluginLogger.warning("Invalid data in output!", true);
                }
            }
        }

        // Load WorldEdit
        this.worldEdit = new WorldEdit();

        if (this.worldEdit.load(this)) {
            PluginLogger.info(Language.getLanguageFile().getMessage(
                    "Console.WorldEdit.Successful"));
        } else {
            PluginLogger.error(Language.getLanguageFile().getMessage(
                    "Console.WorldEdit.Unsuccessful"));
            return;
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
                this.vault = null;
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

        // Create selection manager
        this.selectionManager = new SelectionManager(this);

        // Register command
        this.getCommand("ap").setExecutor(new APCommandExecutor(this));

        PluginLogger.info(Language.getLanguageFile().getMessage(
                "Console.Enabled",
                new String[] { "%Version%", this.getVersion() }));
    }

    @Override
    public void onDisable() {
        if (Language.getLanguageFile() != null) {
            PluginLogger.info(Language.getLanguageFile().getMessage(
                    "Console.Disabled",
                    new String[] { "%Version%", this.getVersion() }));
        }
    }

    public GroupManager getGroupManager() {
        return this.groupManager;
    }

    public SelectionManager getSelectionManager() {
        return this.selectionManager;
    }

    public String getVersion() {
        return this.getDescription().getVersion();
    }

    public WorldEdit getWorldEdit() {
        return this.worldEdit;
    }

    public WorldGuard getWorldGuard() {
        return this.worldGuard;
    }

    public Vault getVault() {
        return this.vault;
    }
}
