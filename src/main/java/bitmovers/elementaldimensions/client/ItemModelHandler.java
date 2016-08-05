package bitmovers.elementaldimensions.client;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.items.GenericItem;
import elec332.core.client.newstuff.IItemModelHandler;
import elec332.core.client.newstuff.ModelHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        return Minecraft.getMinecraft().modelManager.getModel(modelResourceLocation);
    }

}
