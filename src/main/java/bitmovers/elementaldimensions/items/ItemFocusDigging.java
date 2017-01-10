package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.util.Config;
import elec332.core.util.PlayerHelper;
import elec332.core.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFocusDigging extends ItemFocus {

    public ItemFocusDigging() {
        super("focusdigging");
    }

    @Override
    public int execute(ItemStack stack, World world, EntityPlayer player, int dustLevel) {
        if (dustLevel < Config.Wand.diggingDustUsage) {
            if (world.isRemote) {
                PlayerHelper.sendMessageToPlayer(player, TextFormatting.RED + "The wand does not contain enough elemental dust!");
            }
            return dustLevel;
        }

        if (!world.isRemote) {
            Vec3d lookVec = player.getLookVec();
            Vec3d start = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
            int distance = 20;  // @todo make configurable
            Vec3d end = start.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
            RayTraceResult position = world.rayTraceBlocks(start, end);
            if (position != null && WorldHelper.getBlockAt(world, position.getBlockPos()) != Blocks.BEDROCK) {
                EntityPlayerMP playerMP = (EntityPlayerMP) player;
                playerMP.interactionManager.tryHarvestBlock(position.getBlockPos());
            }
        }
        return dustLevel - Config.Wand.diggingDustUsage;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }
}
