package landon.ingamepurchases.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import landon.ingamepurchases.InGamePurchases;
import landon.ingamepurchases.struct.ShopItem;
import landon.ingamepurchases.struct.ShopManager;
import landon.lootgeneratorapi.LootGeneratorAPI;
import landon.lootgeneratorapi.util.GiveUtil;
import landon.lootgeneratorapi.util.ItemBuilder;
import landon.lootgeneratorapi.util.c;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ShopInventory implements InventoryProvider {
    public static SmartInventory build() {
        return SmartInventory.builder()
                .provider(new ShopInventory())
                .title("In-Game Shop")
                .id("inGameShop")
                .size(6, 9)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
        contents.fill(ClickableItem.empty(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&cLoading...", 1, 14, "")));
        ShopManager.get().loadShopItems(items -> {
            Pagination pagination = contents.pagination();
            ClickableItem[] clickableItems = new ClickableItem[items.size()];
            for(int i = 0; i < clickableItems.length; i++) {
                ShopItem shopItem = items.get(i);
                ItemStack viewItem = shopItem.getItem().clone();
                ItemMeta meta = viewItem.getItemMeta();
                List<String> lore = new ArrayList<>(meta.getLore());
                lore.add(c.c(""));
                lore.add(c.c("&6&lPrice: &f&l" + shopItem.getPrice() + "&f coins"));
                lore.add(c.c("&7Left-Click to purchase this item."));
                lore.add(c.c("&8Shop ID: " + shopItem.getId()));
                meta.setLore(lore);
                viewItem.setItemMeta(meta);
                clickableItems[i] = ClickableItem.of(viewItem, e -> {
                    if(isInvFull(player)) {
                        player.sendMessage(c.c("&c&l(!) &cYour inventory is full and thus cannot make any purchases!"));
                        player.closeInventory();
                        return;
                    }
                    player.closeInventory();
                    ShopManager.get().getConfirmItemCache().put(player.getUniqueId(), shopItem);
                    ItemConfirmMenu.build().open(player);
                    player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                });
            }
            pagination.setItems(clickableItems);
            pagination.setItemsPerPage(45);
            pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));
            contents.fillRow(5, ClickableItem.empty(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&0", 1, 15)));
            if(!pagination.isFirst()) {
                contents.set(5, 7, ClickableItem.of(ItemBuilder.createItem(Material.ARROW, "&e&lBack", "&7Click to go back."), e -> {
                    build().open(player, pagination.previous().getPage());
                    player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
                }));
            }
            if(!pagination.isLast()) {
                contents.set(5, 8, ClickableItem.of(ItemBuilder.createItem(Material.ARROW, "&e&lNext Page", "&7Click to go forward."), e -> {
                    build().open(player, pagination.next().getPage());
                    player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1.0F, 1.0F);
                }));
            }
        });
    }

    public static boolean isInvFull(Player player) {
        int amountOfItems = 0;
        for(int i = 0; i < 36; i++) {
            if(player.getInventory().getItem(i) != null) {
                amountOfItems++;
            }
        }
        if(amountOfItems >= 36) {
            return true;
        }
        return false;
    }
}
