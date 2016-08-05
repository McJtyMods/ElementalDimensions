package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.blocks.GenericTileEntity;

public class PortalBaseTileEntity extends GenericTileEntity {

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

}
