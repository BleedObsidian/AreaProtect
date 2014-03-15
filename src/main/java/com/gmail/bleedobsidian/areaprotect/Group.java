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

public class Group {
    private String name;

    private final int MAXIMUM_AREAS;
    private final int MAXIMUM_HEIGHT;
    private final int MAXIMUM_LENGTH;
    private final int MAXIMUM_WIDTH;
    private final int MAXIMUM_RADIUS;
    private final boolean PAY_PER_BLOCK;
    private final double PRICE;

    private FlagSet defaultFlags;

    public Group(String name, int maximumAreas, int maximumHeight,
            int maximumLength, int maximumWidth, int maximumRadius,
            boolean payPerBlock, double price, FlagSet defaultFlags) {
        this.name = name;
        this.MAXIMUM_AREAS = maximumAreas;
        this.MAXIMUM_HEIGHT = maximumHeight;
        this.MAXIMUM_LENGTH = maximumLength;
        this.MAXIMUM_WIDTH = maximumWidth;
        this.MAXIMUM_RADIUS = maximumRadius;
        this.PAY_PER_BLOCK = payPerBlock;
        this.PRICE = price;

        this.defaultFlags = defaultFlags;
    }

    public String getName() {
        return this.name;
    }

    public int getMaximumAreas() {
        return this.MAXIMUM_AREAS;
    }

    public int getMaximumHeight() {
        return this.MAXIMUM_HEIGHT;
    }

    public int getMaximumLength() {
        return this.MAXIMUM_LENGTH;
    }

    public int getMaximumWidth() {
        return this.MAXIMUM_WIDTH;
    }

    public int getMaximumRadius() {
        return this.MAXIMUM_RADIUS;
    }

    public boolean isPayPerBlock() {
        return this.PAY_PER_BLOCK;
    }

    public double getPrice() {
        return this.PRICE;
    }

    public FlagSet getDefaultFlags() {
        return this.defaultFlags;
    }
}
