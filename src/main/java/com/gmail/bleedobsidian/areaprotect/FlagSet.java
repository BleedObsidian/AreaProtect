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

    public String getGreetingMessage() {
        return greetingMessage;
    }

    public void setGreetingMessage(String greetingMessage, String areaName) {
        this.greetingMessage = greetingMessage.replaceAll("%Area_Name%",
                areaName);
    }

    public String getFarewellMessage() {
        return farewellMessage;
    }

    public void setFarewellMessage(String farewellMessage, String areaName) {
        this.farewellMessage = farewellMessage.replaceAll("%Area_Name%",
                areaName);
    }
}
