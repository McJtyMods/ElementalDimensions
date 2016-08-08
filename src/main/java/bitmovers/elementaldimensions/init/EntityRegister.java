package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.items.EntityWaterBossSeed;
import bitmovers.elementaldimensions.mobs.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityRegister {

    public static void init() {
        int id = 1;
        EntityRegistry.registerModEntity(EntityDirtZombie.class, "DirtZombie", id++, ElementalDimensions.instance, 64, 3, true, 0x996600, 0x00ff00);
        EntityRegistry.registerModEntity(EntityGuard.class, "Guard", id++, ElementalDimensions.instance, 32, 3, true, 0x880088, 0x00ff00);
        EntityRegistry.registerModEntity(EntityWaterCreep.class, "WaterCreep", id++, ElementalDimensions.instance, 64, 3, true, 0x002288, 0x005533);
        EntityRegistry.registerModEntity(EntityDirtZombieBoss.class, "DirtZombieBoss", id++, ElementalDimensions.instance, 64, 3, true, 0x996600, 0x00ff00);
        EntityRegistry.registerModEntity(EntityWaterCreepBoss.class, "WaterCreepBoss", id++, ElementalDimensions.instance, 64, 3, true, 0x002288, 0x005533);
        EntityRegistry.registerModEntity(EntityWaterBossSeed.class, "WaterBossSeed", id++, ElementalDimensions.instance, 64, 3, true);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWaterCreep.class, EntityLiving.SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWaterCreepBoss.class, EntityLiving.SpawnPlacementType.IN_WATER);

        LootTableList.register(EntityDirtZombie.LOOT);
        LootTableList.register(EntityDirtZombieBoss.LOOT);
        LootTableList.register(EntityWaterCreep.LOOT);
        LootTableList.register(EntityWaterCreepBoss.LOOT);
        LootTableList.register(EntityGuard.LOOT);

    }

}
