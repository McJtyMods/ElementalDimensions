package bitmovers.elementaldimensions.util.worldgen;

import elec332.core.api.structure.ISchematic;
import elec332.core.world.schematic.Schematic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.storage.loot.LootTableList;

import java.util.Random;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class WorldGenHelper {

    public static BlockPos randomXZPos(int chunkX, int chunkZ, int y, Random random){
        return new BlockPos(randomXZ(chunkX, random), y, randomXZ(chunkZ, random));
    }

    public static int randomXZ(int chunkPos, Random random){
        return chunkPos * 16 + random.nextInt(16);
    }

    public static int findBestStructureY(World world, ISchematic schematic, BlockPos pos, Block matching) {
        int toty = 0;
        int cnt = 0;
        int y = findBestY(world, pos, matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(schematic.getBlockWidth(), 0, 0), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(schematic.getBlockWidth(), 0, schematic.getBlockLength()), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(0, 0, schematic.getBlockLength()), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(schematic.getBlockWidth()/2, 0, schematic.getBlockLength()/2), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }

        if (cnt == 0) {
            return -1;
        }
        return toty / cnt;
    }

    public static int findBestY(World world, BlockPos pos, Block matching) {
        BlockPos npos = world.getTopSolidOrLiquidBlock(pos);
        if (matching != null && world.getBlockState(npos.down()).getBlock() != matching) {
            return -1;
        }
        return npos.getY();
    }

    // Fill the area below a structure with some blocks
    public static void fillWithBlock(World world, Schematic schematic, BlockPos pos, int averagey, IBlockState state) {
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
        for (int x = pos.getX() ; x <= pos.getX() + schematic.getBlockWidth() ; x++) {
            for (int z = pos.getZ() ; z <= pos.getZ() + schematic.getBlockLength() ; z++) {
                for (int y = averagey-1 ; y > 0 ; y--) {
                    mpos.setPos(x, y, z);
                    if (world.isAirBlock(mpos)) {
                        world.setBlockState(mpos, state);
                    }
                }
            }
        }
    }

    // Fix chests in the structure so they have loot
    public static void fixLootChests(World world, Schematic schematic, BlockPos pos) {
        Random random = new Random(world.getSeed());
        random.nextLong();
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
        for (int x = pos.getX() ; x <= pos.getX() + schematic.getBlockWidth() ; x++) {
            for (int z = pos.getZ() ; z <= pos.getZ() + schematic.getBlockLength() ; z++) {
                for (int y = pos.getY() ; y <= pos.getY() + schematic.getBlockHeight() ; y++) {
                    mpos.setPos(x, y, z);
                    TileEntity tileentity = world.getTileEntity(mpos);
                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, random.nextLong());
                    }
                }
            }
        }
    }

    // Fix spawners in the structure so they are a specific type
    public static void fixSpawners(World world, Schematic schematic, BlockPos pos, ResourceLocation mobId) {
        Random random = new Random(world.getSeed());
        random.nextLong();
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos();
        for (int x = pos.getX() ; x <= pos.getX() + schematic.getBlockWidth() ; x++) {
            for (int z = pos.getZ() ; z <= pos.getZ() + schematic.getBlockLength() ; z++) {
                for (int y = pos.getY() ; y <= pos.getY() + schematic.getBlockHeight() ; y++) {
                    mpos.setPos(x, y, z);
                    TileEntity tileentity = world.getTileEntity(mpos);
                    if (tileentity instanceof TileEntityMobSpawner) {
                        TileEntityMobSpawner spawner = (TileEntityMobSpawner) tileentity;
                        MobSpawnerBaseLogic mobspawnerbaselogic = spawner.getSpawnerBaseLogic();
                        mobspawnerbaselogic.setEntityId(mobId);
                        spawner.markDirty();
                    }
                }
            }
        }
    }

    public static boolean areWeOutside(World world, BlockPos pos) {
        BlockPos top = world.getTopSolidOrLiquidBlock(pos);
        boolean b = top.getY() > pos.getY();
        if (!b) {
            return true;
        }

        // Check negative x for a wall
        BlockPos.MutableBlockPos mpos = new BlockPos.MutableBlockPos(pos);
        for (int x = 1 ; x < 20 ; x++) {
            mpos.setPos(pos.getX()-x, pos.getY(), pos.getZ());
            boolean air1 = world.isAirBlock(mpos);
            boolean air2 = world.isAirBlock(mpos.up());
            if ((!air1) && (!air2)) {
                return false;
            }
        }

        return true;
    }

    public static void addOreSpawn(IBlockState block, IBlockState targetBlock,
                            World world, Random random, int blockXPos, int blockZPos, int minVeinSize, int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        WorldGenMinable minable = new WorldGenMinable(block, (minVeinSize + random.nextInt(maxVeinSize - minVeinSize)), state -> state.getBlock() == targetBlock.getBlock());
        for (int i = 0 ; i < chancesToSpawn ; i++) {
            int posX = blockXPos + random.nextInt(16);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(16);
            minable.generate(world, random, new BlockPos(posX, posY, posZ));
        }
    }

}
