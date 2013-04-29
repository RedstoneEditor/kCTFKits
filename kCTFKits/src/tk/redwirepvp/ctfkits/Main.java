package tk.redwirepvp.ctfkits;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import tk.redwirepvp.ctfkits.Mage.CommandMage;
import tk.redwirepvp.ctfkits.Mage.Mage;
import tk.redwirepvp.ctfkits.Mage.MageListener;
import tk.redwirepvp.ctfkits.commands.QuitKitsCommand;
import tk.redwirepvp.ctfkits.utils.InventoryStringDeSerializer;
import tk.redwirepvp.ctfkits.utils.ItemUtil;

public class Main extends JavaPlugin{
	public Mage mage;
	public ItemUtil itemUtil;
	public HashMap<String, String> players = new HashMap<String, String>();
	public InventoryStringDeSerializer inv = new InventoryStringDeSerializer();
	public Scoreboard board;
	public void onEnable(){
		
		itemUtil = new ItemUtil();
		mage = new Mage(this);
		getCommand("mage").setExecutor(new CommandMage(this));
		getCommand("quitkits").setExecutor(new QuitKitsCommand(this));

		getServer().getPluginManager().registerEvents(new MageListener(this), this);
		getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		Objective objective = board.registerNewObjective("kills", "totalKillCount");
	    //Setting where to display the scoreboard/objective (either SIDEBAR, PLAYER_LIST or BELOW_NAME)
	    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	     
	    //Setting the display name of the scoreboard/objective
	    objective.setDisplayName(ChatColor.GREEN + "Kills");
	    for(Player online : Bukkit.getOnlinePlayers()){
	    	if(players.containsKey(online.getName()))
	    	online.setScoreboard(board);
	    	}
	}

	
	public void onDisable(){
		
	}
	
}
