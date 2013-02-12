package com.gmail.bleedobsidian;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public static String Type = "Beta";
	public static String Version = "3.0.2";
	
	Config CConfig = new Config();
	
	@Override
	public void onEnable()
	{
		getLogger().info("AreaProtect v" + Version + " Enabled.");
		
		//----------Config----------//
			getLogger().info("Loading config file...");
				CConfig.initConfig(this.getConfig());
			getLogger().info("Config loaded, Groups created.");
			
			getLogger().info("Groups: " + Groups.Groups.size());
			
			saveConfig();
		//--------------------------//
			
		//----- MySQL -----//
			if(Config.CheckForUpdate)
			{
				Auto.CheckForUpdate();
			}
		//-----------------//
			
		//----- BOSEconomy -----//
			if(Config.UseBOSEconomy)
			{
				Economy.loadBOSE();
			}
		//----------------------//
			
		//----- set Commands -----//
			getCommand("ap").setExecutor(new CommandAP());
		//------------------------//
	}
	
	@Override
	public void onDisable()
	{
		getLogger().info("AreaProtect " + Version + " Disabled.");
		
		//----- MySQL -----//
			Auto.closeConnection();
		//-----------------//
	}
}
