package crystal.economy;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import server.configuration.EconomyConfig;
import server.configuration.LocalConfiguration;
import server.database.Database;

public class PriseHome {
	static double percentage = EconomyConfig.NEXTPRISE.toInteger()/100.0;
	static double FirstPrise = EconomyConfig.FIRSTPRISE.toInteger();
	/**
	 * @author kubab16
	 * @param count
	 * @return double price next set home
	 */
	public static double getPrise(int count) {
		if(EconomyConfig.TYPE.toBoolean())
		{
			return (FirstPrise * Math.pow((percentage+1), count-1));
		}	
		else
		{
			return round((FirstPrise * Math.pow((percentage+1), count-1)),2);
		}
	}
	public static double getPrise(Player player) {
		ResultSet result;
		try {
			result = Database.CreateStatement().executeQuery("SELECT count(*) AS size FROM " + LocalConfiguration.DBPrefix.toString() + "home WHERE UUID=\""+player.getUniqueId().toString()+"\"");
			if (result.next() && result.getRow() > 0)
			{
				int count = result.getInt("size");
				if(percentage < 2)percentage ++;
				if(count == 0)
				{
					return FirstPrise;
				}
				if(EconomyConfig.TYPE.toBoolean())
				{
					return round((FirstPrise * Math.pow((percentage), count)),2);
				}	
				else
				{
					return round((FirstPrise * Math.pow((percentage), count)), 0);
				}
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
		
	}
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
