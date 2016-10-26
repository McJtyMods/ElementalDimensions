package bitmovers.elementaldimensions.dimensions.ores;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ElementalDustItemBlock extends ItemBlock {
    public ElementalDustItemBlock(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
