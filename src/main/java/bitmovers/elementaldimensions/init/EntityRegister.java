package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.mobs.EntityDirtZombie;
import bitmovers.elementaldimensions.mobs.EntityGuard;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityRegister {

    public static void init() {
        EntityRegistry.registerModEntity(EntityDirtZombie.class, "Dirt Zombie", 1, ElementalDimensions.instance, 64, 3, true, 0x996600, 0x00ff00);
        EntityRegistry.registerModEntity(EntityGuard.class, "Guard", 2, ElementalDimensions.instance, 32, 3, true, 0x880088, 0x00ff00);
    }

}
