package bitmovers.elementaldimensions.blocks;

import bitmovers.elementaldimensions.blocks.portal.PortalBaseBlock;
import bitmovers.elementaldimensions.blocks.portal.PortalDialerBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static PortalDialerBlock portalDialerBlock;
    public static PortalBaseBlock portalBaseBlock;

    public static void preInit() {
        portalDialerBlock = new PortalDialerBlock();
        portalBaseBlock = new PortalBaseBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        portalDialerBlock.initModel();
        portalBaseBlock.initModel();
    }
}
