package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderGuard extends RenderLiving<EntityGuard> {

    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/bindings/guard.png");

    public static final Factory FACTORY = new Factory();

    public RenderGuard(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelEnderman(0.0f), 0.5F);
        this.addLayer(new LayerGuardEyes(this));
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityGuard entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntityGuard> {

        @Override
        public Render<? super EntityGuard> createRenderFor(RenderManager manager) {
            return new RenderGuard(manager);
        }

    }

}
