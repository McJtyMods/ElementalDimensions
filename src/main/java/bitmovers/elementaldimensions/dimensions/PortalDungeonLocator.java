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

public class PortalDungeonLocator {

    private static final SafelyCachedObject<World> overworld;

    public static boolean isPortalChunk(int chunkX, int chunkZ) {
        // No portals near spawn
        if (Math.abs(chunkX) < 14 && Math.abs(chunkZ) < 14) {
            return false;
        }
        Random random = new Random(getSpecialSeed(chunkX, chunkZ));
        random.nextFloat();
        return random.nextFloat() < Config.Dimensions.portalDungeonChance;
    }

    public static long getSpecialSeed(int chunkX, int chunkZ) {
        return (overworld.get().getSeed() + chunkX) * 37 + chunkZ * 5 + 113;
    }

    @Nullable
    public static PortalDialerTileEntity getTeleporter(@Nonnull WorldServer currentWorld, @Nonnull BlockPos currentPos, @Nullable Dimensions dimension){
        int dim = dimension == null ? 0 : dimension.getDimensionID();
        MinecraftServer server = currentWorld.getMinecraftServer();
        if (server == null){
            return null;
        }
        WorldServer world = server.getWorld(dim);
        ChunkPos chunkPos = new ChunkPos(currentPos);
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                world.getChunkFromChunkCoords(chunkPos.x + i, chunkPos.z + j);
            }
        }
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

    static {
        overworld = new SafelyCachedObject<>(() -> DimensionManager.getWorld(0));
    }

}
