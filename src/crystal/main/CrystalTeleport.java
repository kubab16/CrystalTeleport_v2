package crystal.main;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.gson.Gson;

import crystal.comand.CrystalInfo;
import crystal.comand.GiveCrystal;
import crystal.comand.SetHome;
import crystal.comand.SetSpawn;
import crystal.comand.WorldNewHome;
import crystal.event.CrystalMenuOption;
import crystal.event.OpenMenu;
import crystal.event.Spawn;
import crystal.menu.CustomInventory;
import server.configuration.EconomyConfig;
import server.configuration.LocalConfiguration;
import server.configuration.MenuConfig;
import server.configuration.PremiumConfig;
import server.configuration.TeleportMessage;
import server.configuration.WorldConfig;
import server.database.Database;
import server.printmessage.Message;

import java.util.logging.Logger;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;


public class CrystalTeleport extends JavaPlugin{
	public static final HashSet<String> working = new HashSet<>();
    public static CrystalTeleport instance;
    public static File Folder = null;
    public static boolean paused;
    public static boolean crashed;
    public static Gson gson;
    public static Server server = null;
    public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;
    public static FileConfiguration config = null;
    public static HashMap<UUID, Boolean> coldown = new HashMap<UUID, Boolean>();
    public static HashMap<UUID, CustomInventory> inventory = new HashMap<UUID, CustomInventory>();
    private static final Logger log = Logger.getLogger("Minecraft");

    
    
    /**
     * <b>Default constructor</b><br />
     * Creates a new instance of the Statistics.
     */
    public CrystalTeleport() {
        instance = this;
        paused = true;
        crashed = false;
        Folder = getDataFolder();
        gson = new Gson();
    }

    public static synchronized boolean isWorking(final String thread) {
        return working.contains(thread);
    }
	
	@Override
    public void onEnable(){
		saveResource("spawn.yml", false);
		new ConfigSpawn();
		server = this.getServer();
		configLoad();
		BukkitScheduler scheduler = getServer().getScheduler();
		databaseLoad();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
	            @Override
	            public void run() {
	            	reconect();           	
	            }
	    }, 20*60, 20*60*LocalConfiguration.DBReconnect.toInteger());   
		
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	clearCashe();           	
            }
    }, 20*60, 20*60*LocalConfiguration.DBReconnect.toInteger());   
	        
        loadComand();
		loadEvent();
		if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();

	}
	@Override
	public void onDisable() {
		if (crashed) {
            crashed = false;
            return;
        }
		Message.log("End work!");
	}
		
	
	public void loadComand()
	{
		getCommand("giveCrystal").setExecutor(new GiveCrystal());
		getCommand("newhome").setExecutor(new SetHome());
		getCommand("setspawn").setExecutor(new SetSpawn());
		getCommand("worldnewhome").setExecutor(new WorldNewHome());	
		getCommand("crystalinfo").setExecutor(new CrystalInfo());	
	}
	
	public void loadEvent()
	{
		this.getServer().getPluginManager().registerEvents(new OpenMenu(),this);
		this.getServer().getPluginManager().registerEvents(new CrystalMenuOption(),this);
		this.getServer().getPluginManager().registerEvents(new Spawn(),this);
		this.getServer().getPluginManager().registerEvents(new colldown(),this);
	}
	public void loadConfig()
	{
		
	}
	/**
	 * Vault setup
	 */
	 @SuppressWarnings("static-access")
	public synchronized void setWorking(final String thread, final boolean working) {
	        if (working)
	            this.working.add(thread);
	        else
	            this.working.remove(thread);
	    }

	    public synchronized boolean isWorking() {
	        return working.size() > 0;
	    }
	    
	    private boolean setupPermissions()
	    {
	        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
	        if (permissionProvider != null) {
	            permission = permissionProvider.getProvider();
	        }
	        return (permission != null);
	    }

	    private boolean setupChat()
	    {
	        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
	        if (chatProvider != null) {
	            chat = chatProvider.getProvider();
	        }

	        return (chat != null);
	    }

	    private boolean setupEconomy()
	    {
	        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	        if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	        }

	        return (economy != null);
	    }
	    
	    private void configLoad()
	    {
	    	if (!new File(Folder, "config.yml").exists()) {
	            Message.log("Config.yml not found. Creating a one for you.");
	            saveDefaultConfig();
	            crashed = true;
	            this.setEnabled(false);
	            return;
	        }
		 config = getConfig();
	    }
	    
	    private void databaseLoad()
	    {
	    	try {
	            new Database();
	        } catch (Exception e) {
	            crashed = true;
	            Message.log(Level.SEVERE, "Cannot establish a database connection!");
	            Message.log(Level.SEVERE, "Is the plugin set up correctly?");
	            if (LocalConfiguration.Debug.toBoolean()) e.printStackTrace();
	            this.setEnabled(false);
	            return;
	        }
	    	Message.log("Database connection established.");
	    	LocalConfiguration.clearCache();
	    }
	    
	    private void reconect()
	    {
	    	try {
	            new Database();
	        } catch (Exception e) {
	            crashed = true;
	            Message.log(Level.SEVERE, "Cannot establish a database connection!");
	            Message.log(Level.SEVERE, "Is the plugin set up correctly?");
	            if (LocalConfiguration.Debug.toBoolean()) e.printStackTrace();
	            this.setEnabled(false);
	            return;
	        }
	    	LocalConfiguration.clearCache();
	    }
	    
	    private void clearCashe() {
			EconomyConfig.clearCache();
			MenuConfig.clearCache();
			PremiumConfig.clearCache();
			TeleportMessage.clearCache();
			WorldConfig.clearCache();
		}
}
