package com.gmail.bleedobsidian;

import org.bukkit.entity.Player;

public class APCreate
{
	Regions regions = new Regions();
	
	Player player;
	String[] args;
	
	int x;
	int y;
	int z;
	
	boolean enoughMoney = false;
	boolean bypass = false;
	
	public boolean apCreate(Player player, String[] args, int x, int y, int z)
	{
		this.player = player;
		this.args = args;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		Group gr;
		if((gr = Groups.getPlayersGroup(player)) != null) //in group
		{
			if(Config.UseBOSEconomy && Economy.BOSEloaded) //Check player has enough money
			{
				if(player.hasPermission("areaprotect.ap.create.BOSEBypass"))
				{
					bypass = true;
				}
				else
				{
					if(Economy.economy.getPlayerMoneyDouble(player.getDisplayName()) >= gr.Price)
					{
						enoughMoney = true;
					}
					else
					{
						player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have enough money");
						return true;
					}
				}
			}
			
			if(gr.RadiusShouldIncludeHeight)
			{
				if(check()){ return true; }
				if(GCheckRegion(gr)){ return true; }
				if(createRegionIncludingHeight(gr.FlagGreeting, gr.FlagFarewell, gr.FlagPvP, gr.FlagChest_Access, gr.FlagEntry)){ return true; }
			}
			else if(gr.FixedSize)
			{
				if(checkFixed()){ return true; }
				if(GCheckRegionFixed(gr)){ return true; }
				if(createRegionFixed(gr.Height, gr.Length, gr.Width, gr.FlagGreeting, gr.FlagFarewell, gr.FlagPvP, gr.FlagChest_Access, gr.FlagEntry)){ return true; }
			}
			else
			{
				if(check()){ return true; }
				if(GCheckRegion(gr)){ return true; }
				if(createRegion(gr.FlagGreeting, gr.FlagFarewell, gr.FlagPvP, gr.FlagChest_Access, gr.FlagEntry)){ return true; }
			}
			
			if(enoughMoney)
			{
				double originalMoney = Economy.economy.getPlayerMoneyDouble(player.getDisplayName());
				double newMoney = originalMoney - gr.Price;
				Economy.economy.setPlayerMoney(player.getDisplayName(), newMoney, true);
					
				player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " " + gr.Price + Config.SuccessColour + " has been taken from your account.");
			}
			
			return true;
		}
		else //Not In group
		{
			if(Config.UseBOSEconomy && Economy.BOSEloaded) //Check player has enough money
			{
				if(player.hasPermission("areaprotect.ap.create.BOSEBypass"))
				{
					bypass = true;
				}
				else
				{
					if(Economy.economy.getPlayerMoneyDouble(player.getDisplayName()) >= Config.DefaultPrice || player.hasPermission("areaprotect.ap.create.BOSEBypass"))
					{
						enoughMoney = true;
					}
					else
					{
						player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You don't have enough money");
						return true;
					}
				}
			}
			
			if(Config.DefaultRadiusShouldIncludeHeight)
			{
				if(check()){ return true; }
				if(NGCheckRegion()){ return true; }
				if(createRegionIncludingHeight(Config.DefaultFlagGreeting, Config.DefaultFlagFarewell, Config.DefaultFlagPvP, Config.DefaultFlagChest_Access, Config.DefaultFlagEntry)){ return true; }
			}
			else if(Config.DefaultFixedSize)
			{
				if(checkFixed()){ return true; }
				if(NGCheckRegionFixed()){ return true; }
				if(createRegionFixed(Config.DefaultHeight, Config.DefaultLength, Config.DefaultWidth, Config.DefaultFlagGreeting, Config.DefaultFlagFarewell, Config.DefaultFlagPvP, Config.DefaultFlagChest_Access, Config.DefaultFlagEntry)){ return true; }
			}
			else
			{
				if(check()){ return true; }
				if(NGCheckRegion()){ return true; }
				if(createRegion(Config.DefaultFlagGreeting, Config.DefaultFlagFarewell, Config.DefaultFlagPvP, Config.DefaultFlagChest_Access, Config.DefaultFlagEntry)){ return true; }
			}
			
			if(enoughMoney)
			{
				if(bypass)
				{
					
				}
				else
				{
					double originalMoney = Economy.economy.getPlayerMoneyDouble(player.getDisplayName());
					double newMoney = originalMoney - Config.DefaultPrice;
					Economy.economy.setPlayerMoney(player.getDisplayName(), newMoney, true);
						
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " " + Config.DefaultPrice + Config.SuccessColour + " has been taken from your account.");
				}
			}
			
			return true;
		}
	}
	
	private boolean check()
	{
		if(player.hasPermission("areaprotect.ap.create"))
		{
			if(args.length >= 3)
			{
				if(Functions.isNumeric(args[1])) //If name is a number
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " " + args[1] + Config.ErrorColour + " is not a name.");
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap create [name] [radius]");
				return true;
			}
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
			return true;
		}
	}
	
	private boolean checkFixed()
	{
		if(player.hasPermission("areaprotect.ap.create"))
		{
			if(args.length == 2)
			{
				if(Functions.isNumeric(args[1])) //If name is a number
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.VariableColour + " " + args[1] + Config.ErrorColour + " is not a name.");
					return true;
				}
				else
				{
					return false; //Ok to carry on
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The area size is fixed, You can not choose a radius.");
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap create [name]");
				return true;
			}
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You do not have permission to use this command.");
			return true;
		}
	}
	
	private boolean GCheckRegion(Group g)
	{
		if(regions.getRegionCount(player) < g.MaximumAmountOfAreas || player.hasPermission("areaprotect.ap.create.bypass.maxareas"))
		{
			if(args.length == 3)
			{
				if(Integer.parseInt(args[2]) <= g.MaximumAreaRadius || player.hasPermission("areaprotect.ap.create.bypass.maxradius"))
				{
					return false;
				}
				else
				{
					player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The radius is too big, Max: " + Config.VariableColour + g.MaximumAreaRadius);
					return true;
				}
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Usage: " + Config.VariableColour + "/ap create [name] [radius]");
				return true;
			}
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You have reached your area limit.");
			return true;
		}
	}
	
	private boolean GCheckRegionFixed(Group g)
	{
		if(!(regions.getRegionCount(player) < g.MaximumAmountOfAreas || player.hasPermission("areaprotect.ap.create.bypass.maxareas")))
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You have reached your area limit.");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean NGCheckRegion()
	{
		if(regions.getRegionCount(player) < Config.DefaultMaximumAmountOfAreas || player.hasPermission("areaprotect.ap.create.bypass.maxareas"))
		{
			if(Integer.parseInt(args[2]) <= Config.DefaultMaximumAreaRadius || player.hasPermission("areaprotect.ap.create.bypass.maxradius"))
			{
				return false;
			}
			else
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The radius is too big, Max: " + Config.VariableColour + Config.DefaultMaximumAreaRadius);
				return true;
			}
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You have reached your area limit.");
			return true;
		}
	}
	
	private boolean NGCheckRegionFixed()
	{
		if(!(regions.getRegionCount(player) < Config.DefaultMaximumAmountOfAreas || player.hasPermission("areaprotect.ap.create.bypass.maxareas")))
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You have reached your area limit.");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean createRegion(boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry)
	{
		int regionAnswer = regions.createRegion(player, args[2], x, z, args[1], FlagGreeting, FlagFarewell, FlagPvP, FlagChest_Access, FlagEntry); //Create region and get answer
		
		if(regionAnswer == 0)//If the region is created successfully continue
		{
			player.sendMessage(Config.AliasColour + Config.Alias + " " + Config.VariableColour + args[1] + Config.SuccessColour + " has been created.");
			return false; //Region created
		}
		else if(regionAnswer == 1) //If region overlaps
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Area overlaps with another area you don't own.");
			return true;
		}
		else if(regionAnswer == 2) //If region couldn't save
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The creation of " + Config.VariableColour + args[1] + Config.ErrorColour + " failed.");
			return true;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The creation of " + Config.VariableColour + args[1] + Config.ErrorColour + " failed.");
			return true;
		}
	}
	
	private boolean createRegionIncludingHeight(boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry)
	{
		int regionAnswer = regions.createRegionIncludingHeight(player, args[2], x, y, z, args[1], FlagGreeting, FlagFarewell, FlagPvP, FlagChest_Access, FlagEntry); //Create region and get answer
		
		if(regionAnswer == 0)//If the region is created successfully continue
		{
			player.sendMessage(Config.AliasColour + Config.Alias + " " + Config.VariableColour + args[1] + Config.SuccessColour + " has been created.");
			return false; //Region created
		}
		else if(regionAnswer == 1) //If region overlaps
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Area overlaps with another area you don't own.");
			return true;
		}
		else if(regionAnswer == 2) //If region couldn't save
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The creation of " + Config.VariableColour + args[1] + Config.ErrorColour + " failed.");
			return true;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The creation of " + Config.VariableColour + args[1] + Config.ErrorColour + " failed.");
			return true;
		}
	}
	
	private boolean createRegionFixed(int h, int l, int w, boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry)
	{
		int regionAnswer = regions.createRegionFixed(player, x, y, z, args[1], h, l, w, FlagGreeting, FlagFarewell, FlagPvP, FlagChest_Access, FlagEntry); //Create region and get answer
		
		if(regionAnswer == 0)//If the region is created successfully continue
		{
			player.sendMessage(Config.AliasColour + Config.Alias + " " + Config.VariableColour + args[1] + Config.SuccessColour + " has been created.");
			return false; //Region created
		}
		else if(regionAnswer == 1) //If region overlaps
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Area overlaps with another area you don't own.");
			return true;
		}
		else if(regionAnswer == 2) //If region couldn't save
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The creation of " + Config.VariableColour + args[1] + Config.ErrorColour + " failed.");
			return true;
		}
		else
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " The creation of " + Config.VariableColour + args[1] + Config.ErrorColour + " failed.");
			return true;
		}
	}
}
