package bitmovers.elementaldimensions.varia;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DelayedRegister {

    private static final List<MBlock> blocks = new ArrayList<>();
    private static final List<Item> items = new ArrayList<>();

    public static IGenericRegistry getRegistry() {
        return new IGenericRegistry() {
            @Override
            public void registerLater(Block block, @Nullable Class<? extends ItemBlock> itemBlockClass, @Nullable Class<? extends TileEntity> tileEntityClass) {
                DelayedRegister.registerLater(block, itemBlockClass, tileEntityClass);
            }

            @Override
            public void registerLater(Item item) {
                DelayedRegister.registerLater(item);
            }
        };
    }

    public static void registerLater(Block block, @Nullable Class<? extends ItemBlock> itemBlockClass, @Nullable Class<? extends TileEntity> tileEntityClass) {
        blocks.add(new MBlock(block, itemBlockClass, tileEntityClass));
    }

    public static void registerLater(Item item) {
        items.add(item);
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        for (MBlock mBlock : blocks) {
            registry.register(mBlock.getBlock());
            if (mBlock.getTileEntityClass() != null) {
                GameRegistry.registerTileEntity(mBlock.getTileEntityClass(), ElementalDimensions.MODID + "_" + mBlock.getBlock().getRegistryName().getResourcePath());
            }
        }
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        for (Item item : items) {
            registry.register(item);
        }
        for (MBlock mBlock : blocks) {
            if (mBlock.getItemBlockClass() != null) {
                ItemBlock itemBlock = createItemBlock(mBlock.getBlock(), mBlock.getItemBlockClass());
                itemBlock.setRegistryName(mBlock.getBlock().getRegistryName());
                registry.register(itemBlock);
            }
        }
    }

    public static Stream<Item> getItems() {
        return items.stream();
    }

    private static ItemBlock createItemBlock(Block block, Class<? extends ItemBlock> itemBlockClass) {
        try {
            Class<?>[] ctorArgClasses = new Class<?>[1];
            ctorArgClasses[0] = Block.class;
            Constructor<? extends ItemBlock> itemCtor = itemBlockClass.getConstructor(ctorArgClasses);
            return itemCtor.newInstance(block);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    private static class MBlock {
        private final Block block;
        private final Class<? extends ItemBlock> itemBlockClass;
        private final Class<? extends TileEntity> tileEntityClass;

        public MBlock(Block block, Class<? extends ItemBlock> itemBlockClass, Class<? extends TileEntity> tileEntityClass) {
            this.block = block;
            this.itemBlockClass = itemBlockClass;
            this.tileEntityClass = tileEntityClass;
        }

        public Block getBlock() {
            return block;
        }

        public Class<? extends ItemBlock> getItemBlockClass() {
            return itemBlockClass;
        }

        public Class<? extends TileEntity> getTileEntityClass() {
            return tileEntityClass;
        }
    }
}
