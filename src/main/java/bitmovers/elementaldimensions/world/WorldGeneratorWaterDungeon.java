package bitmovers.elementaldimensions.world;

import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.WaterDungeonLocator;
import bitmovers.elementaldimensions.dimensions.generators.tools.WaterTerrainGenerator;
import bitmovers.elementaldimensions.dimensions.ores.ElementalDustBlock;
import bitmovers.elementaldimensions.init.BlockRegister;
import bitmovers.elementaldimensions.ncLayer.SchematicLoader;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.util.worldgen.RegisteredWorldGenerator;
import bitmovers.elementaldimensions.util.worldgen.WorldGenHelper;
import elec332.core.api.structure.GenerationType;
import elec332.core.world.StructureTemplate;
import elec332.core.world.WorldHelper;
import elec332.core.world.schematic.Schematic;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

@RegisteredWorldGenerator(weight = 396)
public class WorldGeneratorWaterDungeon implements IWorldGenerator {

    public static final ResourceLocation dungeonResource;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        int dimension = WorldHelper.getDimID(world);
        if (validDimension(dimension)){
            IBlockState ore = BlockRegister.elementalDustBlock.getDefaultState().withProperty(ElementalDustBlock.ORETYPE, ElementalDustBlock.OreType.ORE_STONE);
            WorldGenHelper.addOreSpawn(ore, Blocks.STONE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 5, 8, 6, 2, 50);

            if (WaterDungeonLocator.isWaterDungeonChunk(world, chunkX, chunkZ)){
                Schematic schematic = SchematicLoader.INSTANCE.getSchematic(dungeonResource);
                if (schematic != null) {
                    GenerationType type = GenerationType.NONE;
                    StructureTemplate structure = new StructureTemplate(schematic, type);
                    BlockPos pos = WorldGenHelper.randomXZPos(chunkX, chunkZ, 0, new Random(world.getSeed()));
                    pos = world.getTopSolidOrLiquidBlock(pos);
                    if (pos.getY() > WaterTerrainGenerator.SEALEVEL - 3) {
                        return;
                    }
                    structure.generateStructure(pos, world);
                } else {
                    throw new IllegalStateException();
                }
            }
        }
    }

    private boolean validDimension(int dim){
        return dim == Dimensions.WATER.getDimensionID();
    }

    static {
        dungeonResource = new EDResourceLocation("schematics/dungeon_water.schematic");
    }

}
