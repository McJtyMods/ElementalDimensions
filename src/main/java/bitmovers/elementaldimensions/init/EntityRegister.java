package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.items.EntityAirBossSeed;
import bitmovers.elementaldimensions.items.EntityFireBossSeed;
import bitmovers.elementaldimensions.items.EntityWaterBossSeed;
import bitmovers.elementaldimensions.mobs.*;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EntityRegister {

    public static void init() {
        int id = 1;
        registerModEntity(EntityDirtZombie.class, "ed_dirtzombie", id++, ElementalDimensions.instance, 64, 3, true, 0x996600, 0x00ff00);
        registerModEntity(EntityGuard.class, "ed_guard", id++, ElementalDimensions.instance, 32, 3, true, 0x880088, 0x00ff00);
        registerModEntity(EntityWaterCreep.class, "ed_watercreep", id++, ElementalDimensions.instance, 64, 3, true, 0x002288, 0x775533);
        registerModEntity(EntityDirtZombieBoss.class, "ed_dirtzombieboss", id++, ElementalDimensions.instance, 64, 3, true, 0x996600, 0x00ff00);
        registerModEntity(EntityWaterCreepBoss.class, "ed_watercreepboss", id++, ElementalDimensions.instance, 64, 3, true, 0x002288, 0x775533);
        registerModEntity(EntityGhost.class, "ed_ghost", id++, ElementalDimensions.instance, 64, 3, true, 0x998800, 0x995533);
        registerModEntity(EntityGhostBoss.class, "ed_ghostboss", id++, ElementalDimensions.instance, 64, 3, true, 0x998800, 0x995533);
        registerModEntity(EntitySpirit.class, "ed_spirit", id++, ElementalDimensions.instance, 64, 3, true, 0x996655, 0x00ff55);
        registerModEntity(EntityBlaster.class, "ed_blaster", id++, ElementalDimensions.instance, 64, 3, true, 0xff8833, 0xee3300);
        registerModEntity(EntityFireBoss.class, "ed_fireboss", id++, ElementalDimensions.instance, 64, 3, true, 0xff8833, 0xee3300);

        id = 100;
        registerModEntity(EntityWaterBossSeed.class, "ed_waterbossseed", id++, ElementalDimensions.instance, 64, 3, true);
        registerModEntity(EntityAirBossSeed.class, "ed_airbossseed", id++, ElementalDimensions.instance, 64, 3, true);
        registerModEntity(EntityFireBossSeed.class, "ed_firebossseed", id++, ElementalDimensions.instance, 64, 3, true);

        EntitySpawnPlacementRegistry.setPlacementType(EntityWaterCreep.class, EntityLiving.SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityWaterCreepBoss.class, EntityLiving.SpawnPlacementType.IN_WATER);
        EntitySpawnPlacementRegistry.setPlacementType(EntityGhost.class, EntityLiving.SpawnPlacementType.IN_AIR);

        LootTableList.register(EntitySpirit.LOOT);
        LootTableList.register(EntityDirtZombie.LOOT);
        LootTableList.register(EntityDirtZombieBoss.LOOT);
        LootTableList.register(EntityWaterCreep.LOOT);
        LootTableList.register(EntityWaterCreepBoss.LOOT);
        LootTableList.register(EntityGuard.LOOT);
        LootTableList.register(EntityBlaster.LOOT);

    }

    private static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int eggPrimary, int eggSecondary){
        EntityRegistry.registerModEntity(new EDResourceLocation(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates, eggPrimary, eggSecondary);
    }

    private static void registerModEntity(Class<? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates){
        EntityRegistry.registerModEntity(new EDResourceLocation(entityName), entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
    }


}
