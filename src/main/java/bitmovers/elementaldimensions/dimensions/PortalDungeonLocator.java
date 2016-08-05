package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.blocks.portal.PortalDialerTileEntity;
import bitmovers.elementaldimensions.util.Config;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class PortalDungeonLocator {

    public static boolean isPortalChunk(int chunkX, int chunkZ) {
        WorldServer world = DimensionManager.getWorld(0);
        if (world == null){
            throw new IllegalStateException();
        }
        long seed = world.getSeed();
        Random random = new Random(seed);//((seed + chunkX + 3) * 37 + chunkZ * 5 + 113) ^ seed - 5);
        long xSeed = random.nextLong() >> 2 + 1L;
        long zSeed = random.nextLong() >> 2 + 1L;
        long chunkSeed = (xSeed * chunkX + zSeed * chunkZ) ^ seed;
        random.setSeed(chunkSeed);
        random.nextInt(100);
        return random.nextFloat() < Config.Dimensions.portalDungeonChance;
    }

    @Nullable
    public static PortalDialerTileEntity getTeleporter(WorldServer currentWorld, BlockPos currentPos, Dimensions dimension){
        int dim = dimension.getDimensionID();
        MinecraftServer server = currentWorld.getMinecraftServer();
        if (server == null){
            return null;
        }
        WorldServer world = server.worldServerForDimension(dim);
        Chunk chunk = world.getChunkFromBlockCoords(currentPos);
        for (Map.Entry<BlockPos, TileEntity> entry : chunk.getTileEntityMap().entrySet()){
            BlockPos tilePos = entry.getKey();
            if (currentPos.getX() == tilePos.getX() && currentPos.getZ() == tilePos.getZ()){
                TileEntity tile = entry.getValue();
                if (tile instanceof PortalDialerTileEntity){
                    return (PortalDialerTileEntity) tile;
                }
            }
        }
        return null;
    }

}
