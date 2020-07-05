package crystal.teleport;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import server.configuration.LocalConfiguration;

public class CreateCrystalTeleport {
	static ItemStack itemstack = null;
	public static ItemStack crystal(){
		
		itemstack = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta meta = itemstack.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + LocalConfiguration.CrystalName.toString());
		 
		List<String> lore = new ArrayList<>();
		List<Object> textlore = LocalConfiguration.CrystalHomeLore.getList();
		for(Object add: textlore)
		{
			lore.add(add.toString());
		}
		lore.add("CrystalTeleport");
		meta.setLocalizedName("CrystalTeleportOpen");
		meta.setLore(lore);
		meta.hasEnchants();
		
		itemstack.setItemMeta(meta);
		
		return itemstack;	
	}
public static ItemStack spawn(){
		
		itemstack = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = itemstack.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + LocalConfiguration.SpawnName.toString());
		 
		
		List<String> lore = new ArrayList<>();
		lore.add("CrystalTeleport");
		meta.setLocalizedName("CrystalTeleportSpawn");
		meta.setLore(lore);
		meta.hasEnchants();
		
		itemstack.setItemMeta(meta);
		
		return itemstack;	
	}
}
