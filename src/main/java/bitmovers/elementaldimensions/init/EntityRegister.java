package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.mobs.EntityDirtZombie;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityRegister {

    public static void preInit() {
        EntityRegistry.registerModEntity(EntityDirtZombie.class, "Dirt Zombie", 1, ElementalDimensions.instance, 64, 3, true, 0xff0000, 0x00ff00);
    }
}
