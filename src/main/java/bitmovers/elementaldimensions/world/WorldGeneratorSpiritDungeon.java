package bitmovers.elementaldimensions.world;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.SpiritDungeonLocator;
import bitmovers.elementaldimensions.ncLayer.SchematicLoader;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.worldgen.RegisteredWorldGenerator;
import bitmovers.elementaldimensions.util.worldgen.WorldGenHelper;
import elec332.core.api.structure.GenerationType;
import elec332.core.world.StructureTemplate;
import elec332.core.world.WorldHelper;
import elec332.core.world.schematic.Schematic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

@RegisteredWorldGenerator(weight = 391)
public class WorldGeneratorSpiritDungeon implements IWorldGenerator {

    public static final ResourceLocation dungeonResource;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimension = WorldHelper.getDimID(world);
        if (validDimension(dimension) && SpiritDungeonLocator.isSpiritDungeonChunk(world, chunkX, chunkZ)){
            Schematic schematic = SchematicLoader.INSTANCE.getSchematic(dungeonResource);
            if (schematic != null) {
                GenerationType type = GenerationType.SURFACE;
                StructureTemplate structure = new StructureTemplate(schematic, type);
                BlockPos pos = WorldGenHelper.randomXZPos(chunkX, chunkZ, 0, new Random(world.getSeed()));
                structure.generateStructure(pos, world, chunkProvider);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private boolean validDimension(int dim){
        return dim == Dimensions.SPIRIT.getDimensionID();
    }

    static {
        dungeonResource = new EDResourceLocation("schematics/dungeon_spirit.schematic");
    }

}
