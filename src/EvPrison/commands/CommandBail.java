package EvPrison.commands;

import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import EvPrison.Prison;
import EvPrison.Utils;
import net.evmodder.EvLib.EvCommand;
import net.evmodder.EvLib.hooks.VaultHook;

public class CommandBail extends EvCommand{
	private double HOURLY_BAIL;
	
	public CommandBail(Prison p){
		super(p);
		HOURLY_BAIL = p.getConfig().getDouble("bail-cost-per-hour");
	}

	@Override @SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command command, String label, String args[]){
		//cmd:	/prison bail [-player] [-jail]
		
		OfflinePlayer target = sender instanceof Player ? (Player)sender : null;
		String jailName = null;
		
		if(args.length == 0 && target == null){
			sender.sendMessage("�cPlease specify the player for whom you wish to pay bail");
			return true;
		}
		else{
			OfflinePlayer target2 = Prison.getPlugin().getServer().getOfflinePlayer(args[0]);
			if(target2 == null || !target2.hasPlayedBefore()){
				if(target == null){
					sender.sendMessage("�cUnable to find player '�e"+args[0]+"�c'");
					return false;
				}
				else jailName = args[0];
			}
			else if(args.length > 1) jailName = args[1];
		}
		boolean self = target.getName().equals(sender.getName());
		
		if(!Prison.getPlugin().isPrisoner(target.getUniqueId())){
			if(self) sender.sendMessage("�7You are not currently in jail");
			else sender.sendMessage("�7That player is not currently in jail");
			return true;
		}
		long jailTime = Prison.getPlugin().getJailTimeLeft(target.getUniqueId());
		if(jailTime == 0){
			if(self) sender.sendMessage("�7You cannot bail out of your sentance");
			else sender.sendMessage("�7That player cannot be bailed");
			return true;
		}
		if(sender instanceof Player){
			double bailAmount = jailTime * HOURLY_BAIL;//TODO: lod custom hourly_bail per prison
			
			if(VaultHook.hasAtLeast((Player)sender, bailAmount) == false){
				sender.sendMessage("�4You do not have sufficient funds (�c$"+bailAmount+"�4)");
				return true;
			}
			else if(args[args.length-1].equals("confirm")){
				VaultHook.chargeFee((Player)sender, bailAmount);
				sender.sendMessage("�7You have paid a bail of �c$"+bailAmount+"�7 to release �6"+target.getName());
			}
			else{
				Utils.sendHyperTextCommand("�7Click to pay the �c"+bailAmount+"�7 bail �f[", "�eClick here",
						"/"+label+' '+target.getName()+(jailName == null ? "" : " "+jailName)+" confirm", "�f]", (Player)sender);
				return true;
			}
		}
		Prison.getPlugin().unjail(target.getUniqueId(), jailName);
		return true;
	}

	@Override public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3){
		// TODO Auto-generated method stub
		return null;
	}
}
