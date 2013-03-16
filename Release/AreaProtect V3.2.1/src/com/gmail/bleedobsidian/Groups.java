package com.gmail.bleedobsidian;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Groups
{
	public static ArrayList<Group> Groups = new ArrayList<Group>();
	
	public static void addGroup(String Name, int MaximumAreaRadius, int MaximumAmountOfAreas, boolean RadiusShouldIncludeHeight, boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry, boolean FixedSize, int Height, int Length, int Width, double Price)
	{
		Groups.add(new Group(Name, MaximumAreaRadius, MaximumAmountOfAreas, RadiusShouldIncludeHeight, FlagGreeting, FlagFarewell, FlagPvP, FlagChest_Access, FlagEntry, FixedSize, Height, Length, Width, Price));
	}
	
	public static Group getPlayersGroup(Player player) //Check if player belongs to a group and return it
	{
		for(int g = 0; g < Groups.size(); g++)
		{
			Group group = Groups.get(g);
			
			player.recalculatePermissions();
			
			if(player.hasPermission("areaprotect.group." + group.Name))
			{
				return group;
			}
		}
		
		return null;
	}
}
