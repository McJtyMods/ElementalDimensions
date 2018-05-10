package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhost extends RenderLiving<EntityGhost> {
    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/bindings/ghost.png");
    private ResourceLocation mobShootingTexture = new ResourceLocation("elementaldimensions:textures/bindings/ghost_shooting.png");

    public RenderGhost(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelGhast(), 0.5F);
    }

    /**
     * Returns the location of an bindings's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityGhost entity) {
        return entity.isAttacking() ? mobShootingTexture : mobTexture;
    }

    public static final RenderGhost.Factory FACTORY = new RenderGhost.Factory();

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    @Override
    protected void preRenderCallback(EntityGhost entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(4.5F, 4.5F, 4.5F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static class Factory implements IRenderFactory<EntityGhost> {

        @Override
        public Render<? super EntityGhost> createRenderFor(RenderManager manager) {
            return new RenderGhost(manager);
        }

    }

}