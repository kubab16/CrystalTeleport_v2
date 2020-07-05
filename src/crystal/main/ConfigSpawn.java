
package crystal.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import server.printmessage.Message;

public class ConfigSpawn {
	private static File folder = null;
	
	// Files & File Configs Here
	public static FileConfiguration spawncfg;
	public static File spawnfile;
	// --------------------------
	
	public ConfigSpawn()
	{
		ConfigSpawn.folder = CrystalTeleport.Folder;
		setup();
	}
	
	public void setup() {
		if (!folder.exists()) {
			folder.mkdir();
		}

		spawnfile = new File(folder, "spawn.yml");

		if (!spawnfile.exists()) {
			try {
				spawnfile.createNewFile();
				Message.log(ChatColor.GREEN + "The spawn.yml file has been created");
			} catch (IOException e) {
				Message.log(ChatColor.RED + "Could not create the spawn.yml file");
			}
		}

		spawncfg = YamlConfiguration.loadConfiguration(spawnfile);
	}
	
	public static FileConfiguration MainSpawn()
	{
		return spawncfg;
	}
	
	public static Location getLocation() 
	{
		World world = Bukkit.getServer().getWorld(UUID.fromString(spawncfg.getString("spawn.world")));
		double x = spawncfg.getDouble("spawn.x");
		double y = spawncfg.getDouble("spawn.y");
		double z = spawncfg.getDouble("spawn.z");
		return new Location(world, x , y , z);
	}
	
	public static void saveSpawn() {
		try {
			spawncfg.save(spawnfile);
			Message.log("The players.yml file has been saved");

		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage("Could not save the players.yml file");
		}
	}

	public void reloadSpawn() {
		spawncfg = YamlConfiguration.loadConfiguration(spawnfile);
		Message.log(ChatColor.BLUE + "The players.yml file has been reload");
		saveSpawn();
	}
	
	public static boolean setSpawn(Location location)
	{
		spawncfg.set("spawn.world", location.getWorld().getUID().toString());
		spawncfg.set("spawn.x", location.getBlockX());
		spawncfg.set("spawn.y", location.getBlockY());
		spawncfg.set("spawn.z", location.getBlockZ());
		saveSpawn();
		return true;
	}
	
	public static boolean setSpawn( Player player)
	{
		return setSpawn(player.getLocation());
	}
}
