package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderDirtZombie extends RenderLiving<EntityDirtZombie> {

    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/bindings/dirtzombie.png");

    public static final Factory FACTORY = new Factory();

    public RenderDirtZombie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityDirtZombie entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityDirtZombie> {

        @Override
        public Render<? super EntityDirtZombie> createRenderFor(RenderManager manager) {
            return new RenderDirtZombie(manager);
        }

    }

}
