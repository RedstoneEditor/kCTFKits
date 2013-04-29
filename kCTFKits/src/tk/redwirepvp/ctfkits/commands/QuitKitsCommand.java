package tk.redwirepvp.ctfkits.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import tk.redwirepvp.ctfkits.Main;

public class QuitKitsCommand implements CommandExecutor {

	private Main p;

	public QuitKitsCommand(Main i) {
		p = i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String tag,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("quitkits")) {
			if (sender instanceof Player) {
				if (p.users.contains(((Player) sender).getName())) {
					((Player) sender).getInventory().clear();
					FileConfiguration config = null;
					File file = new File("plugins" + File.separator
							+ "kCTFKits" + File.separator + "inventories"
							+ File.separator + sender.getName() + ".yml");
					config = YamlConfiguration.loadConfiguration(file);
					((Player) sender).getInventory().setContents(
							p.inv.StringToInventory(
									(String) config.get("inventory"))
									.getContents());
					file.delete();

					((Player) sender).updateInventory();
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "You aren't using a kit!");
					return true;
				}
			}
		}
		return false;
	}

}
