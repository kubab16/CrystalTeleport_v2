package crystal.comand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import crystal.main.CrystalTeleport;

public class CrystalInfo implements CommandExecutor{
	private static CrystalTeleport instance = CrystalTeleport.instance;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] arg) {
		Player player = (Player)sender;
		player.sendMessage(ChatColor.GREEN+"------------CrystalTeleport------------");
		player.sendMessage(ChatColor.GREEN+"Official Discord:"+ChatColor.GOLD+" https://discord.gg/Ksyv8xw");
		player.sendMessage(ChatColor.GREEN+"Version: "+ ChatColor.GOLD + instance.getDescription().getVersion());
		player.sendMessage(ChatColor.GREEN+"Autor: "+ ChatColor.GOLD + instance.getDescription().getAuthors());
		player.sendMessage(ChatColor.GREEN+"License: "+ ChatColor.GOLD + instance.getDescription().getDescription());
		player.sendMessage(ChatColor.GREEN+"Github: "+ ChatColor.GOLD + "https://github.com/kubab16/CrystalTeleport_v2");
		player.sendMessage(ChatColor.GREEN+"--------------------------------------");
		return true;
	}
	
}
