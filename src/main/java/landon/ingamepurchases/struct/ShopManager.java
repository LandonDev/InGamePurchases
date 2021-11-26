package landon.ingamepurchases.struct;

import com.mongodb.client.model.Filters;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import landon.ingamepurchases.InGamePurchases;
import landon.ingamepurchases.gui.ShopInventory;
import landon.ingamepurchases.playerdata.PlayerDataManager;
import landon.ingamepurchases.util.ItemParser;
import landon.lootgeneratorapi.util.GiveUtil;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

@Getter
public class ShopManager {
    private static volatile ShopManager inst;

    private HashMap<UUID, ShopItem> confirmItemCache = new HashMap<>();

    private ShopManager() {

    }

    public static ShopManager get() {
        if(inst == null) {
            synchronized (ShopManager.class) {
                inst = new ShopManager();
            }
        }
        return inst;
    }

    public void createShopItem(ItemStack item, int price) {
        ShopItem shopItem = new ShopItem(item, price);
        this.saveShopItem(shopItem);
    }

    public void getItemFromID(int id, Consumer<ItemStack> callback) {
        this.loadShopItems(items -> {
            for (ShopItem item : items) {
                if(item.getId() == id) {
                    callback.accept(item.getItem().clone());
                }
            }
        });
    }

    public void getShopItemFromID(int id, Consumer<ShopItem> callback) {
        this.loadShopItems(items -> {
            for (ShopItem item : items) {
                if(item.getId() == id) {
                    callback.accept(item);
                }
            }
        });
    }

    public void deleteShopItem(ShopItem item) {
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            InGamePurchases.get().getMongo().getShopData().findOneAndDelete(Filters.eq("id", item.getId()));
        });
    }

    public void assignRandomID(Consumer<Integer> callback) {
        this.loadShopItems(items -> {
            int id = 0;
            while(id == 0) {
                int potentialID = ThreadLocalRandom.current().nextInt(100000, 999999);
                boolean matches = false;
                for (ShopItem item : items) {
                    if(item.getId() == potentialID) {
                        matches = true;
                    }
                }
                if(matches) {
                    continue;
                } else {
                    id = potentialID;
                    callback.accept(id);
                }
            }
        });
    }

    public void loadShopItems(Consumer<List<ShopItem>> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            List<ShopItem> itemsTo = new ArrayList<>();
            for (Document doc : InGamePurchases.get().getMongo().getShopData().find()) {
                ItemStack item = ItemParser.parseItem(Material.valueOf(doc.getString("material")), doc.getInteger("amount"), doc.getString("nbt"));
                int price = doc.getInteger("price");
                int id = doc.getInteger("id");
                long putOnStore = doc.getLong("put-on-store");
                double sale = doc.getDouble("sale");
                ShopItem shopItem = new ShopItem(item, price, sale, putOnStore, id);
                itemsTo.add(shopItem);
            }
            callback.accept(itemsTo);
        });
    }

    public void saveShopItem(ShopItem item) {
        Bukkit.getScheduler().runTaskAsynchronously(InGamePurchases.get(), () -> {
            Document doc = new Document();
            doc.append("material", item.getItem().getType().toString());
            doc.append("amount", item.getItem().getAmount());
            doc.append("nbt", ItemParser.getNBT(item.getItem()));
            doc.append("price", item.getPrice());
            doc.append("id", item.getId());
            doc.append("put-on-store", item.getPutOnStore());
            doc.append("sale", item.getSalePercent());
            if(InGamePurchases.get().getMongo().getShopData().findOneAndReplace(Filters.eq("id", item.getId()), doc) == null) {
                InGamePurchases.get().getMongo().getShopData().insertOne(doc);
            }
        });
    }
}
