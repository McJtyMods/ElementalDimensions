package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.EarthChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.IChunkGenerator;

public class EarthWorldProvider extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        return DimensionRegister.earthDimensionType;
    }

    @Override
    public String getSaveFolder() {
        return "ELDIM_EARTH";
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new EarthChunkGenerator(worldObj);
    }

    @Override
    protected void createBiomeProvider() {
        this.biomeProvider = new BiomeProvider(worldObj.getWorldInfo()) {
            @Override
            public Biome getBiome(BlockPos pos) {
                return Biomes.PLAINS;
            }

            @Override
            public Biome getBiome(BlockPos pos, Biome defaultBiome) {
                return Biomes.PLAINS;
            }
        };
    }
}
