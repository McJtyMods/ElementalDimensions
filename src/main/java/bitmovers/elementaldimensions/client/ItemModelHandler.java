package bitmovers.elementaldimensions.client;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.init.ItemRegister;
import bitmovers.elementaldimensions.items.GenericItem;
import com.google.common.collect.ImmutableList;
import elec332.core.client.RenderHelper;
import elec332.core.client.newstuff.IItemModelHandler;
import elec332.core.client.newstuff.ModelHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Elec332 on 5-8-2016.
 */
@ModelHandler
@SideOnly(Side.CLIENT)
public class ItemModelHandler implements IItemModelHandler {

    @Override
    public boolean handleItem(Item item) {
        return item instanceof GenericItem || item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof GenericBlock;
    }

    @Override
    public String getIdentifier(Item item) {
        return "inventory";
    }

    @Override
    public IBakedModel getModelFor(Item item, String s, ModelResourceLocation modelResourceLocation) {
        return getModel(modelResourceLocation);
    }

    private IBakedModel getModel(ModelResourceLocation modelResourceLocation){
        return Minecraft.getMinecraft().modelManager.getModel(modelResourceLocation);
    }

    //TODO
    private class RuneModelHandler implements IBakedModel {

        private RuneModelHandler(final IBakedModel[] models){
            this.iol = new ItemOverrideList(ImmutableList.of()){

                @Override
                @Nonnull
                public IBakedModel handleItemState(@Nonnull IBakedModel originalModel, ItemStack stack, @Nonnull World world, @Nullable EntityLivingBase entity) {
                    return models[stack.getItemDamage()];
                }

            };
        }

        private final ItemOverrideList iol;

        @Override
        @Nonnull
        public List<BakedQuad> getQuads(IBlockState p_188616_1_, EnumFacing p_188616_2_, long p_188616_3_) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isAmbientOcclusion() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isGui3d() {
            return false;
        }

        @Override
        public boolean isBuiltInRenderer() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Nonnull
        public TextureAtlasSprite getParticleTexture() {
            return RenderHelper.getMissingTextureIcon();
        }

        @Override
        @Nonnull
        public ItemCameraTransforms getItemCameraTransforms() {
            throw new UnsupportedOperationException();
        }

        @Override
        @Nonnull
        public ItemOverrideList getOverrides() {
            return this.iol;
        }

    }

}
