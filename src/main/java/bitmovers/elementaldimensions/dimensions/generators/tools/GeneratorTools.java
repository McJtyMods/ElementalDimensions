package bitmovers.elementaldimensions.dimensions.generators.tools;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.ChunkPrimer;

public class GeneratorTools {
    public static void setBlockState(ChunkPrimer primer, int index, IBlockState state) {
        primer.data[index] = (char) Block.BLOCK_STATE_IDS.get(state);
    }

    public  static IBlockState getBlockState(ChunkPrimer primer, int index) {
        return Block.BLOCK_STATE_IDS.getByValue(primer.data[index]);

    }
}
