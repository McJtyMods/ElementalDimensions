package bitmovers.elementaldimensions.util.worldgen;

import net.minecraft.util.math.BlockPos;

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

}
