package landon.ingamepurchases.struct;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class ShopItem {
    private ItemStack item;
    private int price;
    private double salePercent;
    private long putOnStore;
    private int id;

    public ShopItem(ItemStack item, int price) {
        this.item = item;
        this.price = price;
        this.salePercent = 0.0D;
        this.putOnStore = System.currentTimeMillis();
        ShopManager.get().assignRandomID(id -> {
            this.id = id;
        });
    }

    public ShopItem(ItemStack item, int price, double salePercent, long putOnStore, int id) {
        this.item = item;
        this.price = price;
        this.salePercent = salePercent;
        this.putOnStore = putOnStore;
        this.id = id;
    }
}
