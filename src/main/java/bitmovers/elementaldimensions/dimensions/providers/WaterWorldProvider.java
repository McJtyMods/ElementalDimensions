package bitmovers.elementaldimensions.dimensions.providers;

import bitmovers.elementaldimensions.dimensions.generators.WaterChunkGenerator;
import bitmovers.elementaldimensions.init.DimensionRegister;
import mcjty.lib.compat.CompatWorldProvider;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.WorldInfo;

import javax.annotation.Nonnull;

public class WaterWorldProvider extends CompatWorldProvider {

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
        return new WaterChunkGenerator(getWorld());
    }

    @Override
    public void calculateInitialWeather() {
        getWorld().thunderingStrength = 1.0F;
        getWorld().rainingStrength = 1.0F;
        getWorld().getWorldInfo().setThundering(true);
        getWorld().getWorldInfo().setRaining(true);
    }

    @Override
    public void updateWeather() {
        WorldInfo worldInfo = getWorld().getWorldInfo();
        if (!getWorld().isRemote) {
            getWorld().thunderingStrength = 1.0f;
            getWorld().rainingStrength = 1.0F;
            worldInfo.setThundering(true);
            worldInfo.setRaining(true);
        }
        worldInfo.setCleanWeatherTime(0);
        worldInfo.setThunderTime(worldInfo.getThunderTime() - 100);
        getWorld().updateWeatherBody();
    }

    @Override
    protected void initialize() {
        super.initialize();
        this.biomeProvider = new BiomeProvider(getWorld().getWorldInfo()) {

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
