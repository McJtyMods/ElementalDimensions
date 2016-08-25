package bitmovers.elementaldimensions.util.worldgen;

import elec332.core.world.schematic.Schematic;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
}
