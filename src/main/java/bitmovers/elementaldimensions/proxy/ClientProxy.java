package bitmovers.elementaldimensions.proxy;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.*;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }


    private void generateCloud(Set<BlockPos> poses, BlockPos center, float weight, Random seed) {
        if (poses.contains(center)) {
            return;
        }
        poses.add(center);

        if (weight > 0) {
            List<EnumFacing> directions = new ArrayList<>();
            Collections.addAll(directions, EnumFacing.HORIZONTALS);
            while (!directions.isEmpty()) {
                int i = seed.nextInt(directions.size());
                EnumFacing dir = directions.remove(i);
                generateCloud(poses, center.offset(dir), weight - seed.nextFloat() - 1, seed);
            }
        }
    }
    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
