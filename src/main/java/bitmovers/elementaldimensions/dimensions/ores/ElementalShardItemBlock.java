package bitmovers.elementaldimensions.dimensions.ores;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ElementalShardItemBlock extends ItemBlock {
    public ElementalShardItemBlock(Block block) {
        super(block);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
