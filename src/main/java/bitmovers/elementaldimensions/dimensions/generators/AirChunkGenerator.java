package bitmovers.elementaldimensions.dimensions.generators;

import bitmovers.elementaldimensions.dimensions.generators.tools.IslandTerrainGenerator;
import bitmovers.elementaldimensions.dimensions.generators.tools.MapGenTendrils;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AirChunkGenerator implements IChunkGenerator {

    public AirChunkGenerator(World worldObj) {
        this.terraingen = new IslandTerrainGenerator();
        this.worldObj = worldObj;
        long seed = 0x1fff; // @todo
        this.random = new Random((seed + 516) * 314);
        terraingen.setup(worldObj, random, Blocks.GLASS.getDefaultState());
    }

    private final World worldObj;
    @SuppressWarnings("all")
    private final Random random;
    private final IslandTerrainGenerator terraingen;
    private MapGenTendrils tendrilGenerator = new MapGenTendrils();

    @Override
    @Nonnull
    public Chunk provideChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        terraingen.generate(x, z, chunkprimer);
        tendrilGenerator.generate(this.worldObj, x, z, chunkprimer);

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);

        byte[] biomeArray = chunk.getBiomeArray();
        for (int i = 0; i < biomeArray.length; ++i) {
            biomeArray[i] = (byte) Biome.getIdForBiome(Biomes.PLAINS);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
    }

    @Override
    public boolean generateStructures(@Nonnull Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    @Nonnull
    public List<Biome.SpawnListEntry> getPossibleCreatures(@Nonnull EnumCreatureType creatureType, @Nonnull BlockPos pos) {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public BlockPos getStrongholdGen(@Nonnull World worldIn, @Nonnull String structureName, @Nonnull BlockPos position) {
        return null;
    }

    @Override
    public void recreateStructures(@Nonnull Chunk chunkIn, int x, int z) {
    }

}
