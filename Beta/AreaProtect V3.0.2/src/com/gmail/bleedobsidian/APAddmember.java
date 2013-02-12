package com.gmail.bleedobsidian;

import org.bukkit.entity.Player;

public class APAddmember
{
	Regions regions = new Regions();
	
	public boolean apAddmember(Player player, String[] args)
	{
		if(check(player, args)){ return true; }
		
		int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player);
		
		if(checkRegion(player, playerRegionAnswer, args)) { return true; }
		
		if(regions.addMember(player, args[1]))
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Added member: " + Config.VariableColour + args[1]);
			return true;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
			return true;
		}
	}
	
	private boolean check(Player player, String[] args)
	{
		if(args.length == 2)
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap addmember [player]");
			return true;
		}
	}
	
	private boolean checkRegion(Player player, int playerRegionAnswer, String[] args)
	{
		if(playerRegionAnswer == 1) //If player is not inside a region
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You must be inside an area.");
			return true;
		}
		else if(playerRegionAnswer == 2) //If player is not the owner
		{
			if(player.hasPermission("areaprotect.ap.addmember.other"))
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
			if(player.hasPermission("areaprotect.ap.addmember"))
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
