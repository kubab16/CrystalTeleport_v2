package crystal.economy;

import org.bukkit.entity.Player;

import crystal.main.CrystalTeleport;
import net.milkbowl.vault.economy.Economy;

public class playerPrize  {
	public static double m;
	public static Economy econ = CrystalTeleport.economy;
	public static double  GetMoney(Player p) {
	        double m = econ.getBalance(p);
	        return m;
	    }
	public static Boolean  Transation(Player p, double subtractio) {
		
        double m = econ.getBalance(p);
        if (m > subtractio)
        {
        	m -= subtractio;
        	econ.depositPlayer(p, m);
        	return true;
        }
        else
        {
        	return false;
        }
    }
}
