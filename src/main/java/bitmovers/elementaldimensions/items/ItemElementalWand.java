package bitmovers.elementaldimensions.items;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemElementalWand extends GenericItem {

    public ItemElementalWand() {
        super("elementalwand");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        //if (!worldIn.isRemote) {
            ItemStack offhand = playerIn.getHeldItemOffhand();
            if (offhand == null || !(offhand.getItem() instanceof ItemFocus)) {
                playerIn.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You need to have a focus item in your off-hand"));
                return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
            }
            ((ItemFocus) offhand.getItem()).execute(offhand, worldIn, playerIn);
        //}
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return true;
    }

    private static void doDamage(World world, EntityPlayer player) {
        Vec3d lookVec = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        int distance = 20;  // @todo make configurable
        Vec3d end = start.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        // @todo
    }

}
