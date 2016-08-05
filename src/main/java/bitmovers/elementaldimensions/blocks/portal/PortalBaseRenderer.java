package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.tools.RenderTools;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class PortalBaseRenderer extends TileEntitySpecialRenderer<PortalBaseTileEntity> {

    ResourceLocation halo = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/portaleffect.png");

    @Override
    public void renderTileEntityAt(PortalBaseTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
//        super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 1.7F, (float) z + 0.5F);
        this.bindTexture(halo);
        RenderTools.renderQuadBright(1.0f);
        GlStateManager.popMatrix();
    }
}
