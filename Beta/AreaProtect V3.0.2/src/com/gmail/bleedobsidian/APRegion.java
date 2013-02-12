package com.gmail.bleedobsidian;

import org.bukkit.entity.Player;

public class APRegion
{
	Regions regions = new Regions();
	
	public boolean apRegion(Player player)
	{
		if(check(player)){ return true; }
		
		int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
		
		if(checkRegion(player, playerRegionAnswer)){ return true; }
		
		player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Region Info \n");
		player.sendMessage(regions.getFlags(player));
		return true;
	}
	
	private boolean check(Player player)
	{
		if(player.hasPermission("areaprotect.ap.region"))
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
			return true;
		}
	}
	
	private boolean checkRegion(Player player, int playerRegionAnswer)
	{
		if(playerRegionAnswer == 1) //If player is not inside a region
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You must be inside an area.");
			return true;
		}
		else if(playerRegionAnswer == 2) //If player is not the owner
		{
			if(player.hasPermission("areaprotect.ap.region.other"))//If player is allowed to destroy areas they don't own
			{
				return false;
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
				return true;
			}
		}
		else if(playerRegionAnswer == 3) //If error
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
			return true;
		}
		else //If player is inside a region and owns it
		{
			if(player.hasPermission("areaprotect.ap.region"))
			{
				return false;
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
				return true;
			}
		}
	}
}
