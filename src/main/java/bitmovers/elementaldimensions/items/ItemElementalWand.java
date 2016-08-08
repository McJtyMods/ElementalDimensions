package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.init.ItemRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
        if (!worldIn.isRemote) {
            ItemStack offhand = playerIn.getHeldItemOffhand();
            if (offhand == null || !(offhand.getItem() instanceof FocusItem)) {
                playerIn.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You need to have a focus item in your off-hand"));
                return new ActionResult(EnumActionResult.PASS, itemStackIn);
            }
            if (offhand.getItem() == ItemRegister.focusTeleport) {
                doTeleport(worldIn, playerIn);
//            } else if (offhand.getItem() == ItemRegister.focusDamage) {
//                doDamage(worldIn, playerIn);
            } else {
                playerIn.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "Sorry! Not implemented yet!"));
            }
            return new ActionResult(EnumActionResult.PASS, itemStackIn);
        }
        return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
    }

    private static void doTeleport(World world, EntityPlayer player) {
        Vec3d lookVec = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        int distance = 50;  // @todo make configurable
        Vec3d end = start.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        RayTraceResult position = world.rayTraceBlocks(start, end);
        if (position == null) {
            player.setPositionAndUpdate(end.xCoord, end.yCoord, end.zCoord);
        } else {
            BlockPos blockPos = position.getBlockPos();
            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();
            if (world.isAirBlock(blockPos.up()) && world.isAirBlock(blockPos.up(2))) {
                player.setPositionAndUpdate(x+.5, y + 1, z+.5);
            } else {
                EnumFacing hit = position.sideHit;
                if (hit == EnumFacing.UP) {
                    player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You cannot teleport there!"));
                    return;
                }
                if (hit == EnumFacing.DOWN) {
                    player.setPositionAndUpdate(x+.5, y - 2, z+.5);
                    return;
                }
                player.setPositionAndUpdate(x + .5 + hit.getFrontOffsetX(), y, z + .5 + hit.getFrontOffsetZ());
            }
        }

    }

    private static void doDamage(World world, EntityPlayer player) {
        Vec3d lookVec = player.getLookVec();
        Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        int distance = 20;  // @todo make configurable
        Vec3d end = start.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
        // @todo
    }

}
