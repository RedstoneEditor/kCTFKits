package tk.redwirepvp.ctfkits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlayerListener implements Listener{
	private Main p;
	public PlayerListener(Main i) {
		p = i;
	}
	
	@EventHandler
    public void onEntityDeath (EntityDeathEvent event) {
		if(event.getEntity() instanceof Player)
        if(p.players.containsKey(((Player)event.getEntity()).getName()))
        event.setDroppedExp(0);
    }

}
