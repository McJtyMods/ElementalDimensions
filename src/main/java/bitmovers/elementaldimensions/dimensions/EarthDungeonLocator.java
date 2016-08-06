package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.blocks.portal.PortalDialerTileEntity;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.SafelyCachedObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class EarthDungeonLocator {

    public static boolean isEarthDungeonChunk(World world, int chunkX, int chunkZ) {
        Random random = new Random((world.getSeed() + chunkX) * 37 + chunkZ * 5 + 113);
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.earthDungeonChance;
    }
}
