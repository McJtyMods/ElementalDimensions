package bitmovers.elementaldimensions.items;

import elec332.core.util.ItemStackHelper;
import elec332.core.util.PlayerHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class ItemElementalWand extends GenericItem {

    public ItemElementalWand() {
        super("elementalwand");
    }

    @Nonnull
    @Override
    protected ActionResult<ItemStack> onItemRightClick(EntityPlayer player, @Nonnull EnumHand hand, World world) {
        ItemStack offhand = player.getHeldItemOffhand(), itemStack = player.getHeldItemMainhand();
        if (!ItemStackHelper.isStackValid(offhand) || !(offhand.getItem() instanceof ItemFocus)) {
            PlayerHelper.sendMessageToPlayer(player, TextFormatting.RED + "You need to have a focus item in your off-hand");
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }
        ((ItemFocus) offhand.getItem()).execute(offhand, world, player);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return true;
    }


}
