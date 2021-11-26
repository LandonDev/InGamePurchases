package landon.ingamepurchases;

import landon.ingamepurchases.commands.CmdACoins;
import landon.ingamepurchases.commands.CmdCoins;
import landon.ingamepurchases.commands.CmdEditShop;
import landon.ingamepurchases.playerdata.PlayerDataListener;
import landon.ingamepurchases.struct.MongoDB;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class InGamePurchases extends JavaPlugin {
    private static InGamePurchases inst;
    private MongoDB mongo;

    @Override
    public void onEnable() {
        inst = this;
        saveDefaultConfig();
        if(this.getConfig().getBoolean("mongodb.use-uri")) {
            this.mongo = new MongoDB(this.getConfig().getString("mongodb.uri"));
        } else {
            this.mongo = new MongoDB(this.getConfig().getString("mongodb.login-details.host"), this.getConfig().getInt("mongodb.login-details.port"), this.getConfig().getString("mongodb.login-details.username"), this.getConfig().getString("mongodb.login-details.password"));
        }
        this.getServer().getPluginManager().registerEvents(new PlayerDataListener(), this);
        this.getCommand("editshop").setExecutor(new CmdEditShop());
        this.getCommand("coins").setExecutor(new CmdCoins());
        this.getCommand("acoins").setExecutor(new CmdACoins());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static InGamePurchases get() {
        return inst;
    }
}
