package bitmovers.elementaldimensions.mobs;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void preInit() {
        EntityRegistry.registerModEntity(EntityDirtZombie.class, "Dirt Zombie", 1, ElementalDimensions.instance, 64, 3, true, 0xff0000, 0x00ff00);
            }
}
