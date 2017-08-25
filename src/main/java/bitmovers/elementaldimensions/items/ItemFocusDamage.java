package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.network.PacketPointedEntity;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.varia.Broadcaster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFocusDamage extends ItemFocus {

    public ItemFocusDamage() {
        super("focusdamage");
    }

    @Override
    public int execute(ItemStack stack, World world, EntityPlayer player, int dustLevel) {
        if (dustLevel < Config.Wand.damageDustUsage) {
            if (world.isRemote) {
                Broadcaster.message(player, TextFormatting.RED + "The wand does not contain enough elemental dust!");
            }
            return dustLevel;
        }
        if (world.isRemote){
            ElementalDimensions.networkHandler.sendToServer(new PacketPointedEntity());
        }
        return dustLevel - Config.Wand.damageDustUsage;
    }

}
