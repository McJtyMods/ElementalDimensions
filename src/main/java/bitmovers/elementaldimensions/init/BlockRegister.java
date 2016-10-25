package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.blocks.altar.AltarCenterBlock;
import bitmovers.elementaldimensions.blocks.altar.AltarPedestalBlock;
import bitmovers.elementaldimensions.blocks.cosmetic.HardDirtBlock;
import bitmovers.elementaldimensions.blocks.cosmetic.SolidFireBlock;
import bitmovers.elementaldimensions.blocks.cosmetic.SolidWaterBlock;
import bitmovers.elementaldimensions.blocks.portal.PortalBaseBlock;
import bitmovers.elementaldimensions.blocks.portal.PortalDialerBlock;
import bitmovers.elementaldimensions.dimensions.ores.ElementalShardBlock;
import bitmovers.elementaldimensions.ncLayer.overworldTweaks.blocks.SilverFishStone;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class BlockRegister {

    public static PortalDialerBlock portalDialerBlock;
    public static PortalBaseBlock portalBaseBlock;
    public static SolidWaterBlock solidWaterBlock;
    public static SolidFireBlock solidFireBlock;
    public static HardDirtBlock hardDirtBlock;
    public static ElementalShardBlock elementalShardBlock;
    public static AltarCenterBlock altarCenterBlock;
    public static AltarPedestalBlock altarPedestalBlock;

    public static Block silverFishStone;

    public static void init(){
        silverFishStone = GameRegistry.register(new SilverFishStone(), new EDResourceLocation("silverFishStone"));

        portalDialerBlock = new PortalDialerBlock();
        portalBaseBlock = new PortalBaseBlock();
        solidWaterBlock = new SolidWaterBlock();
        solidFireBlock = new SolidFireBlock();
        hardDirtBlock = new HardDirtBlock();

        elementalShardBlock = new ElementalShardBlock();
        altarCenterBlock = new AltarCenterBlock();
        altarPedestalBlock = new AltarPedestalBlock();
    }

    public static void initCrafting() {
        GameRegistry.addRecipe(new ItemStack(altarCenterBlock), " D ", "sSs", "sss", 's', ItemRegister.elementalShardItem, 'S', Blocks.GOLD_BLOCK, 'D', Items.EMERALD);
        GameRegistry.addRecipe(new ItemStack(altarPedestalBlock), " D ", " S ", "sSs", 's', ItemRegister.elementalShardItem, 'S', Blocks.STONE, 'D', Items.DIAMOND);
    }

}
