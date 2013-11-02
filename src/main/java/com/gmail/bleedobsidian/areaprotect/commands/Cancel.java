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

import org.bukkit.entity.Player;

import com.gmail.bleedobsidian.areaprotect.AreaProtect;
import com.gmail.bleedobsidian.areaprotect.Language;
import com.gmail.bleedobsidian.areaprotect.configuration.LanguageFile;
import com.gmail.bleedobsidian.areaprotect.loggers.PlayerLogger;

public class Cancel {
    public static void cancel(AreaProtect areaProtect, Player player,
            String[] args) {
        LanguageFile language = Language.getLanguageFile();

        if (!player.hasPermission("areaprotect.ap.create")) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Permission"));
            return;
        }

        if (!areaProtect.getSelectionManager().isPendingSelection(player)) {
            PlayerLogger.message(player,
                    language.getMessage("Player.Cancel.Not-Creating"));
            return;
        }

        areaProtect.getSelectionManager().removePendingSelection(player);
        return;
    }
}
