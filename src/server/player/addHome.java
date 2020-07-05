package server.player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import server.configuration.LocalConfiguration;
import server.database.Database;
import server.throwExemption.ExceptionHandler;

public class addHome {
	public Boolean exist;
	
	public addHome(Player player, PlayerHome home)
	{
		try {
			if (Database.isClosed())
			{
				Database.reconnect();
			}
			PreparedStatement statement = Database.getConnection()
					.prepareStatement("INSERT INTO "+LocalConfiguration.DBPrefix.toString() + "home (id_home,uuid,name,x,y,z,world) VALUES (NULL,?,?,?,?,?,?)");
			statement.setString(1, player.getUniqueId().toString());
			statement.setString(2, home.getName());
			statement.setDouble(3, home.getX());
			statement.setDouble(4, home.getY());
			statement.setDouble(5, home.getZ());
			statement.setString(6, home.getWorld());
			
			if (!statement.execute())
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
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
			exist = false;
		}
	}
	
}
