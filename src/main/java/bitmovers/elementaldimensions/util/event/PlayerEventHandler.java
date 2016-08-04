package bitmovers.elementaldimensions.util.event;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.network.PacketPlayerConnect;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * Created by Elec332 on 4-8-2016.
 *
 * Used for handling player events
 */
public class PlayerEventHandler {

    @SubscribeEvent
    public void onPlayerConnected(PlayerEvent.PlayerLoggedInEvent event){
        if (event.player instanceof EntityPlayerMP){
            ElementalDimensions.logger.info("Player "+event.player.getDisplayNameString()+" connected, sending connection packet.");
            ElementalDimensions.networkHandler.getNetworkWrapper().sendTo(new PacketPlayerConnect(), (EntityPlayerMP) event.player);
        }
    }

}
