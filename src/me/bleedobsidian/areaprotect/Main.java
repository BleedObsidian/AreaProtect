package me.bleedobsidian.areaprotect;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	FileConfiguration config;
	
	public static int maxAreas;
	public static int maxRadius;
	
	@Override
	public void onEnable()
	{
		loadConfig(); //Load Config File
		getLogger().info("Config Loaded!");
		
		setCommands(); //Set commands
		
		getLogger().info("AreaProtect Enabled!");
	}
	
	@Override
	public void onDisable()
	{
		
	}
	
	public void setCommands()
	{
		getCommand("ap").setExecutor(new CommandAPListener());
	}
	
	public void loadConfig()
	{
		config = this.getConfig();
		
		config.options().header("AreaProtect Config file:");
		config.addDefault("MaximumAreaRadius", 32);
		config.addDefault("MaximumAmountOfAreas", 2);
		
		config.options().copyDefaults(true);
		
		saveConfig();
		
		maxAreas = config.getInt("MaximumAmountOfAreas");
		maxRadius = config.getInt("MaximumAreaRadius");
	}
}
