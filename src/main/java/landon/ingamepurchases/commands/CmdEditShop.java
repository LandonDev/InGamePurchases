package landon.ingamepurchases.commands;

import landon.ingamepurchases.struct.ShopManager;
import landon.lootgeneratorapi.util.c;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdEditShop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            Player player = (Player)sender;
            if(args.length > 2 && args[0].equalsIgnoreCase("additem") && NumberUtils.isNumber(args[1])) {
                if(player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR) {
                    ItemStack item = player.getItemInHand().clone();
                    ShopManager.get().createShopItem(item, Integer.parseInt(args[1]));
                    player.sendMessage(c.c("&aSuccessfully added your item to the ItemShop!"));
                } else {
                    player.sendMessage(c.c("&cHold an item in your hand!"));
                }
                return false;
            }
            if(args.length > 2 && args[0].equalsIgnoreCase("removeitem") && NumberUtils.isNumber(args[1])) {
                int idToFind = Integer.parseInt(args[1]);
                ShopManager.get().getShopItemFromID(idToFind, item -> {
                   if(item != null) {
                       ShopManager.get().deleteShopItem(item);
                       player.sendMessage(c.c("&aSuccessfully deleted the item out of the ItemShop!"));
                       return;
                   }
                   player.sendMessage(c.c("&cNo item was found for the shop id '&7" + idToFind + "&c'"));
                });
                return false;
            }
            player.sendMessage(c.c("&c/editshop additem <price>"));
            player.sendMessage(c.c("&c/editshop removeitem <shop id>"));
        }
        return false;
    }
}
