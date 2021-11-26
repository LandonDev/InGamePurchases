package landon.ingamepurchases.playerdata;

import com.mongodb.client.model.Filters;
import landon.ingamepurchases.InGamePurchases;
import landon.ingamepurchases.struct.ShopItem;
import landon.ingamepurchases.struct.ShopManager;
import landon.lootgeneratorapi.util.c;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class PlayerDataManager {
    private static volatile PlayerDataManager inst;

    private PlayerDataManager() {
    }

    public static PlayerDataManager get() {
        if(inst == null) {
            synchronized (PlayerDataManager.class) {
                inst = new PlayerDataManager();
            }
        }
        return inst;
    }

    public void getCoins(UUID uuid, Consumer<Integer> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            for (Document doc : InGamePurchases.get().getMongo().getPlayerData().find(Filters.eq("uuid", uuid.toString()))) {
                callback.accept(doc.getInteger("coins"));
                return;
            }
            callback.accept(0);
            this.saveUserData(uuid, 0);
        });
    }

    public void hasCoins(UUID uuid, int amount, Consumer<Boolean> callback) {
        this.getCoins(uuid, coins -> {
            if(coins >= amount) {
                callback.accept(true);
            } else {
                callback.accept(false);
            }
        });
    }

    public void addCoins(UUID uuid, int amount) {
        if(Bukkit.getPlayer(uuid) != null) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(c.c(""));
            player.sendMessage(c.c("&a&l+ " + amount + " coins"));
            player.sendMessage(c.c("&7You have received &n" + amount + "&7 coins. View your balance using /coins. Purchase coins using /coins buy."));
            player.sendMessage(c.c(""));
            player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1.0F, 1.0F);
        }
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            this.getCoins(uuid, coins -> {
                int newCoins = coins + amount;
                this.saveUserData(uuid, newCoins);
            });
        });
    }

    public void subtractCoins(UUID uuid, int amount) {
        if(Bukkit.getPlayer(uuid) != null) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendMessage(c.c(""));
            player.sendMessage(c.c("&c&l- " + amount + " coins"));
            player.sendMessage(c.c("&7You have lost &n" + amount + "&7 coins. View your balance using /coins. Purchase coins using /coins buy."));
            player.sendMessage(c.c(""));
            player.playSound(player.getLocation(), Sound.BAT_TAKEOFF, 1.0F, 1.0F);
        }
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            this.getCoins(uuid, coins -> {
                int newCoins = coins - amount;
                this.saveUserData(uuid, newCoins);
            });
        });
    }

    public void saveUserData(UUID uuid, int coins) {
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            Document doc = new Document();
            doc.append("uuid", uuid.toString());
            doc.append("coins", coins);
            if(Bukkit.getPlayer(uuid) != null) {
                doc.append("username", Bukkit.getPlayer(uuid).getName());
            }
            if(InGamePurchases.get().getMongo().getPlayerData().findOneAndReplace(Filters.eq("uuid", uuid.toString()), doc) == null) {
                InGamePurchases.get().getMongo().getPlayerData().insertOne(doc);
            }
        });
    }

    public void updateUsername(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            this.getCoins(player.getUniqueId(), coins -> {
                this.saveUserData(player.getUniqueId(), coins);
            });
        });
    }
}
