package bitmovers.elementaldimensions.dimensions.generators.tools;

import net.minecraft.world.chunk.ChunkPrimer;

public interface ITerrainGenerator {

    void generate(int chunkX, int chunkZ, ChunkPrimer primer);

}
