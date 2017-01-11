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
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AltarCenterTileEntity extends GenericTileEntity implements ITickable {

    public static final int MAXAGE = 30;

    private boolean working = false;
    private boolean spawnNewParticles = false;

    // Client side only
    private List<Particle> particles = new ArrayList<>();
    private int particleTimeout = 10;

    private ItemStack chargingItem = ItemStackTools.getEmptyStack();
    private ItemStack dust = ItemStackTools.getEmptyStack();

    private Random random = new Random();

    private Vec3d randVec() {
        float dx = random.nextFloat() - .5f;
        float dy = random.nextFloat() - .5f;
        float dz = random.nextFloat() - .5f;
        if (Math.abs(dx) < 0.1f && Math.abs(dy) < 0.1f && Math.abs(dz) < 0.1f) {
            dx = .3f;
        }
        return new Vec3d(dx, dy, dz).normalize();
    }

    @Override
    public void update() {
        if (working) {
            if (getWorld().isRemote) {
                // Client side animation if needed
                particleTimeout--;
                if (particleTimeout <= 0) {
                    particleTimeout = 10;
                    // Clean up old particles
                    if (!particles.isEmpty()) {
                        List<Particle> newparticles = new ArrayList<>();
                        for (Particle particle : particles) {
                            if (particle.getAge() > 0) {
                                newparticles.add(particle);
                            }
                        }
                        particles = newparticles;
                    }
                    if (spawnNewParticles) {
                        // If this is true we can spawn new particles
                        particles.add(new Particle(randVec().scale(1.2), .1f, 128, MAXAGE));
                        particles.add(new Particle(randVec().scale(1.2), .1f, 128, MAXAGE));
                        particles.add(new Particle(randVec().scale(1.2), .1f, 128, MAXAGE));
                        particles.add(new Particle(randVec().scale(1.2), .1f, 128, MAXAGE));
                        particles.add(new Particle(randVec().scale(1.2), .1f, 128, MAXAGE));
                        particles.add(new Particle(randVec().scale(1.2), .1f, 128, MAXAGE));
                    }
                }
                for (Particle particle : particles) {
                    particle.older();
                    particle.setD(particle.getD().scale(.8));
                    particle.setAlpha(128 - 4*(MAXAGE - particle.getAge()));
                }

            } else {
                boolean spawn = false;
                if (ItemStackTools.isValid(chargingItem) && ItemStackTools.isValid(dust) && chargingItem.getItem() == ItemRegister.elementalWand) {
                    int dustLevel = ItemElementalWand.getDustLevel(chargingItem);
                    if (dustLevel < Config.Wand.maxDust) {
                        dust.splitStack(1);
                        ItemElementalWand.setDustLevel(chargingItem, dustLevel + 1);
                        spawn = true;
                    }
                }
                setSpawnNewParticles(spawn);
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
//        boolean working = isWorking();

        super.onDataPacket(net, packet);

//        if (getWorld().isRemote) {
//            // If needed send a render update.
//            boolean newWorking = isWorking();
//            if (newWorking != working) {
//                getWorld().markBlockRangeForRenderUpdate(getPos(), getPos());
//            }
//        }
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(int w) {
        if (working == w > 0) {
            return;
        }
        working = w > 0;
        markDirtyClient();
    }

    public boolean isCharging() {
        if (!working) {
            return false;
        }
        if (ItemStackTools.isValid(chargingItem) && ItemStackTools.isValid(dust) && chargingItem.getItem() == ItemRegister.elementalWand) {
            int dustLevel = ItemElementalWand.getDustLevel(chargingItem);
            if (dustLevel < Config.Wand.maxDust) {
                return true;
            }
        }
        return false;
    }

    public boolean isSpawnNewParticles() {
        return spawnNewParticles;
    }

    public void setSpawnNewParticles(boolean spawnNewParticles) {
        if (this.spawnNewParticles == spawnNewParticles) {
            return;
        }
        this.spawnNewParticles = spawnNewParticles;
        markDirtyClient();
    }

    public List<Particle> getParticles() {
        return particles;
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
            chargingItem = ItemStackTools.loadFromNBT(compound.getCompoundTag("item"));
        } else {
            chargingItem = ItemStackTools.getEmptyStack();
        }
        if (compound.hasKey("dust")) {
            dust = ItemStackTools.loadFromNBT(compound.getCompoundTag("item"));
        } else {
            dust = ItemStackTools.getEmptyStack();
        }

        working = compound.getBoolean("working");
        spawnNewParticles = compound.getBoolean("spawnnew");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("working", working);
        compound.setBoolean("spawnnew", spawnNewParticles);
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
