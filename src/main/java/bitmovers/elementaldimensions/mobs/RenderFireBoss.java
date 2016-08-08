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
public class RenderFireBoss extends RenderLiving<EntityFireBoss> {
    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/entity/fire_boss.png");
    private ResourceLocation mobShootingTexture = new ResourceLocation("elementaldimensions:textures/entity/fire_boss_shooting.png");

    public RenderFireBoss(RenderManager renderManagerIn) {
        super(renderManagerIn, new ModelGhast(), 3.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    @Override
    protected ResourceLocation getEntityTexture(EntityFireBoss entity) {
        return entity.isAttacking() ? mobShootingTexture : mobTexture;
    }

    public static final RenderFireBoss.Factory FACTORY = new RenderFireBoss.Factory();

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    @Override
    protected void preRenderCallback(EntityFireBoss entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(4.5F*3, 4.5F*3, 4.5F*3);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static class Factory implements IRenderFactory<EntityFireBoss> {

        @Override
        public Render<? super EntityFireBoss> createRenderFor(RenderManager manager) {
            return new RenderFireBoss(manager);
        }

    }

}