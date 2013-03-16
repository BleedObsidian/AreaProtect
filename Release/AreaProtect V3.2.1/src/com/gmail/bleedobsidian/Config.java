package com.gmail.bleedobsidian;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
	public static FileConfiguration config;
	
	public static String Alias = "[AreaProtect]"; //Player message Alias
	
	public static ChatColor AliasColour = ChatColor.BLUE; //Colour of player message alias
	public static ChatColor ErrorColour = ChatColor.RED; //Colour of error text
	public static ChatColor SuccessColour = ChatColor.GREEN; //Colour of success text
	public static ChatColor VariableColour = ChatColor.AQUA; //Colour of variable text
	
	private List<?> GroupList;
	
	public static boolean CheckForUpdate;
	public static boolean UseBOSEconomy;
	
	public static int DefaultMaximumAreaRadius;
	public static int DefaultMaximumAmountOfAreas;
	public static boolean DefaultRadiusShouldIncludeHeight;
	public static boolean DefaultFlagGreeting;
	public static boolean DefaultFlagFarewell;
	public static boolean DefaultFlagPvP;
	public static boolean DefaultFlagChest_Access;
	public static boolean DefaultFlagEntry;
	public static boolean DefaultFixedSize;
	public static int DefaultHeight;
	public static int DefaultLength;
	public static int DefaultWidth;
	public static double DefaultPrice;
	
	public void initConfig(FileConfiguration config)
	{
		Config.config = config; //Load config into memory
		
		loadDefaults(); //Check default values
		loadProperties(); //Load the config properties into variables
		loadGroups(); //Load and create groups listed in config
	}
	
	private void loadDefaults()
	{
		//----- Config Header -----//
		config.options().header(
		"----- AreaProtect V" + Main.Version + " Config file -----\n" +
		"Here you can change all AreaProtect's properties, groups and limits.\n" + 
		"	\n" + 
		"CheckForUpdate: true | Notify if there is a new version of AreProtect. Strongly recommended \n" + 
		"UseBOSEconomy: true | Use BOSEconomy. If true people need to pay for areas. \n" + 
		"	\n" +
		"Groups: -Default | The list of groups, List all your groups here. Must have a default group called Default, so if players are not in a group it uses this one.\n" +
		"	\n" +
		"MaximumArearadius: 32 | The maximum area radius. \n" +
		"MaximumAmountOfAreas: 1 | The maximum amount of areas. \n" +
		"RadiusShouldIncludeHeight: false | If true the height of the area will be radius x 2 instead of bedrock to sky.  \n" +
		"FixedSize: false | If the user should not be able to choose the radius of their area.  \n" +
		"Flags: \n" +
		"  Greeting: true | If the area should by default have a greeting message.  \n" +
		"  Farewell: true | If the area should by default have a farewell message.  \n" +
		"  PvP: true | If the area should by default have pvp on/off.  \n" +
		"  Chest-Access: true | If the area should by default allow/deny chest-access.  \n" +
		"  Entry: true | If the area should by default allow/deny entry.  \n" +
		"Height: 0 | If FixedSize: true, The height of the area. \n" +
		"Length: 0 | If FixedSize: true, The Length of the area. \n" + 
		"Width: 0 | If FixedSize: true, The width of the area. \n" +
		"Price: 0 | If BOSEconomy: true, The price to create an area. \n");
		//-------------------------//
		
		//-----Default Properties -----//
		config.addDefault("Properties.CheckForUpdate", true);
		config.addDefault("Properties.UseBOSEconomy", true);
		
		config.addDefault("Groups.Groups", Arrays.asList("Default"));
		
		config.addDefault("Groups.Default.MaximumAreaRadius", 32);
		config.addDefault("Groups.Default.MaximumAmountOfAreas", 1);
		config.addDefault("Groups.Default.RadiusShouldIncludeHeight", false);
		config.addDefault("Groups.Default.Flag.Greeting", true);
		config.addDefault("Groups.Default.Flag.Farewell", true);
		config.addDefault("Groups.Default.Flag.PvP", true);
		config.addDefault("Groups.Default.Flag.Chest-Access", true);
		config.addDefault("Groups.Default.Flag.Entry", true);
		config.addDefault("Groups.Default.FixedSize", false);
		config.addDefault("Groups.Default.Height", 0);
		config.addDefault("Groups.Default.Length", 0);
		config.addDefault("Groups.Default.Width", 0);
		config.addDefault("Groups.Default.Price", 100);
		//----------------------------//
		
		config.options().copyDefaults(true); //Save defaults
	}
	
	private void loadProperties()
	{
		//----- Load Properties -----//
		CheckForUpdate = config.getBoolean("Properties.CheckForUpdate");
		UseBOSEconomy = config.getBoolean("Properties.UseBOSEconomy");
		
		GroupList = config.getList("Groups.Groups");
		//---------------------------//
		
		//----- Load Default Properties -----//
		DefaultMaximumAreaRadius = config.getInt("Groups.Default.MaximumAreaRadius");
		DefaultMaximumAmountOfAreas = config.getInt("Groups.Default.MaximumAmountOfAreas");
		DefaultRadiusShouldIncludeHeight = config.getBoolean("Groups.Default.RadiusShouldIncludeHeight");
		DefaultFlagGreeting = config.getBoolean("Groups.Default.Flag.Greeting");
		DefaultFlagFarewell = config.getBoolean("Groups.Default.Flag.Farewell");
		DefaultFlagPvP = config.getBoolean("Groups.Default.Flag.PvP");
		DefaultFlagChest_Access = config.getBoolean("Groups.Default.Flag.Chest-Access");
		DefaultFlagEntry = config.getBoolean("Groups.Default.Flag.Entry");
		DefaultFixedSize = config.getBoolean("Groups.Default.FixedSize");
		DefaultHeight = config.getInt("Groups.Default.Height");
		DefaultLength = config.getInt("Groups.Default.Length");
		DefaultWidth = config.getInt("Groups.Default.Width");
		DefaultPrice = config.getDouble("Groups.Default.Price");
		//-----------------------------------//
	}
	
	private void loadGroups() //Load groups, group properties and create group
	{
		for(int g = 0; g < GroupList.size(); g++)
		{
			if(!GroupList.get(g).equals("Default")) //Ignore default group, already created
			{
				//----- Load Group Properties -----//
				String GroupName = (String) GroupList.get(g);
				
				int GroupMaximumAreaRadius = config.getInt("Groups." + GroupName + ".MaximumAreaRadius");
				int GroupMamimumAmountOfAreas = config.getInt("Groups." + GroupName + ".MaximumAmountOfAreas");
				boolean GroupRadiusShouldIncludeHeight = config.getBoolean("Groups." + GroupName + ".RadiusShouldIncludeHeight");
				boolean GroupFlagGreeting = config.getBoolean("Groups." + GroupName + ".Flag.Greeting");
				boolean GroupFlagFarewell = config.getBoolean("Groups." + GroupName + ".Flag.Farewell");
				boolean GroupFlagPvP = config.getBoolean("Groups." + GroupName + ".Flag.PvP");
				boolean GroupFlagChest_Access = config.getBoolean("Groups." + GroupName + ".Flag.Chest-Access");
				boolean GroupFlagEntry = config.getBoolean("Groups." + GroupName + ".Flag.Entry");
				boolean GroupFixedSize = config.getBoolean("Groups." + GroupName + ".FixedSize");
				int GroupHeight = config.getInt("Groups." + GroupName + ".Height");
				int GroupLength = config.getInt("Groups." + GroupName + ".Length");
				int GroupWidth = config.getInt("Groups." + GroupName + ".Width");
				double GroupPrice = config.getDouble("Groups." + GroupName + ".Price");
				//---------------------------------//
				
				//Add group
				Groups.addGroup(GroupName, GroupMaximumAreaRadius, GroupMamimumAmountOfAreas, GroupRadiusShouldIncludeHeight, GroupFlagGreeting, GroupFlagFarewell, GroupFlagPvP, GroupFlagChest_Access, GroupFlagEntry, GroupFixedSize, GroupHeight, GroupLength, GroupWidth, GroupPrice);
			}
		}
	}
}
