package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.init.ItemRegister;
import mcjty.lib.compat.CompatItemHandler;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

class DustItemHandler implements CompatItemHandler {

    private AltarCenterTileEntity altar;

    public DustItemHandler(AltarCenterTileEntity altar) {
        this.altar = altar;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        return altar.getDust();
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (ItemStackTools.isEmpty(stack) || stack.getItem() != ItemRegister.elementalDustItem) {
            return stack;
        }

        int amount = ItemStackTools.isValid(altar.getDust()) ? ItemStackTools.getStackSize(altar.getDust()) : 0;
        int stacksize = ItemStackTools.getStackSize(stack);
        int remaining = 0;
        int newamount = amount + stacksize;
        if (newamount > 64) {
            remaining = stacksize - (newamount - 64);
            newamount = 64;
        }

        if (!simulate) {
            altar.setDust(new ItemStack(ItemRegister.elementalDustItem, newamount));
            altar.markDirty();
        }

        if (remaining == 0) {
            return ItemStackTools.getEmptyStack();
        } else {
            return new ItemStack(ItemRegister.elementalDustItem, remaining);
        }
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
        return 64;
    }
}
