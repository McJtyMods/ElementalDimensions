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
public class RenderGhostBoss extends RenderLiving<EntityGhostBoss> {
    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/bindings/ghost_boss.png");
    private ResourceLocation mobShootingTexture = new ResourceLocation("elementaldimensions:textures/bindings/ghost_boss_shooting.png");

    public RenderGhostBoss(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelGhast(), 2.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityGhostBoss entity) {
        return entity.isAttacking() ? mobShootingTexture : mobTexture;
    }

    public static final RenderGhostBoss.Factory FACTORY = new RenderGhostBoss.Factory();

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    @Override
    protected void preRenderCallback(EntityGhostBoss entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(4.5F*2, 4.5F*2, 4.5F*2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static class Factory implements IRenderFactory<EntityGhostBoss> {

        @Override
        public Render<? super EntityGhostBoss> createRenderFor(RenderManager manager) {
            return new RenderGhostBoss(manager);
        }

    }

}