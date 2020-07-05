package server.printmessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import crystal.main.CrystalTeleport;
import server.configuration.LocalConfiguration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Message {
    private static Logger logger = CrystalTeleport.getPlugin(CrystalTeleport.class).getLogger();

    private Message() {
    }

    /**
     * Send a message to the specified CommandSender.<br />
     * Using CommandSender allows to easily send messages both to Player and ConsoleCommandSender.
     *
     * @param sender  CommandSender to forward the message to
     * @param message Message to be sent
     */
    
    public static String parseChatColors(String str) {
        if (str == null) return "";
        for (ChatColor color : ChatColor.values()) str = str.replaceAll("&" + color.getChar(), color + "");
        return str;
    }
    
    public static void send(CommandSender sender, String message) {
        if (sender == null) sender = Bukkit.getServer().getConsoleSender();
        if (message == null) return;
        message = parseChatColors(message);
        sender.sendMessage(message);
    }


    /**
     * Builds and sends a message with an attached title.<br />
     * Using CommandSender allows to easily send messages both to Player and ConsoleCommandSender.
     *
     * @param sender     CommandSender to forward the message to
     * @param titleColor Color of the title
     * @param title      Title to attach to the message
     * @param message    Message to be sent
     */
    public static void sendFormatted(CommandSender sender, ChatColor titleColor, String title, String message) {
        if (message == null) return;
        message = titleColor + "[" + title + "] " + ChatColor.WHITE + message;
        send(sender, message);
    }

    /**
     * Builds and sends a message with a green-colored title.
     *
     * @param sender  CommandSender to forward the message to
     * @param message Message to be sent
     */
    public static void sendFormattedSuccess(CommandSender sender, String message) {
        sendFormatted(sender, ChatColor.DARK_GREEN, LocalConfiguration.LogPrefix.toString(), message);
    }

    

    /**
     * Builds and sends a message with a red-colored title.
     *
     * @param sender  CommandSender to forward the message to
     * @param message Message to be sent
     */
    public static void sendFormattedError(CommandSender sender, String message) {
        sendFormatted(sender, ChatColor.DARK_RED, LocalConfiguration.LogPrefix.toString(), message);
    }


    /**
     * Broadcasts a message to all players on the server
     *
     * @param message Message to be sent
     */
    public static void broadcast(String message) {
        for (Player p : Bukkit.getServer().getOnlinePlayers())
            sendFormatted(p, ChatColor.DARK_GREEN, LocalConfiguration.LogPrefix.toString(), message);
        log(parseChatColors(message));
    }

    /**
     * Sends a message into the server log
     *
     * @param messages Messages to be sent
     */
    public static void log(String... messages) {
        for (String message : messages) logger.info(message);
    }

    /**
     * Sends a message into the server log
     *
     * @param level    Severity level
     * @param messages Messages to be sent
     */
    public static void log(Level level, String... messages) {
        for (String message : messages) logger.log(level, message);
    }

    /**
     * Sends a message into the server log if debug is enabled in the configuration.<br />
     * Should not be used if there is more then one line to be sent to the console.
     *
     * @param message Message to be sent
     */
    public static void debug(String... message) {
        if (LocalConfiguration.Debug.toBoolean()) log(message);
    }

    /**
     * Sends a message into the server log if debug is enabled in the configuration.<br />
     * Should not be used if there is more then one line to be sent to the console.
     *
     * @param level   Severity level
     * @param message Message to be sent
     */
    public static void debug(Level level, String... message) {
        if (LocalConfiguration.Debug.toBoolean()) log(level, message);
    }



    /**
     * Centers the string to take up the specified number of characters
     *
     * @param str    String to center
     * @param length New String length
     * @return New String
     */
    public static String centerString(String str, int length) {
        while (str.length() < length) {
            str = " " + str + " ";
        }
        if (str.length() > length) str = str.substring(1);
        return str;
    }
}