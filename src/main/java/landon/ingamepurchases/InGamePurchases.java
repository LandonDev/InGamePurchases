package landon.ingamepurchases;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class InGamePurchases extends JavaPlugin {
    private static InGamePurchases inst;

    @Override
    public void onEnable() {
        inst = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static InGamePurchases get() {
        return inst;
    }
}
