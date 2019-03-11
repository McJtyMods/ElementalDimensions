package bitmovers.elementaldimensions.setup;


import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.items.GenericItem;
import bitmovers.elementaldimensions.varia.DelayedRegister;
import elec332.core.util.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientRegistration {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Block block : RegistryHelper.getBlockRegistry().getValues()){
            if (block instanceof GenericBlock){
                ((GenericBlock) block).initClient();
            }
        }
        DelayedRegister.getItems().forEach(item -> {
            if (item instanceof GenericItem) {
                ((GenericItem) item).initModel();
            }
        });
    }

}
