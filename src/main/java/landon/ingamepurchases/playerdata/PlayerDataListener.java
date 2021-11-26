package landon.ingamepurchases.playerdata;

import com.mysql.jdbc.StringUtils;
import landon.ingamepurchases.gui.ShopInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerDataListener implements Listener {
    @EventHandler
    public void updatePlayerUsernameEvent(PlayerLoginEvent e) {
        PlayerDataManager.get().updateUsername(e.getPlayer());
    }
}
