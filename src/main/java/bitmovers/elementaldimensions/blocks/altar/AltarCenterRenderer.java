package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.client.RegisteredTESR;
import bitmovers.elementaldimensions.client.RenderTools;
import bitmovers.elementaldimensions.init.BlockRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@RegisteredTESR(AltarCenterTileEntity.class)
public class AltarCenterRenderer extends TileEntitySpecialRenderer<AltarCenterTileEntity> {

    ResourceLocation blueSphereTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/bluesphere.png");

    @Override
    public void renderTileEntityAt(AltarCenterTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        if (!te.isWorking()) {
            return;
        }

        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != BlockRegister.altarCenterBlock) {
            return;
        }

        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

//        GlStateManager.enableAlpha();
        GlStateManager.disableAlpha();

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 1.9F, (float) z + 0.5F);

        this.bindTexture(blueSphereTexture);
        RenderTools.renderBillboardQuadBright(1.2f, 240);

        renderItem(te);


        GlStateManager.popMatrix();

//        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void renderItem(AltarCenterTileEntity te) {
        ItemStack stack = te.getStack();
        if (stack != null) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.enableLighting();
            GlStateManager.pushMatrix();
            // Translate to the center of the block and .9 points higher
            GlStateManager.translate(.5, .9, .5);
            GlStateManager.scale(.4f, .4f, .4f);

            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

            GlStateManager.popMatrix();
        }
    }
}
