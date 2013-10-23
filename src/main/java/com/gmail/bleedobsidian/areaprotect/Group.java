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
    private final int MAXIMUM_BLOCKS;
    private final boolean PAY_PER_BLOCK;
    private final double PRICE;

    private FlagSet defaultFlags;

    public Group(String name, int maximumAreas, int maximumBlocks,
            boolean payPerBlock, double price, FlagSet defaultFlags) {
        this.name = name;
        this.MAXIMUM_AREAS = maximumAreas;
        this.MAXIMUM_BLOCKS = maximumBlocks;
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

    public int getMaximumBlocks() {
        return this.MAXIMUM_BLOCKS;
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
