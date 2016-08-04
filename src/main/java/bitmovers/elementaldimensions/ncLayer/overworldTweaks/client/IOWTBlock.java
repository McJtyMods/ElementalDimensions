package bitmovers.elementaldimensions.ncLayer.overworldTweaks.client;

import elec332.core.client.IIconRegistrar;
import elec332.core.client.model.ElecModelBakery;
import elec332.core.client.model.ElecQuadBakery;
import elec332.core.client.model.INoJsonBlock;
import elec332.core.client.model.template.ElecTemplateBakery;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Elec332 on 4-8-2016.
 */
public interface IOWTBlock extends INoJsonBlock{

    @Override
    @SideOnly(Side.CLIENT)
    default public IBakedModel getBlockModel(IBlockState iBlockState) {
        return ClientBlockHandler.INSTANCE.getBlockModel(iBlockState);
    }

    @Override
    @SideOnly(Side.CLIENT)
    default public IBakedModel getItemModel(ItemStack itemStack, World world, EntityLivingBase entityLivingBase) {
        return ClientBlockHandler.INSTANCE.getItemModel(itemStack, world, entityLivingBase);
    }

    @Override
    @SideOnly(Side.CLIENT)
    default public void registerTextures(IIconRegistrar iIconRegistrar) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    default public void registerModels(ElecQuadBakery elecQuadBakery, ElecModelBakery elecModelBakery, ElecTemplateBakery elecTemplateBakery) {
    }

}
