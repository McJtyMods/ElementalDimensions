package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.blocks.GenericTileEntity;
import bitmovers.elementaldimensions.dimensions.Dimensions;
import bitmovers.elementaldimensions.dimensions.PortalDungeonLocator;
import bitmovers.elementaldimensions.items.ItemRune;
import bitmovers.elementaldimensions.mobs.EntityGuard;
import bitmovers.elementaldimensions.util.Config;
import bitmovers.elementaldimensions.util.CustomTeleporter;
import elec332.core.api.annotations.RegisterTile;
import elec332.core.util.DirectionHelper;
import elec332.core.util.PlayerHelper;
import elec332.core.world.WorldHelper;
import mcjty.lib.tools.WorldTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static bitmovers.elementaldimensions.init.ItemRegister.*;

@RegisterTile(name = ElementalDimensions.MODID + "_portaldialer")
public class PortalDialerTileEntity extends GenericTileEntity implements ITickable {

    public PortalDialerTileEntity(){
        destination = Dimensions.OVERWORLD;
    }

    private Dimensions destination;
    private int counter;
    private boolean hasBeenUsed;
    private static Random random = new Random();
    private int guardCounter = 0;
    private int cooldown = 0;

    public Dimensions getDestination() {
        return destination;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        markDirty();
    }

    public BlockPos getTeleportSpot() {
        EnumFacing facing = getWorld().getBlockState(pos).getValue(PortalDialerBlock.FACING_HORIZ);
        return pos.offset(EnumFacing.UP).offset(facing);
    }

    @Override
    public void update() {
        if (!getWorld().isRemote) {
            counter--;

            int currentDim = WorldHelper.getDimID(getWorld());
            if (counter % 50 == 0 && ((destination == null && currentDim != 0) || (destination != null && currentDim != destination.getDimensionID()))) {
                if (cooldown > 0) {
                    cooldown--;
                    markDirty();
                } else {
                    EnumFacing facing = getFacing();
                    BlockPos p = pos.offset(EnumFacing.UP);
                    AxisAlignedBB tpAABB = new AxisAlignedBB(p.offset(DirectionHelper.rotateLeft(facing)), new BlockPos(p.getX() + 1, p.getY() + 3, p.getZ() + 1).offset(DirectionHelper.rotateRight(facing)));
                    List<EntityPlayer> players = getWorld().getEntitiesWithinAABB(EntityPlayer.class, tpAABB);
                    if (!players.isEmpty()) {
                        if (!hasBeenUsed) {
                            hasBeenUsed = true;
                        }
                        PortalDialerTileEntity dest = PortalDungeonLocator.getTeleporter((WorldServer) getWorld(), pos, destination);
                        if (dest != null) {
                            dest.setCooldown(3);    // Some cooldown
                            for (EntityPlayer player : players) {
                                CustomTeleporter.teleportToDimension(player, WorldHelper.getDimID(dest.getWorld()), dest.getTeleportSpot());
                                if (destination != null) {
                                    String[] tasks = destination.getTaskDescriptions();
                                    if (tasks != null) {
                                        PlayerHelper.sendMessageToPlayer(player, TextFormatting.GREEN + "Mortal, here are your tasks:");
                                        for (String task : tasks) {
                                            PlayerHelper.sendMessageToPlayer(player, task);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (counter <= 0) {
                counter = 400 + getWorld().rand.nextInt(200);
                if (!hasBeenUsed) {
                    if (guardCounter >= Config.Mobs.totalMaxGuards) {
                        // We can't spawn any more guards
                        return;
                    }

                    EntityPlayer closestPlayer = getWorld().getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 20, true);
                    if (closestPlayer != null) {
                        // Don't spawn if there are no players nearby
                        return;
                    }

                    List<EntityGuard> guards = getWorld().getEntitiesWithinAABB(EntityGuard.class, new AxisAlignedBB(getPos()).expand(7, 7, 7));
                    if (guards.size() >= Config.Mobs.maxGuards) {
                        return;
                    }

                    BlockPos spawnPos = new BlockPos(getPos().getX() + random.nextInt(5) - 2, getPos().getY(), getPos().getZ() + random.nextInt(5) - 2);
                    if (getWorld().getEntitiesWithinAABB(EntityGuard.class, new AxisAlignedBB(spawnPos).expand(1, 1, 1)).size() == 0) {
                        if (getWorld().isAirBlock(spawnPos) && getWorld().isAirBlock(spawnPos.up(2))) {
                            EntityGuard guard = new EntityGuard(getWorld());
                            guard.setPosition(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                            WorldTools.spawnEntity(getWorld(), guard);
                            guardCounter++;
                            markDirty();
                        }
                    }
                }
            }
        }
    }

    public EnumFacing getFacing(){
        return WorldHelper.getBlockState(getWorld(), pos).getValue(PortalDialerBlock.FACING_HORIZ);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        Dimensions old = destination;
        super.onDataPacket(net, packet);
        if (old != destination) {
            getWorld().markBlockRangeForRenderUpdate(getPos(), getPos());
        }
    }

    public void onActivated(EntityPlayer player){
        ItemStack stack = player.getHeldItemMainhand();
        if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof ItemRune){
                if (getWorld().provider.getDimension() != 0) {
                    PlayerHelper.sendMessageToPlayer(player, TextFormatting.RED + "This portal can only go to the overworld!");
                    return;
                }
                Dimensions dim = ((ItemRune) item).getDimension(stack);
                if (destination == dim){
                    return;
                }
                dropDest(player);
                player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                this.destination = dim;
                markDirtyClient();
            }
        } else {
            dropDest(player);
        }
    }

    @SuppressWarnings("all")
    private void dropDest(EntityPlayer player){
        if (destination != null){
            ItemStack s3 = new ItemStack(getItem(destination));
            if (!player.inventory.addItemStackToInventory(s3)){
                player.dropItem(s3, true);
            }
            destination = null;
            markDirtyClient();
        }
    }

    @Nullable
    private Item getItem(Dimensions destination) {
        switch (destination) {
            case EARTH:
                return runeOfEarth;
            case WATER:
                return runeOfWater;
            case AIR:
                return runeOfAir;
            case SPIRIT:
                return runeOfSpirit;
            case FIRE:
                return runeOfFire;
            default:
                return null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        destination = Dimensions.findLevel(compound.getByte("dest"));
        if (destination == null) {
            destination = Dimensions.OVERWORLD;
        }
        guardCounter = compound.getInteger("guards");
        cooldown = compound.getInteger("cooldown");
        hasBeenUsed = compound.getBoolean("used");
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setByte("dest", destination == null ? -1 : destination.getLevel());
        compound.setInteger("guards", guardCounter);
        compound.setInteger("cooldown", cooldown);
        compound.setBoolean("used", hasBeenUsed);
        return compound;
    }

    @Override
    protected void writeClientDataToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("dest", destination == null ? -1 : destination.getLevel());
    }

    @Override
    protected void readClientDataFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        destination = Dimensions.findLevel(nbt.getByte("dest"));
        if (destination == null) {
            destination = Dimensions.OVERWORLD;
        }
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
        return new AxisAlignedBB(xCoord - 2, yCoord, zCoord - 2, xCoord + 3, yCoord + 3, zCoord + 3);
    }

}
