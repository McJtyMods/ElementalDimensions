package bitmovers.elementaldimensions.ncLayer.dev;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.items.AbstractItem;
import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.util.DateHelper;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.varia.Broadcaster;
import elec332.core.api.util.Area;
import elec332.core.util.IOUtil;
import elec332.core.util.NBTHelper;
import elec332.core.world.schematic.SchematicHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ItemSchematicCreator extends AbstractItem {

    public ItemSchematicCreator() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ElementalDimensions.creativeTab);
        this.setUnlocalizedName(ElementalDimensions.MODID + ".schematicCreator");
        this.setRegistryName(new EDResourceLocation("schematicCreator_DEV"));
    }

    @Override
    protected ResourceLocation[] getTextureLocations() {
        return new ResourceLocation[]{
                new EDResourceLocation("items/sci")
        };
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUseC(EntityPlayer player, EnumHand hand, World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }
            NBTHelper nbt = new NBTHelper(stack.getTagCompound());
            if (player.isSneaking()) {
                nbt.addToTag(pos, "pos1");

                Broadcaster.message(player, "Position 1: "+pos);
                Broadcaster.message(player, "Position 2: "+nbt.getPos("pos2"));
            } else {
                nbt.addToTag(pos, "pos2");

                Broadcaster.message(player, "Position 1: "+nbt.getPos("pos1"));
                Broadcaster.message(player, "Position 2: "+pos);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClickC(EntityPlayer player, @Nonnull EnumHand hand, World world) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && stack.hasTagCompound()) {
            String msg = "Created new schematic";
            NBTHelper nbt = new NBTHelper(stack.getTagCompound());
            BlockPos pos1 = nbt.getPos("pos1"), pos2 = nbt.getPos("pos2");
            if (pos1.equals(BlockPos.ORIGIN) || pos2.equals(BlockPos.ORIGIN)){
                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }
            Area area = new Area(pos1, pos2);
            NBTTagCompound s = SchematicHelper.INSTANCE.writeSchematic(SchematicHelper.INSTANCE.createModSchematic(world, area, (short) 0));
            File folder = new File(NCLayerMain.mcDir, "schematics");
            File file = new File(folder, "Schematic-"+ DateHelper.getDateAsNormalString()+".schematic");
            try {
                IOUtil.createFile(file);
                writeOut(s, file);
                stack.setTagCompound(new NBTTagCompound());
            } catch (Exception e){
                msg = "Failed to write schematic";
            }
            Broadcaster.message(player, msg);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SuppressWarnings("all")
    private void writeOut(NBTTagCompound t, File file) throws IOException {
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
        try {
            CompressedStreamTools.writeCompressed(t, dataoutputstream);
        } finally {
            dataoutputstream.close();
        }
    }

}
