package bitmovers.elementaldimensions.blocks.cosmetic;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class SolidFireBlock extends GenericBlock {

    public SolidFireBlock() {
        super("solidfire", Material.ROCK);
        setSoundType(SoundType.STONE);
    }
}
