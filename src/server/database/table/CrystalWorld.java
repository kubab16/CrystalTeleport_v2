package server.database.table;

import server.configuration.LocalConfiguration;

public class CrystalWorld {
	private static String table = "CREATE TABLE IF NOT EXISTS " + LocalConfiguration.DBPrefix.toString() + "world(" + 
			"world varchar(50) primary key," + 
			"newhome BOOLEAN)";
	public static String getTable()
	{
		return table;
	}
}
