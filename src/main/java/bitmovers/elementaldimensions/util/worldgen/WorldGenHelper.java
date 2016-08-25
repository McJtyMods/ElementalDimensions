package bitmovers.elementaldimensions.util.worldgen;

import elec332.core.world.schematic.Schematic;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    public static int findBestStructureY(World world, Schematic schematic, BlockPos pos, Block matching) {
        int toty = 0;
        int cnt = 0;
        int y = findBestY(world, pos, matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(schematic.width, 0, 0), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(schematic.width, 0, schematic.length), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(0, 0, schematic.length), matching);
        if (y != -1) {
            toty += y;
            cnt++;
        }
        y = findBestY(world, pos.add(schematic.width/2, 0, schematic.length/2), matching);
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
        for (int x = pos.getX() ; x <= pos.getX() + schematic.width ; x++) {
            for (int z = pos.getZ() ; z <= pos.getZ() + schematic.length ; z++) {
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
        for (int x = pos.getX() ; x <= pos.getX() + schematic.width ; x++) {
            for (int z = pos.getZ() ; z <= pos.getZ() + schematic.length ; z++) {
                for (int y = pos.getY() ; y <= pos.getY() + schematic.height ; y++) {
                    mpos.setPos(x, y, z);
                    TileEntity tileentity = world.getTileEntity(mpos);
                    if (tileentity instanceof TileEntityChest) {
                        ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, random.nextLong());
                    }

                }
            }
        }
    }
}
