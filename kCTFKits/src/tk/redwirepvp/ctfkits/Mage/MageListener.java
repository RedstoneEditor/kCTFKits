package tk.redwirepvp.ctfkits.Mage;

import java.lang.reflect.Field;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import tk.redwirepvp.ctfkits.Main;

public class MageListener implements Listener {
	private Main p;

	public MageListener(Main i) {
		p = i;
	}
	
	public int xpid;

	public HashMap<Arrow, Boolean> arrows = new HashMap<Arrow, Boolean>();

	@EventHandler
	public void onProjectileLaunch(final ProjectileLaunchEvent event) {
		System.out.println(event.getEntity().getShooter());
		if (this.p.mage.players.contains(((Player) event.getEntity()
				.getShooter()).getName())) {
			arrows.put((Arrow) event.getEntity(), false);
			((Player) event.getEntity().getShooter()).setExp(0F);
		xpid = Bukkit.getScheduler().scheduleSyncRepeatingTask(p,
					new Runnable() {
						public void run() {
							if ((((Player) event.getEntity().getShooter())
									.getExp() < 1F)) {
								((Player) event.getEntity().getShooter()).setExp(((Player) event
										.getEntity().getShooter()).getExp() + 0.05F);
							} else {
								Bukkit.getScheduler().cancelTask(xpid);
								((Player) event.getEntity().getShooter())
								.setExp(1F);
							}
						}
					}, 0L, 1L);
			Bukkit.getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
				public void run() {
					if (arrows.containsKey(event.getEntity())
							&& arrows.get(event.getEntity()) == false) {
						event.getEntity().remove();
						playFirework(event.getEntity().getWorld(), event
								.getEntity().getLocation());
					}
				}
			}, 5L);
		}
	}

	@EventHandler
	public void rightClick(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getPlayer().getItemInHand().getType() == Material.DIAMOND_HOE) {
				if(event.getPlayer().getExp() >= 1F){
				event.setCancelled(true);
				this.p.mage.fireArrows(event.getPlayer().getWorld(),
						event.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		System.out.println("Projectile hit!");
		Entity entity = event.getEntity();
		System.out.println(((Arrow) entity).getShooter());
		if (entity instanceof Arrow) {
			System.out.println("Projectile was an arrow!");
			Player player = (Player) ((Arrow) entity).getShooter();
			if (((Arrow) entity).getShooter() instanceof Player) {
				System.out.println("Shooter was a Player!");
				System.out.println(p.mage.players);
				if (this.p.mage.players.contains(player.getName())) {
					System.out.println("Player was a Mage!");
					System.out.println(player.getName());
					Arrow arrow = (Arrow) event.getEntity();
					arrow.remove();
					arrows.remove(arrow);
					playFirework(event.getEntity().getWorld(), event
							.getEntity().getLocation());
				}
			}
		}
	}

	public void playFirework(World w, Location l) {
		try {
			Firework fw = (Firework) w.spawn(l, Firework.class);
			FireworkMeta fwm = fw.getFireworkMeta();
			FireworkEffect effect = FireworkEffect.builder().trail(false)
					.flicker(true).withColor(Color.RED).withColor(Color.PURPLE)
					.with(Type.BALL).build();
			fwm.clearEffects();
			fwm.addEffect(effect);
			try {
				Field f = fwm.getClass().getDeclaredField("power");
				f.setAccessible(true);
				f.set(fwm, -2);
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			fw.setFireworkMeta(fwm);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
