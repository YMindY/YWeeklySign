package yxmingy.yweeklysign;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class Main extends PluginBase implements Listener{
	private Config conf = new Config(getDataFolder()+"/Config.yml",Config.YAML);
	public void onEnable() {
		getLogger().info("YWeeklySign in Enabled! Author: xMing.");
		getServer().getPluginManager().registerEvents(this, this);
		if(conf.getAll().isEmpty()) {
			String[][] cmdsString = new String[7][1];
			int i = 1;
			for(;i<=7;i++) {
				cmdsString[i-1][0] = "say @p 已领取周"+String.valueOf(i)+"签到奖励！";
				conf.set(String.valueOf(i), cmdsString[i-1]);
			}
			conf.save();
		}
		
	}
	
}
