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

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.logger.PlayerLogger;

public class APCommandExecutor implements CommandExecutor {
    private AreaProtect areaProtect;

    public APCommandExecutor(AreaProtect areaProtect) {
        this.areaProtect = areaProtect;
    }

    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (this.proccessCommand(player, args)) {
                return true;
            } else {
                PlayerLogger.message(player, Language.getLanguageFile()
                        .getMessage("Player.Syntax-Error"));
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean proccessCommand(Player player, String[] args) {
        if (args[0].equalsIgnoreCase("create")) {
            Create.create(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("cancel")) {
            Cancel.cancel(areaProtect, player, args);
        } else {
            return false;
        }

        return true;
    }
}
