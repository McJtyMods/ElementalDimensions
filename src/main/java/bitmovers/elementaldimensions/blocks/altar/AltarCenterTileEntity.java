package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.blocks.GenericTileEntity;
import bitmovers.elementaldimensions.init.ItemRegister;
import bitmovers.elementaldimensions.items.ItemElementalWand;
import bitmovers.elementaldimensions.util.Config;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AltarCenterTileEntity extends GenericTileEntity implements ITickable {

    private boolean working = false;

    private ItemStack chargingItem = ItemStackTools.getEmptyStack();
    private ItemStack dust = ItemStackTools.getEmptyStack();

    @Override
    public void update() {
        if (working && !getWorld().isRemote) {
            if (ItemStackTools.isValid(chargingItem) && ItemStackTools.isValid(dust) && chargingItem.getItem() == ItemRegister.elementalWand) {
                int dustLevel = ItemElementalWand.getDustLevel(chargingItem);
                if (dustLevel < Config.Wand.maxDust) {
                    dust.splitStack(1);
                    ItemElementalWand.setDustLevel(chargingItem, dustLevel+1);
                }
            }
        }
    }

    public ItemStack getChargingItem() {
        return chargingItem;
    }

    public void setChargingItem(ItemStack chargingItem) {
        this.chargingItem = chargingItem;
        markDirtyClient();
    }


    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        boolean working = isWorking();

        super.onDataPacket(net, packet);

        if (getWorld().isRemote) {
            // If needed send a render update.
            boolean newWorking = isWorking();
            if (newWorking != working) {
                getWorld().markBlockRangeForRenderUpdate(getPos(), getPos());
            }
        }
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(int w) {
        working = w > 0;
        markDirtyClient();
    }

    public ItemStack getDust() {
        return dust;
    }

    public void setDust(ItemStack dust) {
        this.dust = dust;
        markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("item")) {
            chargingItem = new ItemStack(compound.getCompoundTag("item"));
        } else {
            chargingItem = ItemStackTools.getEmptyStack();
        }
        if (compound.hasKey("dust")) {
            dust = new ItemStack(compound.getCompoundTag("dust"));
        } else {
            dust = ItemStackTools.getEmptyStack();
        }

        working = compound.getBoolean("working");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("working", working);
        if (ItemStackTools.isValid(chargingItem)) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            chargingItem.writeToNBT(tagCompound);
            compound.setTag("item", tagCompound);
        }
        if (ItemStackTools.isValid(dust)) {
            NBTTagCompound tagCompound = new NBTTagCompound();
            dust.writeToNBT(tagCompound);
            compound.setTag("dust", tagCompound);
        }
        return super.writeToNBT(compound);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();
        return new AxisAlignedBB(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                // @todo, more general item handler
                return (T) new DustItemHandler(this);
            } else if (facing == EnumFacing.DOWN) {
                return (T) new ChargeItemHandler(this);
            } else {
                return (T) new DustItemHandler(this);
            }
        }
        return super.getCapability(capability, facing);
    }

}
