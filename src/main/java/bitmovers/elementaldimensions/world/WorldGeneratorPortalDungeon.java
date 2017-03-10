package bitmovers.elementaldimensions.world;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.PortalDungeonLocator;
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
@RegisteredWorldGenerator(weight = 3)
public class WorldGeneratorPortalDungeon implements IWorldGenerator {

    public static final ResourceLocation dungeonResource;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        System.out.println("WorldGeneratorPortalDungeon.generate");
        int dimension = WorldHelper.getDimID(world);
        if (validDimension(dimension) && PortalDungeonLocator.isPortalChunk(chunkX, chunkZ)){
            Schematic schematic = SchematicLoader.INSTANCE.getSchematic(dungeonResource);
            if (schematic != null) {
                GenerationType type = GenerationType.SURFACE;
                Dimensions dim = Dimensions.findDimension(dimension);
                if (dim != null) {
                    type = dim.getGenerationType();
                }
                StructureTemplate structure = new StructureTemplate(schematic, type);
                BlockPos pos = WorldGenHelper.randomXZPos(chunkX, chunkZ, 0, new Random(PortalDungeonLocator.getSpecialSeed(chunkX, chunkZ)));
                structure.generateStructure(dim.adjustHeight(chunkX, chunkZ, pos, world, random), world, chunkProvider);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private boolean validDimension(int dim){
        if (dim == 0){
            return true;
        }
        Dimensions dimension = Dimensions.findDimension(dim);
        return dimension != null;
    }

    static {
        dungeonResource = new EDResourceLocation("schematics/dungeon_portal.schematic");
    }

}
