package bitmovers.elementaldimensions.varia;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Broadcaster {

    public static void broadcast(World worldObj, int x, int y, int z, ITextComponent component, float radius) {
        for (Object p : worldObj.playerEntities) {
            EntityPlayer player = (EntityPlayer) p;
            double sqdist = player.getDistanceSq(x + .5, y + .5, z + .5);
            if (sqdist < radius) {
                player.sendStatusMessage(component, false);
            }
        }
    }

    public static void broadcastDimension(World worldObj, ITextComponent component) {
        for (Object p : worldObj.playerEntities) {
            EntityPlayer player = (EntityPlayer) p;
            if (player.world.provider.getDimension() == worldObj.provider.getDimension()) {
                player.sendStatusMessage(component, false);
            }
        }
    }

    public static void message(EntityPlayer player, String message) {
        player.sendMessage(new TextComponentString(message));
    }
}
