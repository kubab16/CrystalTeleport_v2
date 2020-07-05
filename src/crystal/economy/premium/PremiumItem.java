package crystal.economy.premium;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PremiumItem {
	public static ItemStack CreateHomeOptionPremium(String name, String WordName, int id, String type) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD+"#"+name);
		
		
		meta.setLocalizedName("PremiumTeleport");
		
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN+"Świat: "+ WordName);
		lore.add(ChatColor.GOLD+type);
		lore.add("CrystalTeleportPremium");
		meta.setLore(lore);
		
		
		meta.setCustomModelData(id);
		
		item.setItemMeta(meta);
		
		return item;
	} //Create home option
	
	public static ItemStack DeleteHomeOptionPremium(String name, String WordName, int id, String type) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta meta = item.getItemMeta();
		
		
		meta.setDisplayName(name);
		meta.setLocalizedName("deletePremium");
		
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN+"Świat: "+ WordName);
		lore.add(ChatColor.RED+"delete");
		lore.add(ChatColor.GOLD+type);
		meta.setLore(lore);
		
		
		meta.setCustomModelData(id);
		
		item.setItemMeta(meta);
		
		return item;
	} //Create delete home option
	
	public static ItemStack SubmitPremium(String name, String WordName, int count, int max) {
		ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta meta = item.getItemMeta();
		
		
		
		meta.setLocalizedName("addPremiumHome");
		
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GREEN +"Świat: "+ WordName);
		lore.add(ChatColor.GRAY + "" + count+  " / "+max);
		meta.setLore(lore);
		
		
		
		item.setItemMeta(meta);
		
		return item;
	} //Create delete home option
	
	public static ItemStack SubmitPremium(String name, String type) {
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(ChatColor.GOLD+name);
		
		meta.setLocalizedName("addPremiumHome");
		
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.GOLD+type);
		meta.setLore(lore);
		
		
		
		item.setItemMeta(meta);
		
		return item;
	} //Create subbmit
}
