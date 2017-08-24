package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.WaterChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nonnull;

public class WaterWorldProvider extends WorldProvider {

    @Override
    @Nonnull
    public DimensionType getDimensionType() {
        return DimensionRegister.waterDimensionType;
    }

    @Override
    @Nonnull
    public String getSaveFolder() {
        return "ELDIM_WATER";
    }

    @Override
    @Nonnull
    public IChunkGenerator createChunkGenerator() {
        return new WaterChunkGenerator(world);
    }

    @Override
    public void calculateInitialWeather() {
        world.thunderingStrength = 1.0F;
        world.rainingStrength = 1.0F;
        world.getWorldInfo().setThundering(true);
        world.getWorldInfo().setRaining(true);
    }

    @Override
    public void updateWeather() {
        WorldInfo worldInfo = world.getWorldInfo();
        if (!world.isRemote) {
            world.thunderingStrength = 1.0f;
            world.rainingStrength = 1.0F;
            worldInfo.setThundering(true);
            worldInfo.setRaining(true);
        }
        worldInfo.setCleanWeatherTime(0);
        worldInfo.setThunderTime(worldInfo.getThunderTime() - 100);
        world.updateWeatherBody();
    }

    @Override
    protected void init() {
        super.init();
        this.biomeProvider = new BiomeProvider(world.getWorldInfo()) {

            @Override
            @Nonnull
            public Biome getBiome(@Nonnull BlockPos pos) {
                return Biomes.DEEP_OCEAN;
            }

            @Override
            @Nonnull
            public Biome getBiome(BlockPos pos, @Nonnull Biome defaultBiome) {
                return Biomes.DEEP_OCEAN;
            }

        };
    }
}
