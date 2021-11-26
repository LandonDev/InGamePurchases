package landon.ingamepurchases.util;

import net.minecraft.server.v1_7_R4.MojangsonParser;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class ItemParser {
    public static ItemStack parseItem(Material material, int amount, String jsonString) {
        ItemStack stack;
        stack = new ItemStack(material, amount);
        net.minecraft.server.v1_7_R4.ItemStack nmsStack = null;
        try {
            NBTTagCompound nbt = (NBTTagCompound) MojangsonParser.parse(jsonString);
            nmsStack = CraftItemStack.asNMSCopy(stack);
            nmsStack.setTag(nbt);
        } catch (Exception exception) {
            Bukkit.getLogger().log(Level.WARNING, "Unable to parse NBT data '" + jsonString + "'");
        }
        return (nmsStack != null) ? CraftItemStack.asBukkitCopy(nmsStack) : stack;
    }

    public static String getNBT(ItemStack itemStack) {
        net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = nmsStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            String nbt = tag.toString();
            return nbt;
        }
        return null;
    }
}
