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

package com.gmail.bleedobsidian.areaprotect.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.Selection;
import com.gmail.bleedobsidian.areaprotect.logger.PlayerLogger;
import com.gmail.bleedobsidian.areaprotect.managers.SelectionManager;

public class BlockListener implements Listener {
    private SelectionManager selectionManager;

    private Map<Player, Location> locations = new HashMap<Player, Location>();

    public BlockListener(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (this.selectionManager.isPendingSelection(player)) {
            if (locations.containsKey(player)) {
                Selection selection = new Selection(this.locations.get(player),
                        event.getBlock().getLocation());

                PlayerLogger.message(player, Language.getLanguageFile()
                        .getMessage("Player.Create.Select-2"));

                this.selectionManager.selectionCreated(player, selection);

                this.locations.remove(player);
                event.setCancelled(true);
            } else {
                PlayerLogger.message(player, Language.getLanguageFile()
                        .getMessage("Player.Create.Select-1"));

                this.locations.put(player, event.getBlock().getLocation());

                event.setCancelled(true);
            }
        }
    }

    public void removeSelections(Player player) {
        if (this.locations.containsKey(player)) {
            this.locations.remove(player);
        }
    }
}
