package bitmovers.elementaldimensions.ncLayer.overworldTweaks;

import bitmovers.elementaldimensions.init.BlockRegister;
import bitmovers.elementaldimensions.util.worldgen.RegisteredWorldGenerator;
import bitmovers.elementaldimensions.util.worldgen.WorldGenHelper;
import elec332.core.world.WorldHelper;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

/**
 * Created by Elec332 on 4-8-2016.
 */
@RegisteredWorldGenerator
public class SilverfishTweak implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        for (int i = 0; i < random.nextInt(40); i++) {
            BlockPos pos = WorldGenHelper.randomXZPos(chunkX, chunkZ, random.nextInt(64) + 1, random);
            IBlockState blockState = WorldHelper.getBlockState(world, pos);
            if (blockState.getBlock() == Blocks.STONE && blockState.getValue(BlockStone.VARIANT) == BlockStone.EnumType.STONE){
                world.setBlockState(pos, BlockRegister.silverFishStone.getDefaultState());
            }
        }
    }

}
