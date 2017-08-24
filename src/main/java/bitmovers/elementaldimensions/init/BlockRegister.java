package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.blocks.altar.AltarCenterBlock;
import bitmovers.elementaldimensions.blocks.cosmetic.HardDirtBlock;
import bitmovers.elementaldimensions.blocks.cosmetic.SolidFireBlock;
import bitmovers.elementaldimensions.blocks.cosmetic.SolidWaterBlock;
import bitmovers.elementaldimensions.blocks.portal.PortalBaseBlock;
import bitmovers.elementaldimensions.blocks.portal.PortalDialerBlock;
import bitmovers.elementaldimensions.dimensions.ores.ElementalDustBlock;
import net.minecraft.block.Block;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class BlockRegister {

    public static PortalDialerBlock portalDialerBlock;
    public static PortalBaseBlock portalBaseBlock;
    public static SolidWaterBlock solidWaterBlock;
    public static SolidFireBlock solidFireBlock;
    public static HardDirtBlock hardDirtBlock;
    public static ElementalDustBlock elementalDustBlock;
    public static AltarCenterBlock altarCenterBlock;
//    public static AltarPedestalBlock altarPedestalBlock;

    public static Block silverFishStone;

    public static void init(){
//        silverFishStone = GameRegistry.register(new SilverFishStone(), new EDResourceLocation("silverFishStone"));

        portalDialerBlock = new PortalDialerBlock();
        portalBaseBlock = new PortalBaseBlock();
        solidWaterBlock = new SolidWaterBlock();
        solidFireBlock = new SolidFireBlock();
        hardDirtBlock = new HardDirtBlock();

        elementalDustBlock = new ElementalDustBlock();
        altarCenterBlock = new AltarCenterBlock();
//        altarPedestalBlock = new AltarPedestalBlock();
    }
}
