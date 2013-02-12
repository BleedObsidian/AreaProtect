package com.gmail.bleedobsidian;

public class Functions
{
	public static boolean isNumeric(String i)
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
}
