package bitmovers.elementaldimensions.mobs;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class RenderSpirit extends RenderLiving<EntitySpirit> {

    private ResourceLocation mobTexture = new ResourceLocation("elementaldimensions:textures/entity/spirit.png");

    public static final Factory FACTORY = new Factory();

    public RenderSpirit(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5F);
    }


    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntitySpirit entity) {
        return mobTexture;
    }

    public static class Factory implements IRenderFactory<EntitySpirit> {

        @Override
        public Render<? super EntitySpirit> createRenderFor(RenderManager manager) {
            return new RenderSpirit(manager);
        }

    }

}
