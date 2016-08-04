package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ncLayer.overworldTweaks.blocks.SilverFishStone;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class BlockRegister {

    public static Block silverFishStone;

    public static void init(){
        silverFishStone = GameRegistry.register(new SilverFishStone());
    }

}
