package crystal.comand;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import crystal.teleport.CreateCrystalTeleport;
import server.configuration.LocalConfiguration;

public class GiveCrystal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String alias, String[] arg) {
		if((sender.isOp() || sender.hasPermission("admin"))&& LocalConfiguration.ComandGive.toBoolean() && arg.length == 1)
		{	
			Player player = (Player)sender;
			switch(arg[0])
			{
			case "home":
				ItemStack crystal = CreateCrystalTeleport.crystal();
				player.getInventory().addItem(crystal);
				return true;
			case "spawn":
				ItemStack spawm = CreateCrystalTeleport.spawn();				
				player.getInventory().addItem(spawm);
				return true;
			default:
				return false;
			}
			
		}
		return false;
	}

}
