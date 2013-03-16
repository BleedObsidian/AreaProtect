package com.gmail.bleedobsidian;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import cosine.boseconomy.BOSEconomy;

public class Economy
{
	public static BOSEconomy economy = null;
	public static boolean BOSEloaded = false;
	
	public static void loadBOSE()
	{
		// Attempt to get the plugin instance for BOSEconomy.
	    Plugin temp = Bukkit.getServer().getPluginManager().getPlugin("BOSEconomy");
	    
	    // Check whether BOSEconomy is loaded.
	    if(temp == null)
	        // BOSEconomy is not loaded on the server.
	        economy = null;
	    else
	        // BOSEconomy is now stored in the "economy" variable.
	        economy = (BOSEconomy)temp;
	    	BOSEloaded = true;
	    	
	    if(economy == null)
	    {
	    	Bukkit.getLogger().warning(Config.Alias + " Could not load BOSEconomy, if you don't want to use BOSEconomy change the config.");
	    }
	}
}
