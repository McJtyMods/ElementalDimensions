package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.client.RegisteredTESR;
import bitmovers.elementaldimensions.client.RenderTools;
import bitmovers.elementaldimensions.init.BlockRegister;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
@RegisteredTESR(AltarCenterTileEntity.class)
public class AltarCenterRenderer extends TileEntitySpecialRenderer<AltarCenterTileEntity> {

    ResourceLocation blueSphereTexture = new ResourceLocation(ElementalDimensions.MODID, "textures/effects/bluesphere.png");

    @Override
    public void renderTileEntityAt(AltarCenterTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        IBlockState blockState = getWorld().getBlockState(te.getPos());
        if (blockState.getBlock() != BlockRegister.altarCenterBlock) {
            return;
        }

        if (te.isWorking()) {
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

            GlStateManager.disableAlpha();

            GlStateManager.pushMatrix();
            GlStateManager.translate((float) x + 0.5F, (float) y + 1.9F, (float) z + 0.5F);

            this.bindTexture(blueSphereTexture);
            RenderTools.renderBillboardQuadBright(1.2f, 240);


            GlStateManager.popMatrix();

            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }

        renderItem(te, x, y, z);
    }

    private void renderItem(AltarCenterTileEntity te,  double x, double y, double z) {
        ItemStack stack = te.getChargingItem();
        if (ItemStackTools.isValid(stack)) {
            RenderHelper.enableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
//            GlStateManager.enableLighting();
            GlStateManager.pushMatrix();
            // Translate to the center of the block and .9 points higher
            GlStateManager.translate(x + .5, y + 1.5, z + .5);
            GlStateManager.scale(.4f, .4f, .4f);

            RenderTools.renderItemCustom(stack, 0, .4f, true);
//            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

            GlStateManager.popMatrix();
        }
    }
}
