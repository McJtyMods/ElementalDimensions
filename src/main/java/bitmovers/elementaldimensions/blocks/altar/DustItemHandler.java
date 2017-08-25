package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.init.ItemRegister;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

class DustItemHandler implements IItemHandler {

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
        if (stack.isEmpty() || stack.getItem() != ItemRegister.elementalDustItem) {
            return stack;
        }

        int amount = altar.getDust().getCount();
        int stacksize = stack.getCount();
        int remaining = 0;
        int newamount = amount + stacksize;
        if (newamount > 64) {
            remaining = newamount - 64;
            newamount = 64;
        }

        if (!simulate) {
            altar.setDust(new ItemStack(ItemRegister.elementalDustItem, newamount));
            altar.markDirty();
        }

        if (remaining == 0) {
            return ItemStack.EMPTY;
        } else {
            return new ItemStack(ItemRegister.elementalDustItem, remaining);
        }
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!altar.getChargingItem().isEmpty()) {
            amount = Math.min(amount, altar.getChargingItem().getCount());
            ItemStack stack;
            if (simulate) {
                ItemStack stack1 = altar.getChargingItem().copy();
                ItemStack result;
                if (amount <= 0) {
                    stack1.setCount(0);
                    result = ItemStack.EMPTY;
                } else {
                    stack1.setCount(amount);
                    result = stack1;
                }
                stack = result;
            } else {
                stack = altar.getChargingItem().splitStack(amount);
                altar.markDirtyClient();
            }
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
}
