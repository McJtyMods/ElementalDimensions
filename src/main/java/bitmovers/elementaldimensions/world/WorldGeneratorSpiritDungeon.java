package bitmovers.elementaldimensions.world;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.SpiritDungeonLocator;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.worldgen.RegisteredWorldGenerator;
import bitmovers.elementaldimensions.util.worldgen.WorldGenHelper;
import elec332.core.api.structure.GenerationType;
import elec332.core.world.WorldHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@RegisteredWorldGenerator(weight = 391)
public class WorldGeneratorSpiritDungeon extends AbstractDungeonWorldGenerator {

    @Override
    protected boolean validGenPoint(World world, int chunkX, int chunkZ) {
        return WorldHelper.getDimID(world) == Dimensions.SPIRIT.getDimensionID() && SpiritDungeonLocator.isSpiritDungeonChunk(world, chunkX, chunkZ);
    }

    @Override
    protected BlockPos randomPos(World world, int chunkX, int chunkZ, Random random) {
        return WorldGenHelper.randomXZPos(chunkX, chunkZ, 0, new Random(world.getSeed()));
    }

    @Override
    protected ResourceLocation getDungeonSchematic() {
        return dungeonResource;
    }

    @Override
    protected GenerationType getGenerationType(World world) {
        return GenerationType.SURFACE;
    }

    public static final ResourceLocation dungeonResource;

    static {
        dungeonResource = new EDResourceLocation("schematics/dungeon_spirit.schematic");
    }

}
