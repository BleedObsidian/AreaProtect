package me.bleedobsidian.areaprotect;

import java.util.HashMap;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public int createRegion(Player player, String r, int x, int z, String name)
	{
		try
		{
			int radius = Integer.parseInt(r);
			
			Plugin plugin = player.getServer().getPluginManager().getPlugin("WorldGuard");
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
				flags.put(DefaultFlag.GREET_MESSAGE, "Welcome to " + name);
				flags.put(DefaultFlag.FAREWELL_MESSAGE, "Bye");
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
	
	public boolean editFlagGreeting(Player player, String message)
	{
		RegionManager rm = getWorldGuard().getRegionManager(player.getWorld());
		
		if(message.equalsIgnoreCase("off"))
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
		
		if(message.equalsIgnoreCase("off"))
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
}
