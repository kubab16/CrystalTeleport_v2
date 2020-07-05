package server.world;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import server.configuration.LocalConfiguration;
import server.database.Database;
import server.throwExemption.ExceptionHandler;

public class Spawn{
	public static boolean NewSpawn(Player player)
	{
		
		try {
			if (Database.isClosed())
			{
				Database.reconnect();
			}
			Location location = player.getLocation();
			Double x = location.getBlockX() + 0.5;
			Double y = location.getY();
			Double z = location.getBlockZ() + 0.5;
			String world = location.getWorld().getUID().toString();
			PreparedStatement statement = Database.getConnection()
					.prepareStatement("REPLACE INTO "+LocalConfiguration.DBPrefix.toString() + "spawn (world,x,y,z) VALUES (?,?,?,?)");
			statement.setString(1, world);
			statement.setDouble(2, x);
			statement.setDouble(3, y);
			statement.setDouble(4, z);
			if (statement.executeUpdate() > 0)
			{
				statement.close();
				return true;
			}
			else
			{
				statement.close();
				return false;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ExceptionHandler.handle(e);
			return false;
		}
	}
	public static Location getSpawn(Player player)
	{
		try {
			if (Database.isClosed())
			{
				Database.reconnect();
			}
			Location location = player.getLocation();
			String worlduuid = location.getWorld().getUID().toString();
			PreparedStatement statement = Database.getConnection()
					.prepareStatement("SELECT * from "+LocalConfiguration.DBPrefix.toString() + "spawn WHERE world = ?");
			statement.setString(1, worlduuid);
			ResultSet result =  statement.executeQuery();
			if (result.next())
			{
				double x = result.getDouble("x");
				double y = result.getDouble("y");
				double z = result.getDouble("z");
				World world = Bukkit.getServer().getWorld(UUID.fromString(result.getString("world")));
				statement.close();
				Location spawn = new Location(world,x,y,z);
				return spawn;				
			}
			else
			{
				statement.close();
				return null;	
			}
			
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
			return null;
		}	
	}
}
