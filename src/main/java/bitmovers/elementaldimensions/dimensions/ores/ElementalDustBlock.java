package bitmovers.elementaldimensions.dimensions.ores;

import bitmovers.elementaldimensions.blocks.GenericBlock;
import bitmovers.elementaldimensions.init.ItemRegister;
import elec332.core.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ElementalDustBlock extends GenericBlock {

    public static enum OreType implements IStringSerializable {
        ORE_DIRT("dirt"),
        ORE_STONE("stone"),
        ORE_GLASS("glass");

        private final String name;

        OreType(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }

    public static final PropertyEnum<OreType> ORETYPE = PropertyEnum.create("oretype", OreType.class);

    public ElementalDustBlock() {
        super("elementaldust_ore", Material.ROCK, ElementalDustItemBlock.class);
        setHardness(3.0f);
        setResistance(5.0f);
        setHarvestLevel("pickaxe", 2);
    }

    @SideOnly(Side.CLIENT)
    public void initClient() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "oretype=dirt"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 1, new ModelResourceLocation(getRegistryName(), "oretype=stone"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 2, new ModelResourceLocation(getRegistryName(), "oretype=glass"));
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
//        if (world.isRemote) {
//            for (int i = 0 ; i < 10 ; i++) {
//                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, rand.nextGaussian() / 3.0f, rand.nextGaussian() / 3.0f, rand.nextGaussian() / 3.0f);
//            }
//        }
    }

    @Override
    protected void getSubBlocks(@Nonnull Item item, List<ItemStack> list, CreativeTabs creativeTab) {
        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemRegister.elementalDustItem;
    }

    @Override
    public int quantityDropped(Random random) {
        return 2 + random.nextInt(3);
    }

    @Override
    public int quantityDroppedWithBonus(int bonus, Random random) {
        int j = random.nextInt(bonus + 2) - 1;
        if (j < 0) {
            j = 0;
        }

        return this.quantityDropped(random) * (j + 1);
    }

    private Random rand = new Random();

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        return rand.nextInt(7-3 + 1) + 3;
    }

//    @Override
//    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
//        super.onBlockHarvested(worldIn, pos, state, player);
//        if (player != null) {
//            Achievements.trigger(player, Achievements.specialOres);
//        }
//    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(ORETYPE).ordinal();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ORETYPE, OreType.values()[meta]);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ORETYPE);
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        if (state.getValue(ORETYPE) == OreType.ORE_GLASS) {
            return layer == BlockRenderLayer.TRANSLUCENT;
        } else {
            return layer == BlockRenderLayer.SOLID;
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return state.getValue(ORETYPE) != OreType.ORE_GLASS;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (state.getValue(ORETYPE) == OreType.ORE_GLASS) {
            Block block = WorldHelper.getBlockAt(world, pos.offset(side));
            return block != this && super.shouldSideBeRendered(state, world, pos, side);
        } else {
            return super.shouldSideBeRendered(state, world, pos, side);
        }
    }

}
