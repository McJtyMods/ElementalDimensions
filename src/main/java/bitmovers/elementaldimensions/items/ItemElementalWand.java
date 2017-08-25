package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.varia.Broadcaster;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemElementalWand extends GenericItem {

    public ItemElementalWand() {
        super("elementalwand");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        ItemStack offhand = player.getHeldItemOffhand();
        ItemStack itemStack = player.getHeldItemMainhand();
        if (offhand.isEmpty() || !(offhand.getItem() instanceof ItemFocus)) {
            if (world.isRemote) {
                Broadcaster.message(player, TextFormatting.RED + "You need to have a focus item in your off-hand");
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
        }
        int newdust = ((ItemFocus) offhand.getItem()).execute(offhand, world, player, getDustLevel(itemStack));
        setDustLevel(itemStack, newdust);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
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
