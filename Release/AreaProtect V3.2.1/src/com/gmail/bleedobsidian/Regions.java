package com.gmail.bleedobsidian;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class Regions
{
	ApplicableRegionSet set;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int createRegion(Player player, String r, int x, int z, String name, boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry)
	{
		try
		{
			int radius = Integer.parseInt(r);
			
			RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
			
			LocalPlayer LPlayer = getWorldGuard().wrapPlayer(player);
		
			String areaName = player.getName() + "-" + name;
		
			BlockVector b1 = new BlockVector(x + radius, 0, z + radius);
			BlockVector b2 = new BlockVector(x - radius, 255, z - radius);
			
			ProtectedCuboidRegion pr = new ProtectedCuboidRegion(areaName, b1, b2);
			DefaultDomain dd = new DefaultDomain();
	   	    
			if(rm.overlapsUnownedRegion(pr, LPlayer))
			{
				return 1;
			}
			else
			{
			
				dd.addPlayer(player.getName());
				pr.setOwners(dd);
		   	    
				HashMap flags = null;
				// set the flags
				if (flags == null)
				{
					flags = new HashMap();
					if(FlagGreeting)
					{
						flags.put(DefaultFlag.GREET_MESSAGE, "Welcome to " + name);
					}
					
					if(FlagFarewell)
					{
						flags.put(DefaultFlag.FAREWELL_MESSAGE, "Bye");
					}
					
					if(FlagPvP)
					{
						flags.put(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "deny"));
					}
					
					if(FlagChest_Access)
					{
						flags.put(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "deny"));
					}
					
					if(FlagEntry)
					{
						flags.put(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "deny"));
					}
				}
		   	    
				pr.setFlags(flags);
		   	    
				try
				{
					rm.addRegion(pr);
					rm.save();
					return 0;
		   	   	}
				catch (Exception exp)
				{
					return 2;
				}
			}
	   	    
		} catch(Exception e)
		{
			return 3;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int createRegionIncludingHeight(Player player, String r, int x, int y, int z, String name, boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry)
	{
		try
		{
			int radius = Integer.parseInt(r);
			
			RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
			
			LocalPlayer LPlayer = getWorldGuard().wrapPlayer(player);
		
			String areaName = player.getName() + "-" + name;
		
			BlockVector b1 = new BlockVector(x + radius, y + radius, z + radius);
			BlockVector b2 = new BlockVector(x - radius, y - radius, z - radius);
			
			ProtectedCuboidRegion pr = new ProtectedCuboidRegion(areaName, b1, b2);
			DefaultDomain dd = new DefaultDomain();
	   	    
			if(rm.overlapsUnownedRegion(pr, LPlayer))
			{
				return 1;
			}
			else
			{
			
				dd.addPlayer(player.getName());
				pr.setOwners(dd);
		   	    
				HashMap flags = null;
				// set the flags
				if (flags == null)
				{
					flags = new HashMap();
					if(FlagGreeting)
					{
						flags.put(DefaultFlag.GREET_MESSAGE, "Welcome to " + name);
					}
					
					if(FlagFarewell)
					{
						flags.put(DefaultFlag.FAREWELL_MESSAGE, "Bye");
					}
					
					if(FlagPvP)
					{
						flags.put(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "deny"));
					}
					
					if(FlagChest_Access)
					{
						flags.put(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "deny"));
					}
					
					if(FlagEntry)
					{
						flags.put(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "deny"));
					}
				}
		   	    
				pr.setFlags(flags);
		   	    
				try
				{
					rm.addRegion(pr);
					rm.save();
					return 0;
		   	   	}
				catch (Exception exp)
				{
					return 2;
				}
			}
	   	    
		} catch(Exception e)
		{
			return 3;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int createRegionFixed(Player player, int x, int y, int z, String name, int h, int l, int w, boolean FlagGreeting, boolean FlagFarewell, boolean FlagPvP, boolean FlagChest_Access, boolean FlagEntry)
	{
		try
		{
			int height = h;
			int length = l;
			int width = w;
			
			RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
			
			LocalPlayer LPlayer = getWorldGuard().wrapPlayer(player);
		
			String areaName = player.getName() + "-" + name;
		
			BlockVector b1 = new BlockVector(x + length, y + height, z + width);
			BlockVector b2 = new BlockVector(x - length, y - height, z - width);
			
			ProtectedCuboidRegion pr = new ProtectedCuboidRegion(areaName, b1, b2);
			DefaultDomain dd = new DefaultDomain();
	   	    
			if(rm.overlapsUnownedRegion(pr, LPlayer))
			{
				return 1;
			}
			else
			{
			
				dd.addPlayer(player.getName());
				pr.setOwners(dd);
		   	    
				HashMap flags = null;
				// set the flags
				if (flags == null)
				{
					flags = new HashMap();
					if(FlagGreeting)
					{
						flags.put(DefaultFlag.GREET_MESSAGE, "Welcome to " + name);
					}
					
					if(FlagFarewell)
					{
						flags.put(DefaultFlag.FAREWELL_MESSAGE, "Bye");
					}
					
					if(FlagPvP)
					{
						flags.put(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "deny"));
					}
					
					if(FlagChest_Access)
					{
						flags.put(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "deny"));
					}
					
					if(FlagEntry)
					{
						flags.put(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "allow"));
					}
					else
					{
						flags.put(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "deny"));
					}
				}
		   	    
				pr.setFlags(flags);
		   	    
				try
				{
					rm.addRegion(pr);
					rm.save();
					return 0;
		   	   	}
				catch (Exception exp)
				{
					return 2;
				}
			}
	   	    
		} catch(Exception e)
		{
			return 3;
		}
	}
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
	
	public int getRegionCount(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		LocalPlayer LPlayer = getWorldGuard().wrapPlayer(player);
		
		return rm.getRegionCountOfPlayer(LPlayer);
	}
	
	public int playerIsInsideRegionAndOwns(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		set = rm.getApplicableRegions(player.getLocation());
		LocalPlayer LPlayer = getWorldGuard().wrapPlayer(player);
		
		if(set != null)
		{
			for ( ProtectedRegion region : set )
			{
				if(region.isOwner(LPlayer))
				{
					return 0;
				}
				else
				{
			   		return 2;
				}
			}
			return 1; //Not inside an area
		}
		else
		{
			return 3; //Error
		}
	}
	
	public boolean destroyRegion(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				rm.removeRegion(region.getId());
				rm.save();
				
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Regions Destroyed
	}
	
	public int destroyRegionByName(Player player, String id)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		ProtectedRegion region = rm.getRegion(id);
		
		LocalPlayer LPlayer = getWorldGuard().wrapPlayer(player);
		
		if(region == null)
		{
			player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " Could not find region by that name.");
			return 0;
		}
		
		if(!region.isOwner(LPlayer))
		{
			if(!player.hasPermission("areaprotect.ap.destroy.other"))
			{
				player.sendMessage(Config.AliasColour + Config.Alias + Config.ErrorColour + " You are not the owner of this region.");
				return 1;
			}
		}
		
		try
		{
			rm.removeRegion(region.getId());
			rm.save();
			
		} catch(Exception e)
		{
			return 2; //Error
		}
		
		return 3; //Regions Destroyed
	}
	
	public boolean editFlagGreeting(Player player, String message)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		if(message.equalsIgnoreCase("off "))
		{
			for ( ProtectedRegion region : set )
			{
				try
				{
					region.setFlag(DefaultFlag.GREET_MESSAGE, null);
					rm.save();
				} catch(Exception e)
				{
					return false; //Error
				}
			}
			return true; //Flags set
		}
		else
		{
		
			for ( ProtectedRegion region : set )
			{
				try
				{
					region.setFlag(DefaultFlag.GREET_MESSAGE, message);
					rm.save();
				} catch(Exception e)
				{
					return false; //Error
				}
				return true; //Flags set
			}
		}
		return false; //Error
	}
	
	public boolean editFlagFarewell(Player player, String message)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		if(message.equalsIgnoreCase("off "))
		{
			for ( ProtectedRegion region : set )
			{
				try
				{
					region.setFlag(DefaultFlag.FAREWELL_MESSAGE, null);
					rm.save();
				} catch(Exception e)
				{
					return false; //Error
				}
			}
			return true; //Flags set
		}
		else
		{
		
			for ( ProtectedRegion region : set )
			{
				try
				{
					region.setFlag(DefaultFlag.FAREWELL_MESSAGE, message);
					rm.save();
				} catch(Exception e)
				{
					return false; //Error
				}
			}
			return true; //Flags set
		}
	}
	
	public boolean addMember(Player player, String newPlayer)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			DefaultDomain dd = region.getMembers();
			dd.addPlayer(newPlayer);
			
			try
			{
				region.setMembers(dd);
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean removeMember(Player player, String newPlayer)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			DefaultDomain dd = region.getMembers();
			dd.removePlayer(newPlayer);
			
			try
			{
				region.setMembers(dd);
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean enablePVP(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "allow"));
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean disablePVP(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				region.setFlag(DefaultFlag.PVP, DefaultFlag.PVP.parseInput(getWorldGuard(), player, "deny"));
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean enableChest_Access(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				region.setFlag(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "allow"));
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean disableChest_Access(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				region.setFlag(DefaultFlag.CHEST_ACCESS, DefaultFlag.CHEST_ACCESS.parseInput(getWorldGuard(), player, "deny"));
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean enableEntry(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				region.setFlag(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "allow"));
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public boolean disableEntry(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		for ( ProtectedRegion region : set )
		{
			try
			{
				region.setFlag(DefaultFlag.ENTRY, DefaultFlag.ENTRY.parseInput(getWorldGuard(), player, "deny"));
				rm.save();
			} catch(Exception e)
			{
				return false; //Error
			}
		}
		return true; //Flags set
	}
	
	public Map<String, ProtectedRegion> getAreas(Player player)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		return rm.getRegions(); //Flags set
	}
	
	public String getFlags(Player player)
	{
		String flags = "";
		
		for ( ProtectedRegion region : set )
		{
			String[] nameArray = region.getId().split("-");
			String name = nameArray[1];
			
			DefaultDomain Downers = new DefaultDomain();
			Downers = region.getOwners();
			
			DefaultDomain Dmembers = new DefaultDomain();
			Dmembers = region.getMembers();
			
			String owners = Downers.toPlayersString();
			String members = Dmembers.toPlayersString();
			
				flags =
						"\n" + Config.AliasColour + Config.Alias + Config.SuccessColour + " ----- " + name + " -----" + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " Greeting: " + Config.VariableColour + region.getFlag(DefaultFlag.GREET_MESSAGE) + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " Farewell: " + Config.VariableColour + region.getFlag(DefaultFlag.FAREWELL_MESSAGE) + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " PvP: " + Config.VariableColour + region.getFlag(DefaultFlag.PVP) + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " Chest-Access: "  + Config.VariableColour + region.getFlag(DefaultFlag.CHEST_ACCESS) + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " Entry: " + Config.VariableColour + region.getFlag(DefaultFlag.ENTRY) + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " Member(s): " + Config.VariableColour + members + "\n" +
						Config.AliasColour + Config.Alias + Config.SuccessColour + " Owner(s): " + Config.VariableColour + owners;
		}
		return flags; //Flags
	}
}
