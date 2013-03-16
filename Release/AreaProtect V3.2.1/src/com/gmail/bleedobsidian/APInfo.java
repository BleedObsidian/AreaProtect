package com.gmail.bleedobsidian;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.BlockVector2D;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class APInfo
{
	Regions regions = new Regions();
	
	Player subject;
	
	public boolean apInfo(Player player, String[] args)
	{
		Group gr;
		
		if(check(player, args)) { return true; }
		
		player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- Info -----");
		
		if(args.length == 2)
		{
			if(getPlayer(player, args)) { return true; }
			
			player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Player: " + subject.getDisplayName());
			
			if((gr = Groups.getPlayersGroup(subject)) != null) //in group
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Group: " + Config.VariableColour + gr.Name);
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas: "  + Config.VariableColour + regions.getRegionCount(subject) + "/" + gr.MaximumAmountOfAreas);
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Max Radius: "  + Config.VariableColour + gr.MaximumAreaRadius);
				
				if(Config.UseBOSEconomy && Economy.BOSEloaded)
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Area Price: "  + Config.VariableColour + gr.Price);
				}
				
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas:");
				
				getAreas(subject);
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Group: " + Config.VariableColour + "Default");
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas: "  + Config.VariableColour + regions.getRegionCount(subject) + "/" + Config.DefaultMaximumAmountOfAreas);
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Max Radius: "  + Config.VariableColour + Config.DefaultMaximumAreaRadius);
				
				if(Config.UseBOSEconomy && Economy.BOSEloaded)
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Area Price: "  + Config.VariableColour + Config.DefaultPrice);
				}
				
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas:");
				
				getAreas(subject);
			}
			
			return true;
		}
		else
		{
			if((gr = Groups.getPlayersGroup(player)) != null) //in group
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Group: " + Config.VariableColour + gr.Name);
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas: "  + Config.VariableColour + regions.getRegionCount(player) + "/" + gr.MaximumAmountOfAreas);
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Max Radius: "  + Config.VariableColour + gr.MaximumAreaRadius);
				
				if(Config.UseBOSEconomy && Economy.BOSEloaded)
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Area Price: "  + Config.VariableColour + gr.Price);
				}
				
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas:");
				
				getAreas(player);
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Group: " + Config.VariableColour + "Default");
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas: "  + Config.VariableColour + regions.getRegionCount(player) + "/" + Config.DefaultMaximumAmountOfAreas);
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Max Radius: "  + Config.VariableColour + Config.DefaultMaximumAreaRadius);
				
				if(Config.UseBOSEconomy && Economy.BOSEloaded)
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Area Price: "  + Config.VariableColour + Config.DefaultPrice);
				}
				
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + " Areas:");
				
				getAreas(player);
			}
			
			return true;
		}
	}
	
	private boolean check(Player player, String[] args)
	{
		if(args.length == 1)
		{
			if(player.hasPermission("areaprotect.ap.info"))
			{
				return false;
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
				return true;
			}
		}
		else if(args.length == 2)
		{
			if(player.hasPermission("areaprotect.ap.info.other"))
			{
				return false;
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
				return true;
			}
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap info [Player(Optinal)]");
			return true;
		}
	}
	
	private boolean getPlayer(Player player, String[] args)
	{
		subject = Bukkit.getPlayer(args[1]);
		
		if(subject == null)
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Can't get player.");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private void getAreas(Player player)
	{
		Map<String, ProtectedRegion> areas = regions.getAreas(player);
		List<ProtectedRegion> list = new ArrayList<ProtectedRegion>(areas.values());
		List<BlockVector2D> points;
		
		for(int a = 0; a < list.size(); a++)
		{
			String areaname = list.get(a).getId();
			String[] areanameArray = areaname.split("-");
			
			points = list.get(a).getPoints();
			
			if(areanameArray[0].equalsIgnoreCase(player.getDisplayName()))
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "   " + areanameArray[1] + ":");
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "     X: " + points.get(0).getX());
				player.sendMessage(Config.AliasColour + Config.Alias + Config.SuccessColour + "     Z: " + points.get(0).getZ());
			}
		}
	}
}
