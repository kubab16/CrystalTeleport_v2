package crystal.main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import server.configuration.TeleportMessage;
import server.configuration.WorldConfig;

public class colldown implements Listener{
	@SuppressWarnings("deprecation")
	@EventHandler
	   public void onMove(PlayerMoveEvent e) {
		Player player = (Player)e.getPlayer();
		if (CrystalTeleport.coldown.containsKey(player.getUniqueId()))
		{			
			final Location from = e.getFrom();
		    final Location to = e.getTo();
	        if(from.getX() != to.getX() || from.getZ() != to.getZ() || from.getY() != to.getY())
	        {	   
	        	player.resetTitle();
	        	CrystalTeleport.coldown.remove(player.getUniqueId());
	        	player.sendTitle(ChatColor.RED+TeleportMessage.CALNEL.toString(), "");
	        	}
		}
		
    }
	@SuppressWarnings("deprecation")
	public static boolean cooldown(Player player, Location location,ItemStack item)
	{
		if (CrystalTeleport.coldown.containsKey(player.getUniqueId()))
		{
			CrystalTeleport.coldown.remove(player.getUniqueId());
		}
		Location previuce = player.getLocation();
		CrystalTeleport.coldown.put(player.getUniqueId(), true);
		
		player.resetTitle();
		player.sendTitle(ChatColor.GREEN+TeleportMessage.STRART.toString(), ChatColor.DARK_GREEN+TeleportMessage.TIME.toString()+" "+WorldConfig.Coldown.toInteger());
		
		 new BukkitRunnable() {
		        
	            @Override
	            public void run() {
	            	if (CrystalTeleport.coldown.containsKey(player.getUniqueId()))
			    	{
			    		if ( CrystalTeleport.coldown.get(player.getUniqueId()) == true && RemoveItem(item,player))
				    	{
			    			CrystalTeleport.coldown.remove(player.getUniqueId());
			    			player.teleport(location);
				    	}
			    				
			    	}
			    	else
			    	{
			    		CrystalTeleport.coldown.remove(player.getUniqueId());
			    	}
	            

	            }
		 }.runTaskLater(CrystalTeleport.instance, 20*WorldConfig.Coldown.toInteger());
		
		if (previuce != player.getLocation())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean RemoveItem(ItemStack item, Player player)
	{
		if(player.getInventory().contains(item))
		{
			ItemStack addItem = item.clone();
			addItem.setAmount(item.getAmount()-1);
			
			player.getInventory().remove(item);
			
			player.getInventory().addItem(addItem);
			return true;
		}
		else
		{
			player.sendMessage(ChatColor.RED+"No item detected");
			return false;
		}
		
	}
}

