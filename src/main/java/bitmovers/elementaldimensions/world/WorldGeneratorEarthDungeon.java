package bitmovers.elementaldimensions.world;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.EarthDungeonLocator;
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

/**
 * Created by Elec332 on 5-8-2016.
 */
@RegisteredWorldGenerator(weight = 395)
public class WorldGeneratorEarthDungeon implements IWorldGenerator {

    public static final ResourceLocation dungeonResource;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimension = WorldHelper.getDimID(world);
        if (validDimension(dimension) && EarthDungeonLocator.isEarthDungeonChunk(world, chunkX, chunkZ)){
            System.out.println("EARTH: chunkX = " + chunkX + "," + chunkZ);
            Schematic schematic = SchematicLoader.INSTANCE.getSchematic(dungeonResource);
            if (schematic != null) {
                GenerationType type = GenerationType.NONE;
                StructureTemplate structure = new StructureTemplate(schematic, type);
                BlockPos pos = WorldGenHelper.randomXZPos(chunkX, chunkZ, 35, new Random(world.getSeed()));
                System.out.println("    pos = " + pos);
                structure.generateStructure(pos, world, chunkProvider);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private boolean validDimension(int dim){
        return dim == Dimensions.EARTH.getDimensionID();
    }

    static {
        dungeonResource = new EDResourceLocation("schematics/earthDungeon.schematic");
    }

}
