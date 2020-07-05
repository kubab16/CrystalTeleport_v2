package server.player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import server.configuration.LocalConfiguration;
import server.database.Database;
import server.throwExemption.ExceptionHandler;

public class DeleteHome {
	boolean exist;
	public static boolean Delete(Player player, int id)
	{
		
		try {
			if (Database.isClosed())
			{
				Database.reconnect();
			}
			PreparedStatement statement = Database.getConnection()
					.prepareStatement("DELETE FROM  "+LocalConfiguration.DBPrefix.toString() + "home WHERE UUID=? AND id_home = ?");
			statement.setString(1, player.getUniqueId().toString());
			statement.setInt(2, id);
			
			if (!statement.execute())
			{
				statement.close();
				return true;
			}
			else
			{;
				statement.close();
				return false;
			}
		} catch (SQLException e) {
			ExceptionHandler.handle(e);
			return false;
		}
	}
}
