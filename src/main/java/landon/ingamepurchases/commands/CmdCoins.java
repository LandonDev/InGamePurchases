package landon.ingamepurchases.commands;

import landon.ingamepurchases.gui.ShopInventory;
import landon.ingamepurchases.playerdata.PlayerDataManager;
import landon.lootgeneratorapi.util.c;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdCoins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;
            if(args.length > 0 && args[0].equalsIgnoreCase("pay")) {
                if(args.length > 2 && Bukkit.getPlayer(args[1]) != null && NumberUtils.isNumber(args[2])) {
                    Player toGive = Bukkit.getPlayer(args[1]);
                    int amount = Integer.parseInt(args[2]);
                }
                player.sendMessage(c.c("&c/coins pay <player> <amount>"));
                return false;
            }
            if(args.length > 0 && args[0].equalsIgnoreCase("shop")) {
                ShopInventory.build().open(player);
                return false;
            }
            PlayerDataManager.get().getCoins(player.getUniqueId(), coins -> {
                player.sendMessage(c.c("&eCoin Balance: &f&l&n" + coins + "&f coins"));
                player.sendMessage(c.c("&7Coins are a virtual currency obtained through /buy. You can pay others using /coins pay."));
                player.sendMessage(c.c("&7You can use your coins using /coins shop."));
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            });
        }
        return false;
    }
}
