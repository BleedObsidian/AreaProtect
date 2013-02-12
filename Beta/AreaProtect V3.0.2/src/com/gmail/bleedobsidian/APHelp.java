package com.gmail.bleedobsidian;

import org.bukkit.entity.Player;

public class APHelp
{
	public boolean apHelp(Player player, String[] args)
	{
		if(check(player)){ return true; }
		
		if(args.length == 1)
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- AreaProtect Help -----");
			player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap help [category]");
			player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Categories: ");
				if(player.hasPermission("areaprotect.ap.help.areas")) {player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Areas"); }
				if(player.hasPermission("areaprotect.ap.help.members")) {player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Members"); }
				if(player.hasPermission("areaprotect.ap.help.flags")) {player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Flags"); }
				if(player.hasPermission("areaprotect.ap.help.commands")) {player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Commands - All AreaProtect Commands"); }
			player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----------");
			
			return true;
		}
		else
		{
			if(args[1].equalsIgnoreCase("flags"))
			{
				if(player.hasPermission("areaprotect.ap.help.flags"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- AreaProtect Flags Help -----");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Stand in the area you would like to edit the flags of.");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap flag [flag] [value]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Flags: ");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Greeting [message/off] - The greeting message");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Farewell [message/off] - The farewell message");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   PvP [allow/deny] - Enable/Disable PvP");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Chest-Access [allow/deny] - Allow/Deny non-owners to access chests");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   Entry [allow/deny] - Allow/Deny non-owners to enter this area");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----------");
					
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to view category: " + Config.VariableColour + "flags");
					return true;
				}
			}
			else if(args[1].equalsIgnoreCase("members"))
			{
				if(player.hasPermission("areaprotect.ap.help.members"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- AreaProtect Members Help -----");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Members are people who can build in your area.");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Stand in the area you would like to add/remove a member from.");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap addmember [player]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap removemember [player]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----------");
					
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to view category: " + Config.VariableColour + "members");
					return true;
				}
			}
			else if(args[1].equalsIgnoreCase("areas"))
			{
				if(player.hasPermission("areaprotect.ap.help.areas"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- AreaProtect Areas Help -----");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas are regions of land that only you and 'Members' can build on.");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Stand in the middle of the place you want to protect and work out the radius.");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap create [name] [radius]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " To destroy an area stand in it or destroy by name.");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap destroy");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap destroy [name]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----------");
					
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to view category: " + Config.VariableColour + "areas");
					return true;
				}
			}
			else if(args[1].equalsIgnoreCase("commands"))
			{
				if(player.hasPermission("areaprotect.ap.help.commands"))
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- AreaProtect Commands -----");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap create [name] [radius]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap destroy");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap addmember [player]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap removemember [player]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap flag [flag] [value]");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap info (Optional [player])");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " /ap region");
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----------");
					
					return true;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to view category: " + Config.VariableColour + "commands");
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Unkown Category: " + Config.VariableColour + args[1]);
				return true;
			}
		}
	}
	
	private boolean check(Player player)
	{
		if(player.hasPermission("areaprotect.ap.help"))
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
