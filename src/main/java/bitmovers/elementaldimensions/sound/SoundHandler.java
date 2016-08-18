package bitmovers.elementaldimensions.sound;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoundHandler {

    private static int ticks = 0;

    @SubscribeEvent
    public void onWorldTick(TickEvent.ClientTickEvent event) {
        WorldClient world = Minecraft.getMinecraft().theWorld;
        if (world == null) {
            return;
        }

        if (ticks > 0) {
            ticks--;
            return;
        }
        ticks = 40;

        if (world.provider.getDimension() == Dimensions.AIR.getDimensionID()) {
            // @todo improve this
            if (SoundController.isPlaying(SoundController.SPIRITS)) {
                SoundController.stopSoundScape(SoundController.SPIRITS);
            }
            if (!SoundController.isPlaying(SoundController.HOWLINGWIND)) {
                System.out.println("Start playing HOWLINGWIND");
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                SoundController.playSoundScape(world, player, SoundController.HOWLINGWIND);
            }
        } else if (world.provider.getDimension() == Dimensions.SPIRIT.getDimensionID()) {
            // @todo improve this
            if (SoundController.isPlaying(SoundController.HOWLINGWIND)) {
                SoundController.stopSoundScape(SoundController.HOWLINGWIND);
            }
            if (!SoundController.isPlaying(SoundController.SPIRITS)) {
                System.out.println("Start playing SPIRITS");
                EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
                SoundController.playSoundScape(world, player, SoundController.SPIRITS);
            }
        } else {
            SoundController.stopAllSoundScapes();
        }
    }
}
