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

package com.gmail.bleedobsidian.areaprotect.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.bleedobsidian.areaprotect.Selection;
import com.gmail.bleedobsidian.areaprotect.listeners.BlockListener;
import com.gmail.bleedobsidian.areaprotect.listeners.PlayerListener;
import com.gmail.bleedobsidian.areaprotect.managers.interfaces.SelectionListener;

public class SelectionManager {
    private BlockListener blockListener;
    private PlayerListener playerListener;

    private Map<SelectionListener, Player> pendingSelections = new HashMap<SelectionListener, Player>();

    public SelectionManager(JavaPlugin plugin) {
        this.blockListener = new BlockListener(this);
        this.playerListener = new PlayerListener(this);

        plugin.getServer().getPluginManager()
                .registerEvents(blockListener, plugin);
        plugin.getServer().getPluginManager()
                .registerEvents(playerListener, plugin);
    }

    public void addPendingSelection(SelectionListener listener, Player player) {
        this.pendingSelections.put(listener, player);
    }

    public void removePendingSelection(Player player) {
        if (this.pendingSelections.containsValue(player)) {
            for (Entry<SelectionListener, Player> entry : this.pendingSelections
                    .entrySet()) {
                if (entry.getValue().equals(player)) {
                    entry.getKey().selectionCanceled(player);
                    this.blockListener.removeSelections(player);
                    this.playerListener.removeSelections(player);
                    this.pendingSelections.remove(entry.getKey());
                }
            }
        }
    }

    public void selectionCreated(Player player, Selection selection) {
        for (Entry<SelectionListener, Player> entry : this.pendingSelections
                .entrySet()) {
            if (entry.getValue().equals(player)) {
                entry.getKey().selectionMade(player, selection, false);
                this.pendingSelections.remove(entry.getKey());
            }
        }
    }

    public boolean isPendingSelection(Player player) {
        return this.pendingSelections.containsValue(player);
    }
}
