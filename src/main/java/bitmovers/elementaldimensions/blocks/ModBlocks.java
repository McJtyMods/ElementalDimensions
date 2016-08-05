package bitmovers.elementaldimensions.blocks;

import bitmovers.elementaldimensions.blocks.portal.PortalBaseBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static PortalBaseBlock portalBaseBlock;

    public static void preInit() {
        portalBaseBlock = new PortalBaseBlock();
    }

    @SideOnly(Side.CLIENT)
    public static void initClient() {
        portalBaseBlock.initModel();
    }
}
