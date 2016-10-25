package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class AltarCenterBlock extends GenericBlock  {

    public AltarCenterBlock() {
        super("altarcenter", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.STONE);
    }
}
