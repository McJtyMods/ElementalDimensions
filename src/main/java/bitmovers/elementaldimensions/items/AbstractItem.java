package bitmovers.elementaldimensions.items;

import elec332.core.client.IIconRegistrar;
import elec332.core.client.model.ElecModelBakery;
import elec332.core.client.model.ElecQuadBakery;
import elec332.core.client.model.INoJsonItem;
import elec332.core.client.model.template.ElecTemplateBakery;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Elec332 on 4-8-2016.
 */
public abstract class AbstractItem extends Item implements INoJsonItem {

    private TextureAtlasSprite[] textures;
    private IBakedModel model;

    @Override
    @SideOnly(Side.CLIENT)
    public IBakedModel getItemModel(ItemStack itemStack, World world, EntityLivingBase entityLivingBase) {
        return model;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerTextures(IIconRegistrar iIconRegistrar) {
        ResourceLocation[] rl = getTextureLocations();
        if (rl == null){
            rl = new ResourceLocation[0];
        }
        textures = new TextureAtlasSprite[rl.length];
        for (int i = 0; i < rl.length; i++) {
            textures[i] = iIconRegistrar.registerSprite(rl[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerModels(ElecQuadBakery elecQuadBakery, ElecModelBakery elecModelBakery, ElecTemplateBakery elecTemplateBakery) {
        model = elecModelBakery.itemModelForTextures(textures);
    }

    /*
     * Layered
     */
    protected abstract ResourceLocation[] getTextureLocations();

}
