package bitmovers.elementaldimensions.setup;


import bitmovers.elementaldimensions.varia.DelayedRegister;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Registration {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        DelayedRegister.registerBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        DelayedRegister.registerItems(event.getRegistry());
    }
}
