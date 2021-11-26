package landon.ingamepurchases.commands;

import landon.ingamepurchases.playerdata.PlayerDataManager;
import landon.ingamepurchases.struct.ShopManager;
import landon.lootgeneratorapi.util.c;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class CmdACoins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender.isOp()) {
            if(args.length > 2 && Bukkit.getPlayer(args[1]) != null && args[0].equalsIgnoreCase("add") && NumberUtils.isNumber(args[2])) {
                PlayerDataManager.get().addCoins(Bukkit.getPlayer(args[1]).getUniqueId(), Integer.parseInt(args[2]));
                sender.sendMessage(c.c("&aYou have added coins to that player!"));
                return false;
            }
            if(args.length > 2 && Bukkit.getPlayer(args[1]) != null && args[0].equalsIgnoreCase("remove") && NumberUtils.isNumber(args[2])) {
                PlayerDataManager.get().subtractCoins(Bukkit.getPlayer(args[1]).getUniqueId(), Integer.parseInt(args[2]));
                sender.sendMessage(c.c("&aYou have removed coins from that player!"));
                return false;
            }
            if(args.length > 1 && Bukkit.getPlayer(args[1]) != null && args[0].equalsIgnoreCase("see")) {
                PlayerDataManager.get().getCoins(Bukkit.getPlayer(args[1]).getUniqueId(), coins -> {
                    sender.sendMessage(c.c("&a" + Bukkit.getPlayer(args[1]).getName() + "&a has: &f" + coins + " &fcoins."));
                });
                return false;
            }
            sender.sendMessage(c.c("&c/acoins add <player> <amount>"));
            sender.sendMessage(c.c("/acoins remove <player> <amount>"));
            sender.sendMessage(c.c("/acoins see <player>"));
        }
        return false;
    }
}
