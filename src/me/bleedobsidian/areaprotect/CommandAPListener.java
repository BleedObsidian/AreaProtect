package me.bleedobsidian.areaprotect;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAPListener implements CommandExecutor {

	Regions regions = new Regions();
	Main main = new Main();
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command can only be run by a player.");
			return true;
		} else
		{
			Player player = (Player) sender;
			World world = player.getWorld();
			
			int x = player.getLocation().getBlockX();
			int z = player.getLocation().getBlockZ();
			
			if(args.length > 0)
			{
				//Commands
				if(args[0].equalsIgnoreCase("create"))
				{
					if(args.length == 3) //Check if the is the right amount of args for this command
					{
						if(player.hasPermission("areaprotect.ap.create"))//Continue if the player has permission
						{
							if(isNumeric(args[1])) //Make sure name is not numeric
							{
								player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[1] + ChatColor.RED + " is not a name.");
								return true;
							}
							else if (!isNumeric(args[2])) //Make sure radius is a number
							{
								player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[2] + ChatColor.RED + " is not a number.");
								return true;
							}
							else
							{
								if(regions.getRegionCount(player) < Main.maxAreas || player.hasPermission("areaprotect.ap.create.bypass.maxareas")) //Continue if the player has not reached his area limit
								{
									if(Integer.parseInt(args[2]) <= Main.maxRadius || player.hasPermission("areaprotect.ap.create.bypass.maxradius"))
									{
										int regionAnswer = regions.createRegion(player, args[2], x, z, args[1]); //Create region and get answer
									
										if(regionAnswer == 0)//If the region is created successfully continue
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[1] + ChatColor.GREEN + " Has been created.");
											return true;
										}
										else if(regionAnswer == 1) //If region overlaps
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Area overlaps with another region you don't own.");
											return true;
										}
										else if(regionAnswer == 2) //If region couldn't save
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "The creation of " + ChatColor.DARK_BLUE + args[1] + ChatColor.RED + " failed.");
											return true;
										}
										else
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "The creation of " + ChatColor.DARK_BLUE + args[1] + ChatColor.RED + " failed.");
											return true;
										}
									}
									else //If radius is bigger than maxRadius and doesn't have permission to bypass
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Radius to big, Max: " + ChatColor.DARK_BLUE + Main.maxRadius);
										return true;
									}
								} //If the player has reached his limit continue
								else
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You have reached your limit: " + ChatColor.DARK_BLUE + regions.getRegionCount(player) + "/" + Main.maxAreas);
									return true;
								}
							
							}
					
						} //If the player does not have permission continue
						else
						{
							player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You are not allowed to use this command.");
							return true;
						}
					}//If there is not the right amount of args for command
					else
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Usage: " + ChatColor.DARK_BLUE + "/ap create [name] [radius]");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("destroy"))
				{
					if(args.length == 1) //Check if the is the right amount of args for this command
					{
						if(player.hasPermission("areaprotect.ap.destroy"))//Continue if the player has permission
						{
							int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
							
							if(playerRegionAnswer == 1) //If player is not inside a region
							{
								player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be inside an area.");
								return true;
							}
							else if(playerRegionAnswer == 2) //If player is not the owner
							{
								if(player.hasPermission("areaprotect.ap.destroy.other"))//If player is allowed to destroy areas they don't own
								{
									if(regions.destroyRegion(player)) //If region destroyed
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Region destroyed.");
										return true;
									}
									else
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Error destroying region.");
										return true;
									}
								}
								else
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be the owner of this region.");
									return true;
								}
							}
							else if(playerRegionAnswer == 3) //If error
							{
								player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "An error has occurred.");
								return true;
							}
							else //If player is inside a region and owns it
							{
								if(regions.destroyRegion(player)) //If region destroyed
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Region destroyed.");
									return true;
								}
								else
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Error destroying region.");
									return true;
								}
							}
						}
						else //If user doesn't have permission
						{
							player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You are not allowed to use this command.");
							return true;
						}
					}
					else //If not the right amount of args
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Usage: " + ChatColor.DARK_BLUE + "/ap destroy");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("flag"))
				{
					if(args.length >= 3) //Check if the is the right amount of args for this command
					{
						if(player.hasPermission("areaprotect.ap.flag"))//Continue if the player has permission
						{
							if(args[1].equalsIgnoreCase("greeting")) //If flag greeting
							{
								if(player.hasPermission("areaprotect.ap.flag.greeting"))
								{
									int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
								
									if(playerRegionAnswer == 1) //If player is not inside a region
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be inside an area.");
										return true;
									}
									else if(playerRegionAnswer == 2) //If player is not the owner
									{
										if(player.hasPermission("areaprotect.ap.flag.other")) //If the player has permission to edit flags of regions he doesn't own
										{
											if(args[2].equalsIgnoreCase("off")) //If player wants to disable greeting
											{
												if(regions.editFlagGreeting(player, "off")) //Turn off greeting
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Greeting disabled.");
													return true;
												}
												else
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to disable greeting.");
													return true;
												}
											}
											else //If not set it to a message
											{
												if(regions.editFlagGreeting(player, getMessage(args))) //Turn off greeting
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Greeting set.");
													return true;
												}
												else
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to set greeting.");
													return true;
												}
											}
										}
										else
										{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be the owner of this region.");
										return true;
										}
									}
									else if(playerRegionAnswer == 3) //If error
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "An error has occurred.");
										return true;
									}
									else //If player is inside a region and owns it
									{
										if(args[2].equalsIgnoreCase("off")) //If player wants to disable greeting
										{
											if(regions.editFlagGreeting(player, "off")) //Turn off greeting
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Greeting disabled.");
												return true;
											}
											else
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to disable greeting.");
												return true;
											}
										}
										else //If not set it to a message
										{
											if(regions.editFlagGreeting(player, getMessage(args))) //Turn off greeting
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Greeting set.");
												return true;
											}
											else
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to set greeting.");
												return true;
											}
										}
									}
								}
								else
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to edit flag " + ChatColor.DARK_BLUE + "greeting");
									return true;
								}
							}
							else if(args[1].equalsIgnoreCase("farewell")) //If flag greeting
							{
								if(player.hasPermission("areaprotect.ap.flag.farewell"))
								{
									int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
								
									if(playerRegionAnswer == 1) //If player is not inside a region
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be inside an area.");
										return true;
									}
									else if(playerRegionAnswer == 2) //If player is not the owner
									{
										if(player.hasPermission("areaprotect.ap.flag.other")) //If the player has permission to edit flags of regions he doesn't own
										{
											if(args[2].equalsIgnoreCase("off")) //If player wants to disable greeting
											{
												if(regions.editFlagFarewell(player, "off")) //Turn off greeting
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Farewell disabled.");
													return true;
												}
												else
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to disable farewell.");
													return true;
												}
											}
											else //If not set it to a message
											{
												if(regions.editFlagFarewell(player, getMessage(args))) //Turn off greeting
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Farewell set.");
													return true;
												}
												else
												{
													player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to set farewell.");
													return true;
												}
											}
										}
										else
										{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be the owner of this region.");
										return true;
										}
									}
									else if(playerRegionAnswer == 3) //If error
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "An error has occurred.");
										return true;
									}
									else //If player is inside a region and owns it
									{
										if(args[2].equalsIgnoreCase("off")) //If player wants to disable greeting
										{
											if(regions.editFlagFarewell(player, "off")) //Turn off greeting
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Farewell disabled.");
												return true;
											}
											else
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to disable farewell.");
												return true;
											}
										}
										else //If not set it to a message
										{
											if(regions.editFlagFarewell(player, getMessage(args))) //Turn off greeting
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Farewell set.");
												return true;
											}
											else
											{
												player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Failed to set farewell.");
												return true;
											}
										}
									}
								}
								else
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to edit flag " + ChatColor.DARK_BLUE + "farewell");
									return true;
								}
							}
							else
							{
								player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Unkown flag " + ChatColor.DARK_BLUE + args[1]);
								return true;
							}
						}
						else //If player doesn't have permission
						{
							player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to use this command.");
							return true;
						}
					}
					else //If not the right amount of args
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Usage: " + ChatColor.DARK_BLUE + "/ap flag [greeting/farewell] [value/off]");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("addmember"))
				{
					if(args.length == 2) //Check if the is the right amount of args for this command
					{
						if(player.hasPermission("areaprotect.ap.addmember"))//Continue if the player has permission
						{
								int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
								
								if(playerRegionAnswer == 1) //If player is not inside a region
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be inside an area.");
									return true;
								}
								else if(playerRegionAnswer == 2) //If player is not the owner
								{
									if(player.hasPermission("areaprotect.ap.addmember.other"))
									{
										if(regions.addMember(player, args[2]))
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[1] + ChatColor.GREEN + " added to members.");
											return true;
										}
										else
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Error adding player to members.");
											return true;
										}
									}
									else
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be the owner of this area.");
										return true;
									}
								}
								else if(playerRegionAnswer == 3) //If error
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "An error has occurred.");
									return true;
								}
								else //If player is inside a region and owns it
								{
									if(regions.addMember(player, args[1]))
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[1] + ChatColor.GREEN + " added to members.");
										return true;
									}
									else
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Error adding player to members.");
										return true;
									}
								}
						}
						else //If player doesn't have permission
						{
							player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to use this command.");
							return true;
						}
					}
					else //If not the right amount of args
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Usage: " + ChatColor.DARK_BLUE + "/ap addmember [name]");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("removemember"))
				{
					if(args.length == 2) //Check if the is the right amount of args for this command
					{
						if(player.hasPermission("areaprotect.ap.removemember"))//Continue if the player has permission
						{
								int playerRegionAnswer = regions.playerIsInsideRegionAndOwns(player); //Check if player is inside a region and owns it
								
								if(playerRegionAnswer == 1) //If player is not inside a region
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be inside an area.");
									return true;
								}
								else if(playerRegionAnswer == 2) //If player is not the owner
								{
									if(player.hasPermission("areaprotect.ap.removemember.other"))
									{
										if(regions.removeMember(player, args[2]))
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[1] + ChatColor.GREEN + " removed from members.");
											return true;
										}
										else
										{
											player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Error removing player from members.");
											return true;
										}
									}
									else
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You must be the owner of this area.");
										return true;
									}
								}
								else if(playerRegionAnswer == 3) //If error
								{
									player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "An error has occurred.");
									return true;
								}
								else //If player is inside a region and owns it
								{
									if(regions.removeMember(player, args[1]))
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.DARK_BLUE + args[1] + ChatColor.GREEN + " removed from members.");
										return true;
									}
									else
									{
										player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Error removing player from members.");
										return true;
									}
								}
						}
						else //If player doesn't have permission
						{
							player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to use this command.");
							return true;
						}
					}
					else //If not the right amount of args
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Usage: " + ChatColor.DARK_BLUE + "/ap removemember [name]");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("help"))
				{
					if(player.hasPermission("areaprotect.ap.help"))
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.YELLOW + "All commands of Area Protect.");
						player.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");
						player.sendMessage(ChatColor.AQUA + "/ap create [name] [radius] " + ChatColor.GREEN + "| Create a protected area.");
						player.sendMessage(ChatColor.AQUA + "/ap destroy " + ChatColor.GREEN + "| Destroy an area.");
						player.sendMessage(ChatColor.AQUA + "/ap addmember [name] " + ChatColor.GREEN + "| Add a member to the area.");
						player.sendMessage(ChatColor.AQUA + "/ap removemember [name] " + ChatColor.GREEN + "| Remove a member from the area.");
						player.sendMessage(ChatColor.AQUA + "/ap tutorial " + ChatColor.GREEN + "| Run a tutorial about how to use Area Protect.");
						player.sendMessage(ChatColor.AQUA + "/ap info " + ChatColor.GREEN + "| Info about your areas.");
						return true;
					}
					else
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to use this command");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("info"))
				{
					if(player.hasPermission("areaprotect.ap.info"))
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "You have " + ChatColor.DARK_BLUE + regions.getRegionCount(player) + "/" + Main.maxAreas + ChatColor.GREEN + " areas.");
						return true;
					}
					else
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to use this command");
						return true;
					}
				}
				else if(args[0].equalsIgnoreCase("tutorial"))
				{
					if(player.hasPermission("areaprotect.ap.tutorial"))
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.GREEN + "Tutorial on how to use AreaProtect:");
						player.sendMessage("");
						player.sendMessage(ChatColor.YELLOW + "1. " + ChatColor.GREEN + "Stand in the middle of the place you want to create an area in and figure out it's radius.");
						player.sendMessage(ChatColor.YELLOW + "2. " + ChatColor.GREEN + "Then use the command: " + ChatColor.DARK_BLUE + "/ap create [name] [radius]");
						player.sendMessage("");
						player.sendMessage(ChatColor.BLUE + "Changeing the Enter/Exit Message");
						player.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");
						player.sendMessage("");
						player.sendMessage(ChatColor.YELLOW + "Enter: " + ChatColor.DARK_BLUE + "/ap flag greeting [message/off]");
						player.sendMessage(ChatColor.YELLOW + "Exit: " + ChatColor.DARK_BLUE + "/ap flag farewell [message/off]");
						player.sendMessage("");
						player.sendMessage(ChatColor.BLUE + "Adding other users that can build");
						player.sendMessage(ChatColor.YELLOW + "-----------------------------------------------------");
						player.sendMessage("");
						player.sendMessage(ChatColor.YELLOW + "Add: " + ChatColor.DARK_BLUE + "/ap addmember [name]");
						player.sendMessage(ChatColor.YELLOW + "Remove: " + ChatColor.DARK_BLUE + "/ap removemember [name]");
						
						return true;
					}
					else
					{
						player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "You do not have permission to use this command");
						return true;
					}
				}
				else //unkown command
				{
					player.sendMessage(ChatColor.BLUE + "[AreaProtect] " + ChatColor.RED + "Unkown command: " + ChatColor.DARK_BLUE + args[0]);
					return true;
				}
			}
			else //If args are not more than 0
			{
				return false;
			}
		}
	}
	
	public boolean isNumeric(String i)
	{
		try
		{
			Integer.parseInt(i);
			return true;
		} catch(Exception E)
		{
			return false;
		}
	}
	
	public String getMessage(String[] args)
	{
		String newGreeting = "";
		for (int i = 2; i < args.length; i++)
		{
				newGreeting = (new StringBuilder(String.valueOf(newGreeting))).append(args[i]).append(i == args.length - 2 ? " " : " ").toString();
		}
		return newGreeting;
	}
}
