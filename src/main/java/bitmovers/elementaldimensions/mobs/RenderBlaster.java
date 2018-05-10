package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderBlaster extends RenderLiving<EntityBlaster> {

    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/entity/blaster.png");

    public static final Factory FACTORY = new Factory();

    public RenderBlaster(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelBlaze(), 0.5F);
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlaster entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityBlaster> {

        @Override
        public Render<? super EntityBlaster> createRenderFor(RenderManager manager) {
            return new RenderBlaster(manager);
        }

    }

}
