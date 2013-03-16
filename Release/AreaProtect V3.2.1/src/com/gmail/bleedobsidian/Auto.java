package com.gmail.bleedobsidian;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mysql.jdbc.Connection;
import java.sql.Statement;

public class Auto
{
	static Connection con = null;
    static Statement st = null;
    static ResultSet rs = null;
    
    static Plugin plugin = Bukkit.getPluginManager().getPlugin("AreaProtect");
	
	public static void CheckForUpdate()
	{
		try {
			getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			plugin.getLogger().warning("Could not connect to MySQL Database");
		}
		
		try
		{
			if(con != null)
			{
				Statement st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM `Update` WHERE `Type`='"+ Main.Type + "';");
				
				String latestversion = "";
				String capable = "";
				String comments = "";
				
				if(rs.next())
				{
				    latestversion = rs.getString(2);
					capable = rs.getString(3);
					comments = rs.getString(4);
				}
				
				if(!(latestversion.equalsIgnoreCase(Main.Version)))
				{
					Bukkit.getLogger().warning(Config.Alias + " There is a new " + Main.Type + " version of AreaProtect available: " + latestversion + " capable of " + capable);
					Bukkit.getLogger().warning(Config.Alias + " " + comments);
				}
				else
				{
					plugin.getLogger().info("No update available.");
				}
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void getConnection() throws SQLException
	{
		plugin.getLogger().info("Checking for update...");
        String url = "jdbc:mysql://mysql.5wire.co.uk:3306/wackygam_areaprotect";
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
