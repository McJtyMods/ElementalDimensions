package bitmovers.elementaldimensions.blocks.cosmetic;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import elec332.core.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SolidWaterBlock extends GenericBlock {

    public SolidWaterBlock() {
        super("solidwater", Material.GLASS);
        setSoundType(SoundType.GLASS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        Block block = WorldHelper.getBlockAt(blockAccess, pos.offset(side));
        return block != this && super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

}
