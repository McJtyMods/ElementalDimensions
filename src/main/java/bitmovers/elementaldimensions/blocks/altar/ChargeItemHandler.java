package bitmovers.elementaldimensions.blocks.altar;

import mcjty.lib.compat.CompatItemHandler;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

class ChargeItemHandler implements CompatItemHandler {

    private AltarCenterTileEntity altar;

    public ChargeItemHandler(AltarCenterTileEntity altar) {
        this.altar = altar;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return altar.getChargingItem();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (ItemStackTools.isValid(altar.getChargingItem())) {
            // No room, there is already an item
            return stack;
        }
        if (!simulate) {
            altar.setChargingItem(stack);
        }

        return ItemStackTools.getEmptyStack();
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (ItemStackTools.isValid(altar.getChargingItem())) {
            amount = Math.min(amount, ItemStackTools.getStackSize(altar.getChargingItem()));
            ItemStack stack;
            if (simulate) {
                stack = ItemStackTools.setStackSize(altar.getChargingItem().copy(), amount);
            } else {
                stack = altar.getChargingItem().splitStack(amount);
                altar.markDirtyClient();
            }
            return stack;
        } else {
            return ItemStackTools.getEmptyStack();
        }
    }

    @Override
    public int getSlotMaxLimit() {
        return 1;
    }
}
