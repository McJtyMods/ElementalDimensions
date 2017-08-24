package bitmovers.elementaldimensions.blocks.altar;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

class ChargeItemHandler implements IItemHandler {

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
        if (!altar.getChargingItem().isEmpty()) {
            // No room, there is already an item
            return stack;
        }
        if (!simulate) {
            altar.setChargingItem(stack);
        }

        return ItemStack.EMPTY;
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
        return 1;
    }
}
