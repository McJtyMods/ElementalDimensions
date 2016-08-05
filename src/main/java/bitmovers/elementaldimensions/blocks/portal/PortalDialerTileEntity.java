package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.blocks.GenericTileEntity;
import elec332.core.api.annotations.RegisterTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static bitmovers.elementaldimensions.init.ItemRegister.*;

@RegisterTile(name = ElementalDimensions.MODID + "_portaldialer")
public class PortalDialerTileEntity extends GenericTileEntity {

    private PortialDestination destination = PortialDestination.EARTH;

    public PortialDestination getDestination() {
        return destination;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        PortialDestination old = destination;
        super.onDataPacket(net, packet);
        if (old != destination) {
            worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
        }
    }

    /// Undial the portal (dial back to 'earth') and possibly return an itemstack with the previous rune if any
    @Nullable
    public ItemStack undial() {
        if (destination == PortialDestination.EARTH) {
            // Do nothing
            return null;
        }
        Item item = getItem(destination);

        destination = PortialDestination.EARTH;
        markDirtyClient();

        if (item == null) {
            return null;
        }
        return new ItemStack(item);
    }

    public boolean dial(@Nullable ItemStack rune) {
        if (rune == null) {
            return false;
        }
        if (rune.getItem() == getItem(destination)) {
            // Do nothing
            return false;
        }

        PortialDestination newdest = getDestination(rune.getItem());
        if (newdest == null) {
            return false;
        }
        this.destination = newdest;
        markDirtyClient();
        return true;
    }

    private Item getItem(PortialDestination destination) {
        switch (destination) {
            case EARTH:
                return null;
            case WATER:
                return runeOfWater;
            case AIR:
                return runeOfAir;
            case SPIRIT:
                return runeOfSpirit;
            case FIRE:
                return runeOfFire;
        }
        return null;
    }

    private PortialDestination getDestination(Item item) {
        if (item == runeOfWater) {
            return PortialDestination.WATER;
        } else if (item == runeOfAir) {
            return PortialDestination.AIR;
        } else if (item == runeOfSpirit) {
            return PortialDestination.SPIRIT;
        } else if (item == runeOfFire) {
            return PortialDestination.FIRE;
        } else {
            return null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        destination = PortialDestination.values()[compound.getByte("dest")];
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("dest", (byte) destination.ordinal());
        return compound;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        int xCoord = getPos().getX();
        int yCoord = getPos().getY();
        int zCoord = getPos().getZ();
        return new AxisAlignedBB(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3);
    }

}
