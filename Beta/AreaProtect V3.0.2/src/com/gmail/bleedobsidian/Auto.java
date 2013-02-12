package com.gmail.bleedobsidian;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.Connection;
import java.sql.Statement;

public class Auto
{
	static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    
    static Server server = Bukkit.getServer();
    static ConsoleCommandSender console = server.getConsoleSender();
    
    static Plugin plugin = Bukkit.getPluginManager().getPlugin("AreaProtect");
	
	public static void CheckForUpdate()
	{
		try {
			getConnection();
		} catch (SQLException e1) {
			plugin.getLogger().warning("Could not connect to MySQL Database");
		}
		
		try
		{
			Statement st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM `Update` WHERE `Type`='"+ Main.Type + "';");
			
			String latestversion = null;
			if (rs.next()) {
			    latestversion = rs.getString(2);
			}
			
			if(!(latestversion.equalsIgnoreCase(Main.Version)))
			{
				console.sendMessage(Config.Alias + ChatColor.GREEN + " There is a new " + Main.Type + " version of AreaProtect available: " + latestversion + " capable of " + rs.getString(3));
				console.sendMessage(Config.Alias + ChatColor.GREEN + " " + rs.getString(5));
			}
			else
			{
				plugin.getLogger().info("No update available.");
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void getConnection() throws SQLException
	{
		plugin.getLogger().info("Checking for update...");
        String url = "jdbc:mysql://www.wacky-games.nn.pe:3306/wackygam_areaprotect";
        String user = "wackygam_ap";
        String password = "areaprotect5?";

        con = (Connection) DriverManager.getConnection(url, user, password);
	}
	
	public static void closeConnection()
	{
		if(con != null)
		{
			try {
				con.close();
				plugin.getLogger().info("Connection closed.");
			} catch (SQLException e) {
				plugin.getLogger().warning("Error closing MySQL Connection.");
			}
		}
	}
}
