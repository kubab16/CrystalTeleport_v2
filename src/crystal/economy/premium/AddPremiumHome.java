package crystal.economy.premium;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import server.configuration.LocalConfiguration;
import server.configuration.PremiumConfig;
import server.database.Database;
import server.player.PlayerHome;
import server.throwExemption.ExceptionHandler;

public class AddPremiumHome {
public Boolean exist = true;
	
	public AddPremiumHome(Player player, PlayerHome home, String type)
	{		
		if(player.hasPermission("svip"))
		{
			try {
				if (Database.isClosed())
				{
					Database.reconnect();
				}
				ResultSet result = Database.CreateStatement().executeQuery("SELECT count(*) AS size FROM " + LocalConfiguration.DBPrefix.toString() + "premium WHERE UUID=\""+player.getUniqueId().toString()+"\"");
				if (result.next())
				{
					if(result.getInt("size") < PremiumConfig.vip.toInteger())
					{
						PreparedStatement statement = Database.getConnection()
								.prepareStatement("INSERT INTO "+LocalConfiguration.DBPrefix.toString() + "premium (id_home,uuid,name,x,y,z,world,type) VALUES (NULL,?,?,?,?,?,?,?)");
						statement.setString(1, player.getUniqueId().toString());
						statement.setString(2, home.getName());
						statement.setDouble(3, home.getX());
						statement.setDouble(4, home.getY());
						statement.setDouble(5, home.getZ());
						statement.setString(6, home.getWorld());
						statement.setString(7, type);
						
						if (statement.execute())
						{
							statement.close();
							player.sendMessage("SetHome " + home.getName() );
							exist = true;
						}
						else
						{
							player.sendMessage("Not SetHome " + home.getName() );
							statement.close();
							exist = false;
						}
					}
				}
				result.close();
			} catch (SQLException e) {
				ExceptionHandler.handle(e);
				exist = false;
			}
		}
		else			
		if(player.hasPermission("vip"))
		{
			try {
				if (Database.isClosed())
				{
					Database.reconnect();
				}
				ResultSet result = Database.CreateStatement().executeQuery("SELECT count(*) AS size FROM " + LocalConfiguration.DBPrefix.toString() + "premium WHERE UUID=\""+player.getUniqueId().toString()+"\"");
				if (result.next())
				{
					if(result.getInt("size") < PremiumConfig.vip.toInteger())
					{
						PreparedStatement statement = Database.getConnection()
								.prepareStatement("INSERT INTO "+LocalConfiguration.DBPrefix.toString() + "premium (id_home,uuid,name,x,y,z,world, type) VALUES (NULL,?,?,?,?,?,?,?)");
						statement.setString(1, player.getUniqueId().toString());
						statement.setString(2, home.getName());
						statement.setDouble(3, home.getX());
						statement.setDouble(4, home.getY());
						statement.setDouble(5, home.getZ());
						statement.setString(6, home.getWorld());
						statement.setString(7, "vip");
						
						if (!statement.execute())
						{
							statement.close();
							player.sendMessage("SetHome " + home.getName() );
							Database.CreateStatement().executeUpdate("Update " + LocalConfiguration.DBPrefix.toString() + "premium set home = home + 1 WHERE UUID = '"+player.getUniqueId().toString()+"'");
							exist = true;
						}
						else
						{
							player.sendMessage("Not SetHome " + home.getName() );
							statement.close();
							exist = false;
						}
					}
				}
				result.close();
				
			} catch (SQLException e) {
				ExceptionHandler.handle(e);
				exist = false;
			}
		}
		else
		{
			exist = false;
			return;
		}
	}
		
}