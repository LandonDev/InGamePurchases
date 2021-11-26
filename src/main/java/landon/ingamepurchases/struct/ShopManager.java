package landon.ingamepurchases.struct;

public class ShopManager {
    private static volatile ShopManager inst;

    private ShopManager() {}

    public static ShopManager get() {
        if(inst == null) {
            synchronized (ShopManager.class) {
                inst = new ShopManager();
            }
        }
        return inst;
    }
}
