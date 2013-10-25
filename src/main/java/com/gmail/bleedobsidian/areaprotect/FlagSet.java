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

import java.util.HashMap;
import java.util.Map;

import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

public class FlagSet {
    private boolean greeting;
    private boolean farewell;
    private boolean pvp;
    private boolean chestAccess;
    private boolean entry;
    private boolean sendChat;
    private boolean receiveChat;
    private boolean use;
    private boolean mobDamage;
    private boolean mobSpawning;
    private boolean creeperExplosion;
    private boolean endermanGrief;

    private String greetingMessage;
    private String farewellMessage;

    public Map<Flag<?>, Object> toWorldGuardFlags(String areaName) {
        Map<Flag<?>, Object> flags = new HashMap<Flag<?>, Object>();

        flags.put(DefaultFlag.PVP, this.toState(pvp));
        flags.put(DefaultFlag.CHEST_ACCESS, this.toState(chestAccess));
        flags.put(DefaultFlag.ENTRY, this.toState(entry));
        flags.put(DefaultFlag.SEND_CHAT, this.toState(sendChat));
        flags.put(DefaultFlag.RECEIVE_CHAT, this.toState(receiveChat));
        flags.put(DefaultFlag.USE, this.toState(use));
        flags.put(DefaultFlag.MOB_DAMAGE, this.toState(mobDamage));
        flags.put(DefaultFlag.MOB_SPAWNING, this.toState(mobSpawning));
        flags.put(DefaultFlag.CREEPER_EXPLOSION, this.toState(creeperExplosion));
        flags.put(DefaultFlag.ENDER_BUILD, this.toState(endermanGrief));

        if (greeting) {
            flags.put(DefaultFlag.GREET_MESSAGE,
                    this.getGreetingMessage(areaName));
        }

        if (farewell) {
            flags.put(DefaultFlag.FAREWELL_MESSAGE,
                    this.getFarewellMessage(areaName));
        }

        return flags;
    }

    public boolean isGreeting() {
        return greeting;
    }

    public void setGreeting(boolean greeting) {
        this.greeting = greeting;
    }

    public boolean isFarewell() {
        return farewell;
    }

    public void setFarewell(boolean farewell) {
        this.farewell = farewell;
    }

    public boolean isPVP() {
        return pvp;
    }

    public void setPVP(boolean pvp) {
        this.pvp = pvp;
    }

    public boolean isChestAccess() {
        return chestAccess;
    }

    public void setChestAccess(boolean chestAccess) {
        this.chestAccess = chestAccess;
    }

    public boolean isEntry() {
        return entry;
    }

    public void setEntry(boolean entry) {
        this.entry = entry;
    }

    public boolean isSendChat() {
        return sendChat;
    }

    public void setSendChat(boolean sendChat) {
        this.sendChat = sendChat;
    }

    public boolean isReceiveChat() {
        return receiveChat;
    }

    public void setReceiveChat(boolean receiveChat) {
        this.receiveChat = receiveChat;
    }

    public boolean isUse() {
        return use;
    }

    public void setUse(boolean use) {
        this.use = use;
    }

    public boolean isMobDamage() {
        return mobDamage;
    }

    public void setMobDamage(boolean mobDamage) {
        this.mobDamage = mobDamage;
    }

    public boolean isMobSpawning() {
        return mobSpawning;
    }

    public void setMobSpawning(boolean mobSpawning) {
        this.mobSpawning = mobSpawning;
    }

    public boolean isCreeperExplosion() {
        return creeperExplosion;
    }

    public void setCreeperExplosion(boolean creeperExplosion) {
        this.creeperExplosion = creeperExplosion;
    }

    public boolean isEndermanGrief() {
        return endermanGrief;
    }

    public void setEndermanGrief(boolean endermanGrief) {
        this.endermanGrief = endermanGrief;
    }

    public String getGreetingMessage(String areaName) {
        return greetingMessage.replaceAll("%Area_Name%", areaName);
    }

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = "&b[AreaProtect]: " + greetingMessage;
    }

    public String getFarewellMessage(String areaName) {
        return farewellMessage.replaceAll("%Area_Name%", areaName);
    }

    public void setFarewellMessage(String farewellMessage) {
        this.farewellMessage = "&b[AreaProtect]: " + farewellMessage;
    }

    private State toState(boolean bool) {
        if (bool) {
            return State.ALLOW;
        } else {
            return State.DENY;
        }
    }
}
