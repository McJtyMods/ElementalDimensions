package bitmovers.elementaldimensions.blocks.cosmetic;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class HardDirtBlock extends GenericBlock {

    public HardDirtBlock() {
        super("harddirt", Material.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(1.5F);
        setResistance(10.0F);
    }
}
