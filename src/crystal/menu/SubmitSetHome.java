package crystal.menu;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import crystal.economy.premium.PremiumItem;
import crystal.main.CrystalTeleport;

public class SubmitSetHome {
	public static void SetHome(Player player,double prinse,String name)
	{
		Inventory items = CrystalTeleport.server.createInventory(null, 9, ChatColor.DARK_GREEN + "Set Home");
		items.setItem(0, CrystalOption.None());
		items.setItem(1, CrystalOption.None());
		
		items.setItem(2, CrystalOption.cancel());
		
		items.setItem(3, CrystalOption.None());
		items.setItem(4, CrystalOption.None());
		items.setItem(5, CrystalOption.None());
		
		items.setItem(6, CrystalOption.submit(prinse, name));
		
		if(player.hasPermission("svip"))
		{
			items.setItem(7,PremiumItem.SubmitPremium(name, "Svip"));
		}
		else
			if(player.hasPermission("vip"))
			{
				items.setItem(7,PremiumItem.SubmitPremium(name, "Vip"));
			}
			else
			{
				items.setItem(7, CrystalOption.None());
			}		
		items.setItem(8, CrystalOption.None());
		
		player.openInventory(items);
		
	}
}
