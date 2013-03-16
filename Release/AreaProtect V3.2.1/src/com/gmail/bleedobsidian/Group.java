package com.gmail.bleedobsidian;

public class Group
{
	public String Name;
	
	public int MaximumAreaRadius;
	public int MaximumAmountOfAreas;
	public boolean RadiusShouldIncludeHeight;
	public boolean FlagGreeting;
	public boolean FlagFarewell;
	public boolean FlagPvP;
	public boolean FlagChest_Access;
	public boolean FlagEntry;
	public boolean FixedSize;
	public int Height;
	public int Length;
	public int Width;
	public double Price;
	
	Group(String Name, int MaximumAreaRadius, int MaximumAmountOfAreas, boolean RadiusShouldIncludeHeight, boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry, boolean FixedSize, int Height, int Length, int Width, double Price)
	{
		this.Name = Name;
		
		this.MaximumAreaRadius = MaximumAreaRadius;
		this.MaximumAmountOfAreas = MaximumAmountOfAreas;
		this.RadiusShouldIncludeHeight = RadiusShouldIncludeHeight;
		this.FlagGreeting = FlagGreeting;
		this.FlagFarewell = FlagFarewell;
		this.FlagPvP = FlagPvP;
		this.FlagChest_Access = FlagChest_Access;
		this.FlagEntry = FlagEntry;
		this.FixedSize = FixedSize;
		this.Height = Height;
		this.Length = Length;
		this.Width = Width;
		this.Price = Price;
	}
}
