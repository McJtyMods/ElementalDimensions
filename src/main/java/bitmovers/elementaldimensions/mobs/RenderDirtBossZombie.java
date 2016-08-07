package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderDirtBossZombie extends RenderLiving<EntityDirtBossZombie> {

    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/entity/dirtzombie.png");

    public static final Factory FACTORY = new Factory();

    public RenderDirtBossZombie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5F);
    }

    @Override
    protected void preRenderCallback(EntityDirtBossZombie entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(5, 5, 5);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityDirtBossZombie entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityDirtBossZombie> {

        @Override
        public Render<? super EntityDirtBossZombie> createRenderFor(RenderManager manager) {
            return new RenderDirtBossZombie(manager);
        }

    }

}
