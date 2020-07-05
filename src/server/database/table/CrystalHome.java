package server.database.table;

import server.configuration.LocalConfiguration;

public class CrystalHome {
	private static String tableHome = "CREATE TABLE IF NOT EXISTS " + LocalConfiguration.DBPrefix.toString() + "home (" + 
			"id_home int auto_increment primary key," + 
			"name varchar(20)," + 
			"uuid varchar(50) not null," + 
			"x double not null," + 
			"y double not null," + 
			"z double not null," + 
			"world varchar(50) )";
	private static String TablePremium = "CREATE TABLE IF NOT EXISTS " + LocalConfiguration.DBPrefix.toString() + "premium (" + 
			"id_home int auto_increment primary key," + 
			"name varchar(20)," + 
			"uuid varchar(50) not null," + 
			"x double not null," + 
			"y double not null," + 
			"z double not null," + 
			"world varchar(50)," +
			"type varchar(10) )";
	
	public static String getTable()
	{
		return tableHome;
	}
	public static String GetTableUser()
	{
		return TablePremium;
	}
}
