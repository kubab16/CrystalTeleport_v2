package crystal.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import crystal.main.CrystalTeleport;
import crystal.menu.CustomInventory;

public class OpenMenu implements Listener{
	
	@EventHandler
	public void onPlayerClicks(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = player.getInventory().getItemInMainHand();
		if ( action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR))
		{
			if (!item.hasItemMeta()) {
				return;
			}
			if ( item.getItemMeta().getLocalizedName().equals( "CrystalTeleportOpen" ) ) 
			{
				event.setCancelled(true);
				
				if(CrystalTeleport.inventory.containsKey(player.getUniqueId()))
				{
					CrystalTeleport.inventory.replace(player.getUniqueId(), new CustomInventory(player));
				}
				else
				{
					CrystalTeleport.inventory.put(player.getUniqueId(), new CustomInventory(player));
				}
				if(CrystalMenuOption.ItemOpenMenu.containsKey(player.getUniqueId()))
				{
					CrystalMenuOption.ItemOpenMenu.replace(player.getUniqueId(), item);
				}
				else
				{
					CrystalMenuOption.ItemOpenMenu.put(player.getUniqueId(), item);
				}
			}
		}
			
	}	
}

