package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.World;

import java.util.Random;

public class PortalDungeonLocator {

    public static boolean isPortalChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 37 + chunkZ * 5 + 113);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.portalDungeonChance;
    }

}
