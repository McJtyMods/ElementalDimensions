package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class AltarPedestalBlock extends GenericBlock  {

    public AltarPedestalBlock() {
        super("altarpedestal", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.STONE);
    }
}
