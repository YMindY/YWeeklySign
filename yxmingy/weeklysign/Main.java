package yxmingy.weeklysign;
import java.util.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class Main extends PluginBase{
	private Config conf;
	private static Config record;
	public void onEnable() {
		conf = new Config(getDataFolder()+"/Config.yml",Config.YAML);
		record = new Config(getDataFolder()+"/Rec.yml",Config.YAML);
		if(conf.getAll().isEmpty()) {
			String[][] cmdsString = new String[7][1];
			int i = 1;
			for(;i<=7;i++) {
				cmdsString[i-1][0] = "say @p 已领取周"+String.valueOf(i)+"签到奖励！";
				conf.set(String.valueOf(i), cmdsString[i-1]);
			}
			conf.save();
		}
		getLogger().notice("YWeeklySign in Enabled! Author: xMing.");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!command.getName().equals("sign")) return true;
		
		if(args.length < 1) return false;
		if(!conf.exists(args[0])) return false;
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		int w = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(w == 0) w = 7;
		if(!args[0].contentEquals(String.valueOf(w))) {
			sender.sendMessage("今天是星期"+String.valueOf(w));
			return true;
		}
		String date = DateFormat.getDateInstance().format(now);
		if(record.exists(sender.getName()) && record.getString(sender.getName()).contentEquals(date)) {
			sender.sendMessage("你今天已经签过到了！");
			return true;
		}
		@SuppressWarnings("unchecked")
		ArrayList<String> cmds = (ArrayList<String>)conf.get(args[0]);
		for (int i = 0; i < cmds.size(); i++) {
			getServer().dispatchCommand(new ConsoleCommandSender(), cmds.get(i).replaceAll("@p", sender.getName()));
		}
		record.set(sender.getName(), date);
		record.save();
		return true;
	}
}
