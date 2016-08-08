package bitmovers.elementaldimensions.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFocusTeleport extends ItemFocus {

    public ItemFocusTeleport() {
        super("focusteleport");
    }

    @Override
    public void execute(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
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
                    player.setPositionAndUpdate(x + .5, y + 1, z + .5);
                } else {
                    EnumFacing hit = position.sideHit;
                    if (hit == EnumFacing.UP) {
                        player.addChatComponentMessage(new TextComponentString(TextFormatting.RED + "You cannot teleport there!"));
                        return;
                    }
                    if (hit == EnumFacing.DOWN) {
                        player.setPositionAndUpdate(x + .5, y - 2, z + .5);
                        return;
                    }
                    player.setPositionAndUpdate(x + .5 + hit.getFrontOffsetX(), y, z + .5 + hit.getFrontOffsetZ());
                }
            }
        }
    }

}
