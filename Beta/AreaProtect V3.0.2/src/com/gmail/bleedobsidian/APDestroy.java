package com.gmail.bleedobsidian;

import org.bukkit.entity.Player;

public class APDestroy
{
	Regions regions = new Regions();
	
	public boolean apDestroy(Player player, String[] args)
	{
		if(check(player)){ return true; }
		
		int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
		
		if(checkRegion(player, playerRegionAnswer, args)){ return true; }
		
		if(args.length == 2)
		{
			String name = player.getDisplayName() + "-" + args[1];
			
			int Answer = regions.destroyRegionByName(player, name);
			
			if(Answer == 3)
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Region destroyed.");
				return true;
			}
			else if(Answer == 2)
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error destroying region.");
				return true;
			}
			else
			{
				return true;
			}
		}
		else
		{
			if(regions.destroyRegion(player))
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Region destroyed.");
				return true;
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error destroying region.");
				return true;
			}
		}
	}
	
	private boolean check(Player player)
	{
		if(player.hasPermission("areaprotect.ap.destroy"))
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
			return true;
		}
	}
	
	private boolean checkRegion(Player player, int playerRegionAnswer, String[] args)
	{
		if(args.length == 2)
		{
			return false;
		}
		else
		{
			if(playerRegionAnswer == 1) //If player is not inside a region
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You must be inside an area or use " + Config.VariableColour + "/ap destroy [name]");
				return true;
			}
			else if(playerRegionAnswer == 2) //If player is not the owner
			{
				if(player.hasPermission("areaprotect.ap.destroy.other"))//If player is allowed to destroy areas they don't own
				{
					return false;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this region.");
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
				return false;
			}
		}
	}
}
