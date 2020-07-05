package server.throwExemption;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;

import crystal.main.CrystalTeleport;
import server.configuration.LocalConfiguration;
import server.printmessage.Message;

public class ExceptionHandler {

    private static String lastError = "";

    /**
     * Display a properly formatted error log in the server console
     *
     * @param t Throwable to format
     */
    public static void handle(Throwable t) {
        handle(t, false);
    }

    /**
     * Display a properly formatted error log in the server console.
     *
     * @param t     Throwable to format
     * @param debug If <b>true</b>, or debug in the config is set to true it will print an stacktacke otherwirse supress the error.
     */
    public static void handle(Throwable t, boolean debug) {
        if (debug || !LocalConfiguration.Debug.toBoolean()) return;

        if (t.getLocalizedMessage().equalsIgnoreCase(lastError)) return;
        else lastError = t.getClass().getName();
        PluginDescriptionFile description = CrystalTeleport.getPlugin(CrystalTeleport.class).getDescription();
        Message.log(
                "+-------------- [ CrystalTeleport ] --------------+",
                "| The plugin 'CrystalTeleport' has caused an error.",
                "| Please, create a new ticket with this error at",
                "| " + description.getWebsite(),
                "| ",
                "| Bukkit   : " + Bukkit.getVersion(),
                "| Plugin   : " + description.getFullName(),
                "| Version  : " + description.getVersion(),
                "| Error    : " + t.getClass().getName(),
                "|            " + t.getLocalizedMessage(),
                "+---------------------------------------------------+",
                "| The stack trace of the error follows: ",
                "| ",
                "| " + t.getClass().getName()
        );
        for (StackTraceElement element : t.getStackTrace()) {
            Message.log("| at " + element.toString());
        }
        Message.log(
                "| Multiple errors might have occurred, only",
                "| one stack trace is shown.",
                "+--------------------------------------------+"
        );
    }

 
}