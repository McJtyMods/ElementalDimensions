package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.util.Config;
import elec332.core.util.ItemStackHelper;
import elec332.core.util.PlayerHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemElementalWand extends GenericItem {

    public ItemElementalWand() {
        super("elementalwand");
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClickC(EntityPlayer player, @Nonnull EnumHand hand, World world) {
        ItemStack offhand = player.getHeldItemOffhand();
        ItemStack itemStack = player.getHeldItemMainhand();
        if (!ItemStackHelper.isStackValid(offhand) || !(offhand.getItem() instanceof ItemFocus)) {
            if (world.isRemote) {
                PlayerHelper.sendMessageToPlayer(player, TextFormatting.RED + "You need to have a focus item in your off-hand");
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }
        int newdust = ((ItemFocus) offhand.getItem()).execute(offhand, world, player, getDustLevel(itemStack));
        setDustLevel(itemStack, newdust);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public void addInformationC(@Nonnull ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        super.addInformationC(stack, world, tooltip, advanced);
        tooltip.add(TextFormatting.GREEN + "Put in the altar to charge with elemental dust");
        int dustLevel = getDustLevel(stack);
        tooltip.add(TextFormatting.GREEN + "Charge: " + TextFormatting.YELLOW + dustLevel + TextFormatting.WHITE + " (max " + Config.Wand.maxDust + ")");
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return true;
    }

    public static int getDustLevel(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            return 0;
        } else {
            return compound.getInteger("dust");
        }
    }

    public static void setDustLevel(ItemStack stack, int dust) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }
        compound.setInteger("dust", dust);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        int dust = getDustLevel(stack);
        int max = Config.Wand.maxDust;
        return (max - dust) / (double) max;
    }

}
