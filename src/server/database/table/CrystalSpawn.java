package server.database.table;

import server.configuration.LocalConfiguration;

public class CrystalSpawn {
	private static String table = "CREATE TABLE IF NOT EXISTS " + LocalConfiguration.DBPrefix.toString() + "spawn(" + 
			"world varchar(50) primary key," + 
			"x double not null," + 
			"y double not null," + 
			"z double not null)";
	public static String getTable()
	{
		return table;
	}
}
