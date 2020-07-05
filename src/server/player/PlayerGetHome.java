package server.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import crystal.economy.premium.PremiumItem;
import crystal.menu.CrystalOption;
import server.configuration.LocalConfiguration;
import server.database.Database;
import server.printmessage.Message;

public class PlayerGetHome {

	private static  List<ItemStack> homes = new ArrayList<ItemStack>();

	public static List<ItemStack> getHome(Player player)
	{
		if (Database.isClosed())
		{
			Database.reconnect();
		}
		ResultSet results = Database.executeQuery("SELECT id_home,name,x,y,z,world FROM "+ LocalConfiguration.DBPrefix.toString() + "home WHERE uuid='"+
				player.getUniqueId().toString()+"'");
		try {
			while (results.next())
			 {
				ItemStack item = CrystalOption.CreateHomeOption(results.getString("name"),
						Bukkit.getServer().getWorld(UUID.fromString(results.getString("world"))).getName()
						, results.getInt("id_home"));
				if(!homes.add(item))
				{
					Message.log(Level.SEVERE, "ERROR load home " + player.getName());
				}
			 }
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		results = Database.executeQuery("SELECT id_home,name,x,y,z,world,type FROM "+ LocalConfiguration.DBPrefix.toString() + "premium WHERE uuid='"+
				player.getUniqueId().toString()+"'");
		/**
		 * Premium item generator
		 */
		try {
			while (results.next())
			 {
				ItemStack item = PremiumItem.CreateHomeOptionPremium(results.getString("name"),
						Bukkit.getServer().getWorld(UUID.fromString(results.getString("world"))).getName()
						, results.getInt("id_home"),results.getString("type"));
				homes.add(item);
			 }
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return homes;
	}
	
	public static List<ItemStack> getHomeDelete(Player player)
	{
		if (Database.isClosed())
		{
			Database.reconnect();
		}
		ResultSet results = Database.executeQuery("SELECT id_home,name,x,y,z,world FROM "+ LocalConfiguration.DBPrefix.toString() + "home WHERE uuid='"+
				player.getUniqueId().toString()+"'");
		try {
			while (results.next())
			 {
				ItemStack item = CrystalOption.DeleteHomeOption(results.getString("name"),
						Bukkit.getServer().getWorld(UUID.fromString(results.getString("world"))).getName()
						, results.getInt("id_home"));
				if(!homes.add(item))
				{
					Message.log(Level.SEVERE, "ERROR load home " + player.getName());
				}
			 }
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		results = Database.executeQuery("SELECT id_home,name,x,y,z,world,type FROM "+ LocalConfiguration.DBPrefix.toString() + "premium WHERE uuid='"+
				player.getUniqueId().toString()+"'");
		/**
		 * Premium item generator
		 */
		try {
			while (results.next())
			 {
				ItemStack item = PremiumItem.DeleteHomeOptionPremium(results.getString("name"),
						Bukkit.getServer().getWorld(UUID.fromString(results.getString("world"))).getName()
						, results.getInt("id_home"),results.getString("type"));
				homes.add(item);
			 }
			results.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return homes;
	}
}
