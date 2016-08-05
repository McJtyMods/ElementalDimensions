package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class PortalDialerBlock extends GenericBlock implements ITileEntityProvider {

    public static final PropertyDirection FACING_HORIZ = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static PropertyEnum<PortialDestination> DESTINATION = PropertyEnum.create("destination", PortialDestination.class);

    public PortalDialerBlock() {
        super("portaldialer", Material.ROCK);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new PortalDialerTileEntity();
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof PortalDialerTileEntity) {
            PortalDialerTileEntity dialer = (PortalDialerTileEntity) te;
            return state.withProperty(DESTINATION, dialer.getDestination());
        } else {
            return state.withProperty(DESTINATION, PortialDestination.EARTH);
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING_HORIZ, DESTINATION);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING_HORIZ, EnumFacing.values()[meta + 2]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING_HORIZ).getIndex()-2;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING_HORIZ, placer.getHorizontalFacing().getOpposite()), 2);
    }



    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof PortalDialerTileEntity) {
                ItemStack stack = playerIn.getHeldItemMainhand();
                PortalDialerTileEntity dialer = (PortalDialerTileEntity) te;
                ItemStack old = dialer.undial();
                if (old != null) {
                    if (!playerIn.inventory.addItemStackToInventory(old)) {
                        playerIn.dropItem(old, true);
                    }
                }
                if (dialer.dial(stack)) {
                    playerIn.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
                    playerIn.openContainer.detectAndSendChanges();
                }
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
    }

}
