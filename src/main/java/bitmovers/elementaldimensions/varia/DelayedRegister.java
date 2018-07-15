package bitmovers.elementaldimensions.varia;

import bitmovers.elementaldimensions.ElementalDimensions;
import mcjty.lib.datafix.fixes.TileEntityNamespace;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DelayedRegister { // @todo replace this whole class with McJtyRegister

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
        ModFixs modFixs = FMLCommonHandler.instance().getDataFixer().init(ElementalDimensions.MODID, 1);
        Map<String, String> oldToNewIdMap = new HashMap<>();
        for (MBlock mBlock : blocks) {
            registry.register(mBlock.getBlock());
            if (mBlock.getTileEntityClass() != null) {
                String oldPath = ElementalDimensions.MODID + "_" + mBlock.getBlock().getRegistryName().getResourcePath();
                String newId = mBlock.getBlock().getRegistryName().toString();
                GameRegistry.registerTileEntity(mBlock.getTileEntityClass(), newId);
                oldToNewIdMap.put(oldPath, newId);
                oldToNewIdMap.put("minecraft:" + oldPath, newId);
            }
        }

        // We used to accidentally register TEs with names like "minecraft:elementaldimensions_altarcenter" instead of "elementaldimensions:altarcenter".
        // Set up a DataFixer to map these incorrect names to the correct ones, so that we don't break old saved games.
        // @todo Remove all this if we ever break saved-game compatibility.
        oldToNewIdMap.put(ElementalDimensions.MODID + "_altarcenter", ElementalDimensions.MODID + ":altarcenter");
        oldToNewIdMap.put("minecraft:" + ElementalDimensions.MODID + "_altarcenter", ElementalDimensions.MODID + ":altarcenter");
        oldToNewIdMap.put(ElementalDimensions.MODID + "_portaldialer", ElementalDimensions.MODID + ":portaldialer");
        oldToNewIdMap.put("minecraft:" + ElementalDimensions.MODID + "_portaldialer", ElementalDimensions.MODID + ":portaldialer");
        modFixs.registerFix(FixTypes.BLOCK_ENTITY, new TileEntityNamespace(oldToNewIdMap, 1));
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
