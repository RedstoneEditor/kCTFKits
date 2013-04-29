package tk.redwirepvp.ctfkits.Mage;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tk.redwirepvp.ctfkits.Main;

public class CommandMage implements CommandExecutor{
	private Main p;
	public CommandMage(Main i) {
		p = i;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String tag,
			String[] args) {
		if(cmd.getName().equalsIgnoreCase("mage")){
			if(!p.mage.players.contains(sender.getName())){
				p.mage.players.add(sender.getName());
			}
			if(!p.users.contains(sender.getName())){
				p.users.add(sender.getName());
			}
			
			FileConfiguration config = null;
			File file = new File("plugins"+File.separator+"kCTFKits"+File.separator+"inventories"+File.separator+sender.getName()+".yml");
			if(!file.exists()){
			        try {
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        config = YamlConfiguration.loadConfiguration(file);
			        config.set("inventory", p.inv.InventoryToString(((Player)sender).getInventory()));

			        try {
						config.save(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			p.mage.giveKit((Player) sender);
			((Player)sender).setExp(1F);
			return true;
		}
		return false;
	}

}
