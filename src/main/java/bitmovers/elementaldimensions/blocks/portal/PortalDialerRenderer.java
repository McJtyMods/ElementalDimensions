package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.blocks.portal.PortalDialerBlock;
import bitmovers.elementaldimensions.blocks.portal.PortalDialerTileEntity;
import bitmovers.elementaldimensions.client.RegisteredTESR;
import bitmovers.elementaldimensions.client.RenderTools;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@RegisteredTESR(PortalDialerTileEntity.class)
public class PortalDialerRenderer extends TileEntitySpecialRenderer<PortalDialerTileEntity> {

    ResourceLocation portalTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/portaleffect.png");
    ResourceLocation spiritTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/portalspirit.png");
    ResourceLocation fireTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/portalfire.png");
    ResourceLocation airTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/portalair.png");
    ResourceLocation waterTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/portalwater.png");

    @Override
    public void renderTileEntityAt(PortalDialerTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 3.0F, (float) z + 0.5F);

        EnumFacing facing = getWorld().getBlockState(te.getPos()).getValue(PortalDialerBlock.FACING_HORIZ);
        if (facing == EnumFacing.WEST || facing == EnumFacing.EAST) {
            GlStateManager.rotate(90, 0, 1, 0);
        }

        long bright = System.currentTimeMillis() % 2400;
        if (bright > 1200) {
            bright = 2400-bright;
        }
        int brightness = (int) (bright / 10);
        this.bindTexture(portalTexture);
        RenderTools.renderQuadBright(2.5f, brightness);
        GlStateManager.rotate(180, 0, 1, 0);
        RenderTools.renderQuadBright(2.5f, brightness);

        ResourceLocation txt = null;
        if (te.getDestination() != null) {
            switch (te.getDestination()) {
                case EARTH:
                    break;
                case WATER:
                    txt = waterTexture;
                    break;
                case AIR:
                    txt = airTexture;
                    break;
                case SPIRIT:
                    txt = spiritTexture;
                    break;
                case FIRE:
                    txt = fireTexture;
                    break;
            }
        }

        if (txt != null) {
            this.bindTexture(txt);
            RenderTools.renderQuadBright(1.5f, 240);
            GlStateManager.rotate(180, 0, 1, 0);
            RenderTools.renderQuadBright(1.5f, 240);
        }

        GlStateManager.popMatrix();
    }
}
