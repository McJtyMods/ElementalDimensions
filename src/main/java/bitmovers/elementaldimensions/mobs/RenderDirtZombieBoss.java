package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderDirtZombieBoss extends RenderLiving<EntityDirtZombieBoss> {

    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/entity/dirtzombie.png");

    public static final Factory FACTORY = new Factory();

    public RenderDirtZombieBoss(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 1.0F);
    }

    @Override
    protected void preRenderCallback(EntityDirtZombieBoss entitylivingbaseIn, float partialTickTime) {
        GlStateManager.scale(4, 4, 4);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityDirtZombieBoss entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityDirtZombieBoss> {

        @Override
        public Render<? super EntityDirtZombieBoss> createRenderFor(RenderManager manager) {
            return new RenderDirtZombieBoss(manager);
        }

    }

}
