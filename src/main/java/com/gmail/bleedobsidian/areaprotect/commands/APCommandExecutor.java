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

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.api.Vault;
import com.gmail.bleedobsidian.areaprotect.api.WorldGuard;

public class APCommandExecutor implements CommandExecutor {
    private AreaProtect areaProtect;

    private WorldGuard worldGuard;
    private Vault vault;

    public APCommandExecutor(AreaProtect areaProtect) {
        this.areaProtect = areaProtect;

        this.worldGuard = areaProtect.getWorldGuard();
        this.vault = areaProtect.getVault();
    }

    public boolean onCommand(CommandSender sender, Command command,
            String label, String[] args) {
        return false;
    }

}
