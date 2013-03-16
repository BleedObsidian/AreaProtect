package com.gmail.bleedobsidian;

import org.bukkit.entity.Player;

public class APFlag
{
	Regions regions = new Regions();
	
	public boolean apFlag(Player player, String[] args)
	{
		int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player);
		
		if(checkRegion(player, playerRegionAnswer, args)) { return true; }
		
		if(args[1].equalsIgnoreCase("greeting")) //Flag GREETING
		{
			if(checkGreeting(player, args)) { return true; }
			
			if(playerRegionAnswer == 2)
			{
				if(!player.hasPermission("areaprotect.ap.flag.greeting.other"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this area.");
					return true;
				}
			}
			else
			{
				if(!player.hasPermission("areaprotect.ap.flag.greeting"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have permission to set flag:" + Config.VariableColour + " greeting");
					return true;
				}
			}
			
			if(regions.editFlagGreeting(player, getMessage(args)))
			{
				if(!args[2].equalsIgnoreCase("off"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Flag Set");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Flag Disabled");
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
				return true;
			}
		}
		else if(args[1].equalsIgnoreCase("farewell")) //Flag FAREWELL
		{
			if(checkFarewell(player, args)) { return true; }
			
			if(playerRegionAnswer == 2)
			{
				if(!player.hasPermission("areaprotect.ap.flag.farewell.other"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this region.");
					return true;
				}
			}
			else
			{
				if(!player.hasPermission("areaprotect.ap.flag.farewell"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have permission to set flag:" + Config.VariableColour + " farewell");
					return true;
				}
			}
			
			if(regions.editFlagFarewell(player, getMessage(args)))
			{
				if(!args[2].equalsIgnoreCase("off"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Flag Set");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Flag Disabled");
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
				return true;
			}
		}
		else if(args[1].equalsIgnoreCase("pvp")) //Flag PvP
		{
			if(checkPVP(player, args)) { return true; }
			
			if(playerRegionAnswer == 2)
			{
				if(!player.hasPermission("areaprotect.ap.flag.pvp.other"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this region.");
					return true;
				}
			}
			else
			{
				if(!player.hasPermission("areaprotect.ap.flag.pvp"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have permission to set flag:" + Config.VariableColour + " pvp");
					return true;
				}
			}
			
			if(args[2].equalsIgnoreCase("allow"))
			{
				if(regions.enablePVP(player))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " PvP enabled");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
					return true;
				}
			}
			else if(args[2].equalsIgnoreCase("deny"))
			{
				if(regions.disablePVP(player))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " PvP disabled");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag pvp [allow/deny]");
				return true;
			}
		}
		else if(args[1].equalsIgnoreCase("chest-access")) //Flag CHEST ACCESS
		{
			if(checkChest_Access(player, args)) { return true; }
			
			if(playerRegionAnswer == 2)
			{
				if(!player.hasPermission("areaprotect.ap.flag.chestaccess.other"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this region.");
					return true;
				}
			}
			else
			{
				if(!player.hasPermission("areaprotect.ap.flag.chestaccess"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have permission to set flag:" + Config.VariableColour + " chest-access");
					return true;
				}
			}
			
			if(args[2].equalsIgnoreCase("allow"))
			{
				if(regions.enableChest_Access(player))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Chest-Access allowed");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
					return true;
				}
			}
			else if(args[2].equalsIgnoreCase("deny"))
			{
				if(regions.disableChest_Access(player))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Chest-Access denied");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag chest-access [allow/deny]");
				return true;
			}
		}
		else if(args[1].equalsIgnoreCase("entry")) //Flag ENTRY
		{
			if(checkEntry(player, args)) { return true; }
			
			if(playerRegionAnswer == 2)
			{
				if(!player.hasPermission("areaprotect.ap.flag.entry.other"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this region.");
					return true;
				}
			}
			else
			{
				if(!player.hasPermission("areaprotect.ap.flag.entry"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have permission to set flag:" + Config.VariableColour + " entry");
					return true;
				}
			}
			
			if(args[2].equalsIgnoreCase("allow"))
			{
				if(regions.enableEntry(player))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Entry allowed");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
					return true;
				}
			}
			else if(args[2].equalsIgnoreCase("deny"))
			{
				if(regions.disableEntry(player))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Entry denied");
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Error.");
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag entry [allow/deny]");
				return true;
			}
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag [flag] [value]");
			player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap helpflags" + Config.ErrorColour + " for more.");
			return true;
		}
	}
	
	private boolean checkRegion(Player player, int playerRegionAnswer, String[] args)
	{
		if(args.length >= 2)
		{
			if(playerRegionAnswer == 1) //If player is not inside a region
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You must be inside an area.");
				return true;
			}
			else if(playerRegionAnswer == 2) //If player is not the owner
			{
				return false;
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
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag [flag] [value]");
			player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap help flags" + Config.ErrorColour + " for more.");
			return true;
		}
	}
	
	private boolean checkGreeting(Player player, String[] args)
	{
		if(args.length >= 3)
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag greeting [message/off]");
			return true;
		}
	}
	
	private boolean checkFarewell(Player player, String[] args)
	{
		if(args.length >= 3)
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag farewell [message/off]");
			return true;
		}
	}
	
	private boolean checkPVP(Player player, String[] args)
	{
		if(args.length == 3)
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag pvp [allow/deny]");
			return true;
		}
	}
	
	private boolean checkChest_Access(Player player, String[] args)
	{
		if(args.length == 3)
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag chest-access [allow/deny]");
			return true;
		}
	}
	
	private boolean checkEntry(Player player, String[] args)
	{
		if(args.length == 3)
		{
			return false;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap flag entry [allow/deny]");
			return true;
		}
	}
	
	private String getMessage(String[] args)
	{
		String newMessage = "";
		for (int i = 2; i < args.length; i++)
		{
				newMessage = (new StringBuilder(String.valueOf(newMessage))).append(args[i]).append(i == args.length - 2 ? " " : " ").toString();
		}
		return newMessage;
	}
}
