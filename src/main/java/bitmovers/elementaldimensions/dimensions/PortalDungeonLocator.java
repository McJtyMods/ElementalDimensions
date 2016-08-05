package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.Random;

public class PortalDungeonLocator {

    public static boolean isPortalChunk(int chunkX, int chunkZ) {
        WorldServer world = DimensionManager.getWorld(0);
        if (world == null){
            throw new IllegalStateException();
        }
        Random random = new Random((world.getSeed() + chunkX) * 37 + chunkZ * 5 + 113);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.portalDungeonChance;
    }

}
