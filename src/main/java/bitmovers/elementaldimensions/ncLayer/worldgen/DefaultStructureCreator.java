package bitmovers.elementaldimensions.ncLayer.worldgen;

import bitmovers.elementaldimensions.ncLayer.SchematicLoader;
import bitmovers.elementaldimensions.setup.ModSetup;
import elec332.core.api.structure.GenerationType;
import elec332.core.world.StructureTemplate;
import elec332.core.world.WorldHelper;
import elec332.core.world.schematic.Schematic;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Elec332 on 4-8-2016.
 *
 * Test only
 */
public class DefaultStructureCreator implements IWorldGenerator {

    public DefaultStructureCreator(ResourceLocation template, GenerationType generationType, Integer... dimensions){
        this.structureTemplate = template;
        this.generationType = generationType;
        this.dimensions = dimensions;
    }

    private final ResourceLocation structureTemplate;
    private final GenerationType generationType;
    private final Integer[] dimensions;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        Schematic template = SchematicLoader.INSTANCE.getSchematic(structureTemplate);
        if (template == null){
            return;
        }
        random.setSeed(ModSetup.random.nextLong());
        StructureTemplate structureTemplate = new StructureTemplate(template, generationType, dimensions);
        if (random.nextInt(100) == 0 && structureTemplate.canSpawnInDimension(WorldHelper.getDimID(world))){
            structureTemplate.generateStructure(new BlockPos((chunkX << 4) + random.nextInt(16), 30, (chunkZ << 4) + random.nextInt(16)), world);
        }
    }

}
