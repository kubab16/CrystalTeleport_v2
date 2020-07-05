package crystal.comand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import crystal.main.ConfigSpawn;
import server.configuration.WorldConfig;
import server.database.Database;
import server.world.Spawn;

public class SetSpawn implements CommandExecutor{
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] arg) {
		
			Player player = (Player)sender;
			boolean ok = false;
			if (player.hasPermission("admin"))
			{
				if(WorldConfig.MuliSpawn.toBoolean())
				{
					ok =  Spawn.NewSpawn(player);
					Database.reconnect();
					return ok;
				}
				else
				{
					
					if(ConfigSpawn.setSpawn(player))
					{
						player.sendMessage(ChatColor.GOLD+"setspawn");
						return true;
					}
					else
					{
						return false;
					}
					
				}
			}
			else
			{
				return false;
			}
	}
}
