package server.world;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.World;
import org.bukkit.entity.Player;

import server.configuration.LocalConfiguration;
import server.configuration.WorldConfig;
import server.database.Database;

public class Permision {
	
	public static boolean newHome(World world)
	{
		try {
			PreparedStatement statment = Database.getConnection().prepareStatement("SELECT newhome FROM "+ LocalConfiguration.DBPrefix.toString() + "world WHERE world = ?");
			String uuid = world.getUID().toString();
			statment.setString(1, uuid);
			ResultSet result = statment.executeQuery();
			if (!result.next())
			{
				statment.close();
				result.close();
				return WorldConfig.WorldSetHome.toBoolean();
			}
			else
			{
				if(result.getBoolean("newhome") == true)
				{
					statment.close();
					result.close();
					return true;
				}
				else
				{
					statment.close();
					result.close();
					return false;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean SetPermision(World world,boolean onOf)
	{
		try {
			PreparedStatement statment = Database.getConnection().prepareStatement("REPLACE  INTO "+ LocalConfiguration.DBPrefix.toString() + "world values (?,?)");
			String uuid = world.getUID().toString();
			statment.setString(1, uuid);
			statment.setBoolean(2, onOf);
			int result = statment.executeUpdate();
			statment.close();
			if (result > 0)
			{
				return true;
			}
			else
			{
				return false;
			}		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static boolean SetPermision(Player player,boolean onOf)
	{
		World world = player.getWorld();
		try {
			PreparedStatement statment = Database.getConnection().prepareStatement("REPLACE INTO "+ LocalConfiguration.DBPrefix.toString() + "world values (?,?)");
			String uuid = world.getUID().toString();
			statment.setString(1, uuid);
			statment.setBoolean(2, onOf);
			int result = statment.executeUpdate();
			statment.close();
			if (result > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
