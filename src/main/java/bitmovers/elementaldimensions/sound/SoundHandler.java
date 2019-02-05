package bitmovers.elementaldimensions.sound;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import elec332.core.ElecCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SoundHandler {

    private static int ticks = 0;

    @SubscribeEvent
    public void onWorldTick(TickEvent.ClientTickEvent event) {
        World world = ElecCore.proxy.getClientWorld();
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
                EntityPlayer player = ElecCore.proxy.getClientPlayer();
                SoundController.playSoundScape(world, player, SoundController.HOWLINGWIND);
            }
        } else if (world.provider.getDimension() == Dimensions.SPIRIT.getDimensionID()) {
            // @todo improve this
            if (SoundController.isPlaying(SoundController.HOWLINGWIND)) {
                SoundController.stopSoundScape(SoundController.HOWLINGWIND);
            }
            if (!SoundController.isPlaying(SoundController.SPIRITS)) {
                System.out.println("Start playing SPIRITS");
                EntityPlayer player = ElecCore.proxy.getClientPlayer();
                SoundController.playSoundScape(world, player, SoundController.SPIRITS);
            }
        } else {
            SoundController.stopAllSoundScapes();
        }
    }
}
