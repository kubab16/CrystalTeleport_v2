package crystal.comand;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import crystal.economy.PriseHome;
import crystal.menu.SubmitSetHome;
import server.configuration.EconomyConfig;
import server.configuration.LocalConfiguration;
import server.configuration.WorldConfig;
import server.database.Database;
import server.world.Permision;

public class SetHome implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] arg) {
		int count = 0;
		Player player = (Player)sender;
		
		if(WorldConfig.WorldSetHome.toBoolean())
		{
			if(Permision.newHome(player.getWorld()))
			{
			ResultSet result;
			try {
				result = Database.CreateStatement().executeQuery("SELECT count(*) as size FROM " + LocalConfiguration.DBPrefix.toString() + "home WHERE UUID=\""+player.getUniqueId().toString()+"\"");
				if (result.next() && result.getRow() > 0)
				{
					count = result.getInt("size");
				}
				result.close();
			}catch (SQLException e) {
				e.printStackTrace();
				return false;
			} 
			if (arg.length == 1 && count < EconomyConfig.LIMIT.toInteger() |  EconomyConfig.LIMIT.toInteger() == 0)
			{
				SubmitSetHome.SetHome( player, PriseHome.getPrise(player) ,arg[0]);
				return true;
			}
			else
				return false;
			}
			else
			{
				player.sendMessage(ChatColor.RED + WorldConfig.Message.toString());
				return true;
			}
		}
		else
		{
			player.sendMessage(ChatColor.RED + WorldConfig.Message.toString());
			return true;
		}
	}
}
