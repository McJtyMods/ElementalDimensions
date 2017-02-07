package bitmovers.elementaldimensions.blocks.portal;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.dimensions.Dimensions;
import elec332.core.world.WorldHelper;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
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
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import javax.annotation.Nonnull;
import java.util.Random;

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
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
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
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        super.addProbeInfo(mode, probeInfo, player, world, blockState, data);
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof PortalDialerTileEntity) {
            PortalDialerTileEntity dialer = (PortalDialerTileEntity) te;
            Dimensions destination = dialer.getDestination();
            if (destination != null) {
                probeInfo.text(TextFormatting.GREEN + "Dialed too " + TextFormatting.WHITE + destination.getName());
            }
        }
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
    public boolean onBlockActivatedC(World world, BlockPos pos, EntityPlayer player, EnumHand hand, IBlockState state, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && hand == EnumHand.MAIN_HAND) {
            TileEntity te = WorldHelper.getTileAt(world, pos);
            if (te instanceof PortalDialerTileEntity) {
                ((PortalDialerTileEntity) te).onActivated(player);
            }
        }
        return true;
    }

}
