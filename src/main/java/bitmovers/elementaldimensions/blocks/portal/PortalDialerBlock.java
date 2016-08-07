package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.dimensions.Dimensions;
import com.google.common.collect.Sets;
import elec332.core.world.WorldHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PortalDialerBlock extends GenericBlock implements ITileEntityProvider {

    public static final PropertyDirection FACING_HORIZ = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyEnum<Dimensions> DESTINATION = PropertyEnum.create("destination", Dimensions.class);

    public PortalDialerBlock() {
        super("portaldialer", Material.ROCK);
        setHardness(50.0F);
        setResistance(2000.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    @Nonnull
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new PortalDialerTileEntity();
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, @Nonnull BlockRenderLayer layer) {
        return layer == BlockRenderLayer.SOLID || layer == BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof PortalDialerTileEntity) {
            PortalDialerTileEntity dialer = (PortalDialerTileEntity) te;
            return state.withProperty(DESTINATION, dialer.getDestination());
        } else {
            return state.withProperty(DESTINATION, Dimensions.EARTH);
        }
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING_HORIZ, DESTINATION);
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
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
            TileEntity te = WorldHelper.getTileAt(worldIn, pos);
            if (te instanceof PortalDialerTileEntity) {
                ((PortalDialerTileEntity) te).onActivated(playerIn);
            }
        }
        return true;
    }

}
