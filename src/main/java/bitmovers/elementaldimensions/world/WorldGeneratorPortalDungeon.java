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
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Elec332 on 5-8-2016.
 */
@RegisteredWorldGenerator(weight = 394)
public class WorldGeneratorPortalDungeon implements IWorldGenerator {

    public static final ResourceLocation dungeonResource;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimension = WorldHelper.getDimID(world);
        if (validDimension(dimension) && PortalDungeonLocator.isPortalChunk(chunkX, chunkZ)){
            Schematic schematic = SchematicLoader.INSTANCE.getSchematic(dungeonResource);
            if (schematic != null) {
                StructureTemplate structure = new StructureTemplate(schematic, GenerationType.SURFACE);
                structure.generateStructure(WorldGenHelper.randomXZPos(chunkX, chunkZ, 0, random), world, chunkProvider);
            } else {
                throw new IllegalStateException();
            }
        }
    }

    private boolean validDimension(int dim){
        if (dim == 0){
            return true;
        }
        for (Dimensions dimension : Dimensions.values()){
            if (dimension.getDimensionID() == dim){
                return true;
            }
        }
        return false;
    }

    static {
        dungeonResource = new EDResourceLocation("schematics/portalDungeon.schematic");
    }

}
