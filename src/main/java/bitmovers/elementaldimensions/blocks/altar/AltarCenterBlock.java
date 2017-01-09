package bitmovers.elementaldimensions.blocks.altar;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.blocks.GenericBlock;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nonnull;

public class AltarCenterBlock extends GenericBlock implements ITileEntityProvider {

    public static final PropertyBool WORKING = PropertyBool.create("working");

    public AltarCenterBlock() {
        super("altarcenter", Material.ROCK);
        setHardness(3.0f);
        setResistance(5.0f);
        setSoundType(SoundType.STONE);
        GameRegistry.registerTileEntity(AltarCenterTileEntity.class, ElementalDimensions.MODID + "_altarcenter");
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new AltarCenterTileEntity();
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @Override
    protected void neighborChanged(World world, BlockPos pos, IBlockState state, Block neighbor, BlockPos p_189540_5_) {
        checkRedstone(world, pos);
    }

    protected void checkRedstone(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof AltarCenterTileEntity) {
            int powered = world.isBlockIndirectlyGettingPowered(pos); //TODO: check
            AltarCenterTileEntity altar = (AltarCenterTileEntity) te;
            altar.setWorking(powered);
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        boolean working = false;
        if (te instanceof AltarCenterTileEntity) {
            working = ((AltarCenterTileEntity)te).isWorking();
        }
        return state.withProperty(WORKING, working);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, WORKING);
    }

    private AltarCenterTileEntity getTE(World world, BlockPos pos) {
        return (AltarCenterTileEntity) world.getTileEntity(pos);
    }

    @Override
    protected boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, EnumHand hand, IBlockState state, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            AltarCenterTileEntity te = getTE(world, pos);
            if (ItemStackTools.isEmpty(te.getStack())) {
                if (ItemStackTools.isValid(player.getHeldItem(hand))) {
                    // There is no item in the pedestal and the player is holding an item. We move that item
                    // to the pedestal
                    te.setStack(player.getHeldItem(hand));
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStackTools.getEmptyStack());
                    // Make sure the client knows about the changes in the player inventory
                    player.openContainer.detectAndSendChanges();
                }
            } else {
                // There is a stack in the pedestal. In this case we remove it and try to put it in the
                // players inventory if there is room
                ItemStack stack = te.getStack();
                te.setStack(ItemStackTools.getEmptyStack());
                if (!player.inventory.addItemStackToInventory(stack)) {
                    // Not possible. Throw item in the world
                    EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY()+1, pos.getZ(), stack);
                    world.spawnEntity(entityItem);
                } else {
                    player.openContainer.detectAndSendChanges();
                }
            }
        }

        // Return true also on the client to make sure that MC knows we handled this and will not try to place
        // a block on the client
        return true;
    }
}
