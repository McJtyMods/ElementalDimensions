package bitmovers.elementaldimensions.mobs;

import bitmovers.elementaldimensions.util.Config;
import elec332.core.world.WorldHelper;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnEventHandler {

    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        //System.out.println("event.getEntity() = " + event.getEntity());
        if (event.getEntity() instanceof EntityDirtZombie) {
            if (WorldHelper.getDimID(event.getWorld()) != Config.Dimensions.Earth.dimensionID) {
                System.out.println("Spawn denied!");
                event.setResult(Event.Result.DENY);
            }
        }
    }

}
