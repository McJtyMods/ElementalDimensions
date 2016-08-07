package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.mobs.EntityDirtZombie;
import bitmovers.elementaldimensions.mobs.EntityGuard;
import bitmovers.elementaldimensions.mobs.EntityWaterCreep;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityRegister {

    public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(EntityDirtZombie.class, "Dirt Zombie", id++, ElementalDimensions.instance, 64, 3, true, 0x996600, 0x00ff00);
        EntityRegistry.registerModEntity(EntityGuard.class, "Guard", id++, ElementalDimensions.instance, 32, 3, true, 0x880088, 0x00ff00);
        EntityRegistry.registerModEntity(EntityWaterCreep.class, "Water Creep", id++, ElementalDimensions.instance, 64, 3, true, 0x002288, 0x005533);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWaterCreep.class, EntityLiving.SpawnPlacementType.IN_WATER);

        LootTableList.register(EntityDirtZombie.LOOT);
        LootTableList.register(EntityWaterCreep.LOOT);
        LootTableList.register(EntityGuard.LOOT);

    }

}
