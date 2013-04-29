package tk.redwirepvp.ctfkits;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import tk.redwirepvp.ctfkits.Mage.CommandMage;
import tk.redwirepvp.ctfkits.Mage.Mage;
import tk.redwirepvp.ctfkits.Mage.MageListener;
import tk.redwirepvp.ctfkits.commands.QuitKitsCommand;
import tk.redwirepvp.ctfkits.utils.InventoryStringDeSerializer;
import tk.redwirepvp.ctfkits.utils.ItemUtil;

public class Main extends JavaPlugin{
	public Mage mage;
	public ItemUtil itemUtil;
	public ArrayList<String> users = new ArrayList<String>();
	public InventoryStringDeSerializer inv = new InventoryStringDeSerializer();
	
	public void onEnable(){
		
		itemUtil = new ItemUtil();
		mage = new Mage(this);
		getCommand("mage").setExecutor(new CommandMage(this));
		getCommand("quitkits").setExecutor(new QuitKitsCommand(this));

		getServer().getPluginManager().registerEvents(new MageListener(this), this);
	}

	
	public void onDisable(){
		
	}
	
}
