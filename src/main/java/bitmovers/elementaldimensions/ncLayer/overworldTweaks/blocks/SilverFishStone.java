package bitmovers.elementaldimensions.ncLayer.overworldTweaks.blocks;

import bitmovers.elementaldimensions.ncLayer.overworldTweaks.client.IOWTBlock;
import elec332.core.world.WorldHelper;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class SilverFishStone extends BlockStone implements IOWTBlock {

    public SilverFishStone() {
        super();
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, float chance, int fortune) {
        EntitySilverfish entitysilverfish = new EntitySilverfish(worldIn);
        entitysilverfish.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
        WorldHelper.spawnEntityInWorld(worldIn, entitysilverfish);
        entitysilverfish.spawnExplosionParticle();
    }

}
