package crystal.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import crystal.main.CrystalTeleport;
import server.player.PlayerGetHome;
import server.printmessage.Message;

public class CustomInventory implements Listener{
	public int page = 0; 
	public Player player = null;
	public int pageDisplay = 0;
	public ArrayList<Inventory> items = new ArrayList<Inventory>();
	public CustomInventory(Player player)
	{
		this.player = player;
		newInventory(player);
	}
	public CustomInventory(Player player, int page)
	{
		this.pageDisplay = page;
		this.player = player;
		newInventory(player);
	}
	
	public ArrayList<Inventory> newInventory(Player player) {
		
		
		items = Page(player);
		if (items.size() != 0)
		{
			Inventory inventory = items.get(pageDisplay);
			player.openInventory(inventory);
		}			
		else
			Message.log(Level.WARNING,"Warming load homes");
		return items;
	}
	
	private ArrayList<Inventory> Page(Player player)
	{
		List<ItemStack> home = PlayerGetHome.getHome(player);
		 ArrayList<Inventory> pages = new ArrayList<Inventory>();
		int sizeS = home.size();
		if( sizeS % 9 == 0 )
		{
			page = sizeS / 9;
		}
		else
		{
			page = ( sizeS / 9 ) + 1;
		}
		int i = 0;
		for (int p = 0; p < page; p++)
		{
			Inventory inventory = CrystalTeleport.server.createInventory(null, 18, ChatColor.DARK_GREEN + "Crystal Teleport: "+ ( p + 1 ) +"/" + page );
			if (p == page - 1)
			{
				int it;
				for (it = 0 ; it <=8; it++)
				{	
					if(i<sizeS)
						inventory.setItem(it, home.get(i));//Create item in menu
					else
						inventory.setItem(it, CrystalOption.None());
					i++;
				}
			}
			else
			{
				for(int it = 0 ; it <=8;it++)
				{
					inventory.setItem(it, home.get(i));//Create item in menu
					i++;
				}	
			}
			if (p>0) 
			{
				inventory.setItem(9,CrystalOption.previuce());
			}
			else
			{
				inventory.setItem(9, CrystalOption.None());
			}
			inventory.setItem(10, CrystalOption.None());
			inventory.setItem(11, CrystalOption.None());
			inventory.setItem(12, CrystalOption.None());
			inventory.setItem(13, CrystalOption.delete());
			inventory.setItem(14, CrystalOption.None());
			inventory.setItem(15, CrystalOption.None());
			inventory.setItem(16, CrystalOption.None());
			
			if (p<page-1) 
			{
				inventory.setItem(17,CrystalOption.next());
			}
			else
			{
				inventory.setItem(17, CrystalOption.None());
			
			
			}
			pages.add(inventory);
		}
		home.clear();
		
		if(pageDisplay >= page)
		{
			pageDisplay--;
		}
		
		return pages;				
	}
	
	
	public boolean openPage(int page)
	{
		Inventory inventory = items.get(page);
		if (inventory != null)
		{
			this.player.openInventory(inventory);
			return true;
		}
		else
			return false;
	}
	
	public boolean nextPage()
	{
		this.pageDisplay++;
		return openPage();
	}
	
	public boolean previucePage()
	{
		this.pageDisplay--;
		return openPage();
	}
	
	public boolean openPage()
	{	
		Inventory inventory = items.get(this.pageDisplay);
		if (inventory != null)
		{
			this.player.openInventory(inventory);
			return true;
		}
		else
			return false;
	}
	
	public int getPage()
	{
		return this.pageDisplay;
	}
}
