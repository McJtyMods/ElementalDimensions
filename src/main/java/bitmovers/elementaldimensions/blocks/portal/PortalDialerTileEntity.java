package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.blocks.GenericTileEntity;
import bitmovers.elementaldimensions.mobs.EntityGuard;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.worldgen.WorldGenHelper;
import elec332.core.api.annotations.RegisterTile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

import static bitmovers.elementaldimensions.init.ItemRegister.*;

@RegisterTile(name = ElementalDimensions.MODID + "_portaldialer")
public class PortalDialerTileEntity extends GenericTileEntity implements ITickable {

    private PortialDestination destination = PortialDestination.EARTH;

    public PortialDestination getDestination() {
        return destination;
    }


    private int counter = 0;

    private static Random random = new Random();

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            counter--;
            if (counter <= 0) {
                counter = 500;
                List<EntityGuard> guards = worldObj.getEntitiesWithinAABB(EntityGuard.class, new AxisAlignedBB(getPos()).expand(7, 7, 7));
                if (guards.size() >= Config.Mobs.maxGuards) {
                    return;
                }

                BlockPos spawnPos = new BlockPos(getPos().getX() + random.nextInt(7)-3, getPos().getY(), getPos().getZ() + random.nextInt(7)-3);
                if (worldObj.getEntitiesWithinAABB(EntityGuard.class, new AxisAlignedBB(spawnPos).expand(1, 1, 1)).size() == 0) {
                    EntityGuard guard = new EntityGuard(worldObj);
                    guard.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                    worldObj.spawnEntityInWorld(guard);
                }
            }
        }
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
