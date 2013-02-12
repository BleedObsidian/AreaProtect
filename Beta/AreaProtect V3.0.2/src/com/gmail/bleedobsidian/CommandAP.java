package com.gmail.bleedobsidian;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAP implements CommandExecutor
{
	APCreate APCreate = new APCreate();
	APDestroy APDestroy = new APDestroy();
	APInfo APInfo = new APInfo();
	APFlag APFlag = new APFlag();
	APRegion APRegion = new APRegion();
	APAddmember APAddmember = new APAddmember();
	APRemovemember APRemovemember = new APRemovemember();
	APHelp APHelp = new APHelp();
	
	Player player;
	World world;
	
	int x;
	int y;
	int z;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length > 0)
		{
			if(sender instanceof Player)
			{
				player = (Player) sender;
				world = player.getWorld();
				
				x = player.getLocation().getBlockX();
				y = player.getLocation().getBlockY();
				z = player.getLocation().getBlockZ();
			}
			
			if(args[0].equalsIgnoreCase("create")){ return apCreate(player, args); }
			else if(args[0].equalsIgnoreCase("destroy")){ return apDestroy(player, args); }
			else if(args[0].equalsIgnoreCase("flag")){ return apFlag(player, args); }
			else if(args[0].equalsIgnoreCase("addmember")){ return apAddmember(player, args); }
			else if(args[0].equalsIgnoreCase("removemember")){ return apRemovemember(player, args); }
			else if(args[0].equalsIgnoreCase("info")){ return apInfo(player, args); }
			else if(args[0].equalsIgnoreCase("region")){ return apRegion(player); }
			else if(args[0].equalsIgnoreCase("help")){ return apHelp(player, args); }
			else { return false; }
		}
		else
		{
			sender.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Invalid Command. See /ap help");
			return true;
		}
	}
	
	private boolean apCreate(Player player, String[] args)
	{
		return APCreate.apCreate(player, args, x, y, z); //Run command
	}
	
	private boolean apDestroy(Player player, String[] args)
	{
		return APDestroy.apDestroy(player, args); //Run command
	}
	
	private boolean apFlag(Player player, String[] args)
	{
		return APFlag.apFlag(player, args);
	}
	
	private boolean apAddmember(Player player, String[] args)
	{
		return APAddmember.apAddmember(player, args);
	}
	
	private boolean apRemovemember(Player player, String[] args)
	{
		return APRemovemember.apRemovemember(player, args);
	}
	
	private boolean apInfo(Player player, String[] args)
	{
		return APInfo.apInfo(player, args);
	}
	
	private boolean apRegion(Player player)
	{
		return APRegion.apRegion(player);
	}
	
	private boolean apHelp(Player player, String[] args)
	{
		return APHelp.apHelp(player, args);
	}
}
