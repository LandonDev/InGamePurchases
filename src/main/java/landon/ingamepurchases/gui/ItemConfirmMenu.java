package landon.ingamepurchases.gui;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import landon.ingamepurchases.InGamePurchases;
import landon.ingamepurchases.playerdata.PlayerDataManager;
import landon.ingamepurchases.struct.ShopItem;
import landon.ingamepurchases.struct.ShopManager;
import landon.lootgeneratorapi.util.GiveUtil;
import landon.lootgeneratorapi.util.ItemBuilder;
import landon.lootgeneratorapi.util.c;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ItemConfirmMenu implements InventoryProvider {
    public static SmartInventory build() {
        return SmartInventory.builder()
                .provider(new ItemConfirmMenu())
                .title("Confirm Purchase")
                .id("confirmItem")
                .size(1, 9)
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        ShopItem shopItem = ShopManager.get().getConfirmItemCache().get(player.getUniqueId());
        contents.set(0, 0, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&a&lConfirm Purchase", 1, 5, "&7Click to CONFIRM your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r", "", "&c&l&nWARNING:&c This purchase will subtract", "&c&l" + shopItem.getPrice() + "&c coins from your /coins balance!", "&cCoins are &nun-refundable&c unless technical", "&cissues arise. For any problem, contact", "&cour staff using /ticket create."), e -> {
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            playProcessingAnimation(player, contents, shopItem);
        }));
        contents.set(0, 1, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&a&lConfirm Purchase", 1, 5, "&7Click to CONFIRM your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r", "", "&c&l&nWARNING:&c This purchase will subtract", "&c&l" + shopItem.getPrice() + "&c coins from your /coins balance!", "&cCoins are &nun-refundable&c unless technical", "&cissues arise. For any problem, contact", "&cour staff using /ticket create."), e -> {
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            playProcessingAnimation(player, contents, shopItem);
        }));
        contents.set(0, 2, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&a&lConfirm Purchase", 1, 5, "&7Click to CONFIRM your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r", "", "&c&l&nWARNING:&c This purchase will subtract", "&c&l" + shopItem.getPrice() + "&c coins from your /coins balance!", "&cCoins are &nun-refundable&c unless technical", "&cissues arise. For any problem, contact", "&cour staff using /ticket create."), e -> {
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            playProcessingAnimation(player, contents, shopItem);
        }));
        contents.set(0, 3, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&a&lConfirm Purchase", 1, 5, "&7Click to CONFIRM your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r", "", "&c&l&nWARNING:&c This purchase will subtract", "&c&l" + shopItem.getPrice() + "&c coins from your /coins balance!", "&cCoins are &nun-refundable&c unless technical", "&cissues arise. For any problem, contact", "&cour staff using /ticket create."), e -> {
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
            playProcessingAnimation(player, contents, shopItem);
        }));
        ItemStack viewItem = shopItem.getItem().clone();
        ItemMeta meta = viewItem.getItemMeta();
        List<String> lore = new ArrayList<>(meta.getLore());
        lore.add(c.c(""));
        lore.add(c.c("&6&lPrice: &f&l" + shopItem.getPrice() + "&f coins"));
        lore.add(c.c("&8Shop ID: " + shopItem.getId()));
        meta.setLore(lore);
        viewItem.setItemMeta(meta);
        contents.set(0, 4, ClickableItem.empty(viewItem));
        contents.set(0, 5, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&c&lDeny Purchase", 1, 14, "&7Click to DENY your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r"), e -> {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            ShopInventory.build().open(player);
        }));
        contents.set(0, 6, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&c&lDeny Purchase", 1, 14, "&7Click to DENY your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r"), e -> {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            ShopInventory.build().open(player);
        }));
        contents.set(0, 7, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&c&lDeny Purchase", 1, 14, "&7Click to DENY your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r"), e -> {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            ShopInventory.build().open(player);
        }));
        contents.set(0, 8, ClickableItem.of(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&c&lDeny Purchase", 1, 14, "&7Click to DENY your purchase of:", shopItem.getItem().getItemMeta().getDisplayName() + "&r"), e -> {
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            ShopInventory.build().open(player);
        }));
    }

    public void playProcessingAnimation(Player player, InventoryContents contents, ShopItem item) {
        contents.fill(ClickableItem.empty(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&0", 1, 15)));
        new BukkitRunnable() {
            int toStart = 0;
            @Override
            public void run() {
                if(toStart == 9) {
                    PlayerDataManager.get().hasCoins(player.getUniqueId(), item.getPrice(), has -> {
                        playResult(player, contents, has, item);
                    });
                    cancel();
                }
                contents.set(0, toStart, ClickableItem.empty(ItemBuilder.createItem(Material.STAINED_GLASS, "&aProcessing...", 1, 5)));
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
                toStart++;
            }
        }.runTaskTimer(InGamePurchases.get(), 0L, 5L);
    }

    public void playResult(Player player, InventoryContents contents, boolean success, ShopItem item) {
        if(success) {
            player.closeInventory();
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            PlayerDataManager.get().subtractCoins(player.getUniqueId(), item.getPrice());
            if(ShopInventory.isInvFull(player)) {
                GiveUtil.giveOrDropItem(player, item.getItem().clone());
            } else {
                player.getInventory().addItem(item.getItem().clone());
            }
            Bukkit.broadcastMessage(c.c(""));
            Bukkit.broadcastMessage(c.c("&6&l(!) &6&n" + player.getName() + "&6 has purchased " + item.getItem().getItemMeta().getDisplayName() + "&r &6for &l&n" + item.getPrice() + "&6 coins!"));
            Bukkit.broadcastMessage(c.c("&7To support our server, type /buy. To use coins use /coins shop."));
            Bukkit.broadcastMessage(c.c(""));
        } else {
            contents.fill(ClickableItem.empty(ItemBuilder.createItem(Material.STAINED_GLASS_PANE, "&c&lFAILED:&r &cInsufficient Funds!", 1, 14)));
            player.sendMessage(c.c("&c&l(!) &cYou do not have enough coins to make this purchase!"));
            player.sendMessage(c.c("&7To check your balance or find out more information, type /coins."));
            player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            Bukkit.getScheduler().runTaskLater(InGamePurchases.get(), () -> {
                if(player.getOpenInventory().getTitle().contains("Confirm Purchase")) {
                    ShopInventory.build().open(player);
                }
            }, 100L);
        }
    }
}
