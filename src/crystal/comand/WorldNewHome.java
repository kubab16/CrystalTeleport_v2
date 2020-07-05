package crystal.comand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import server.configuration.LocalConfiguration;
import server.world.Permision;

public class WorldNewHome implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		
		if((sender.isOp() || sender.hasPermission("admin"))&& LocalConfiguration.ComandGive.toBoolean() && arg.length == 1)
		{	
			Player player = (Player)sender;
			switch(arg[0])
			{
			case "true":
				Permision.SetPermision(player, true);
				player.sendMessage(ChatColor.GREEN+"newhome this world true");
				return true;
			case "false":
				Permision.SetPermision(player, false);
				player.sendMessage(ChatColor.RED+"newhome this world false");
				return true;
			default:
				return false;
			}
			
		}
		return false;
	}

}
