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

package com.gmail.bleedobsidian.areaprotect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.command.commands.AddMember;
import com.gmail.bleedobsidian.areaprotect.command.commands.AddOwner;
import com.gmail.bleedobsidian.areaprotect.command.commands.Cancel;
import com.gmail.bleedobsidian.areaprotect.command.commands.Create;
import com.gmail.bleedobsidian.areaprotect.command.commands.Destroy;
import com.gmail.bleedobsidian.areaprotect.command.commands.Flag;
import com.gmail.bleedobsidian.areaprotect.command.commands.Help;
import com.gmail.bleedobsidian.areaprotect.command.commands.Info;
import com.gmail.bleedobsidian.areaprotect.command.commands.RemoveMember;
import com.gmail.bleedobsidian.areaprotect.command.commands.RemoveOwner;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;

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
        } else if (args[0].equalsIgnoreCase("destroy")) {
            Destroy.destroy(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("cancel")) {
            Cancel.cancel(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("flag")) {
            Flag.flag(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("addmember")) {
            AddMember.addMember(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("removemember")) {
            RemoveMember.removeMember(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("addowner")) {
            AddOwner.addOwner(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("removeowner")) {
            RemoveOwner.removeOwner(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("info")) {
            Info.info(areaProtect, player, args);
        } else if (args[0].equalsIgnoreCase("help")) {
            Help.help(areaProtect, player, args);
        } else {
            return false;
        }

        return true;
    }
}
