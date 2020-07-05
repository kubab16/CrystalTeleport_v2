package server.configuration;

import java.util.List;

import crystal.main.CrystalTeleport;
import server.throwExemption.ExceptionHandler;

public enum LocalConfiguration {

    Debug("debug"),
    DBHost("database.host"),
    DBPort("database.port"),
    DBName("database.name"),
    DBUser("database.user"),
    DBPass("database.pass"),
    DBPrefix("database.prefix"),
    DBConnect("jdbc:mysql://" + DBHost.toString() + ":" + DBPort.toInteger() + "/" + DBName.toString(), true),
    DBReconnect("database.reconnect_interval"),
    LogPrefix("log-prefix"),
    ComandGive("comand.give"),
    CrystalName("crystal.name"),
    SpawnName("spawn.name"),
    CrystalHomeLore("crystal.lore"),
    CrystalSpawnLore("spawn.lore"),;

    private String node;
    private boolean fixedValue;
    private boolean cached;
    private Object cachedValue;

    /**
     * Default constructor
     * Used to pull the configuration node values from config.yml
     *
     * @param node Configuration node
     */
    LocalConfiguration(String node) {
        this.node = node;
        this.fixedValue = false;
        this.cached = false;
        this.cachedValue = null;

        updateCache();
    }

    /**
     * Constructor
     * Used to permanently store a default value
     *
     * @param value      Value to store
     * @param fixedValue <b>true</b> to finalize the value
     */
    LocalConfiguration(Object value, boolean fixedValue) {
        if (fixedValue) {
            this.node = "";
            this.fixedValue = fixedValue;
            this.cached = false;
            this.cachedValue = value;
        } else {
            this.node = (String) value;
            this.fixedValue = fixedValue;
            this.cached = false;
            this.cachedValue = null;

            updateCache();
        }
    }

    /**
     * Clears the cache for all nodes
     */
    public static void clearCache() {
        for (LocalConfiguration configNode : LocalConfiguration.values()) {
            configNode.cached = false;
        }
    }

    /**
     * Returns the cached value of the node, and updates the cache if necessary
     *
     * @return Node value
     */
    private Object getCachedValue() {
        if (!cached) updateCache();
        return cachedValue;
    }

    /**
     * Returns the value of the node
     *
     * @return Node value
     */
    public Object getValue() {
        if (fixedValue) return cachedValue;
        else return getCachedValue();
    }

    /**
     * Returns the value of the node as a String
     *
     * @return Node value
     */
    public String toString() {
        try {
            return (String) getValue();
        } catch (Throwable t) {
            ExceptionHandler.handle(t);
            return "";
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<Object> getList()
    {
    	if (!cached) updateCache();
    	
    	 try {
             List<Object> list = (List<Object>) CrystalTeleport.config.getList(node);
			return list;
         } catch (Throwable t) {
             ExceptionHandler.handle(t);
             return null;
         }
    }

    /**
     * Returns the value of the node as a Boolean
     *
     * @return Node value
     */
    public Boolean toBoolean() {
        try {
            return (Boolean) getValue();
        } catch (Throwable t) {
            ExceptionHandler.handle(t);
            return false;
        }
    }

    /**
     * Returns the value of the node as an Integer
     *
     * @return Node value
     */
    public Integer toInteger() {
        try {
            return (Integer) getValue();
        } catch (Throwable t) {
            ExceptionHandler.handle(t);
            return 0;
        }
    }

    /**
     * Updates the cached node value
     */
    private void updateCache() {
        cachedValue = CrystalTeleport.config.get(node);
        cached = true;
    }
}
