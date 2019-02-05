package bitmovers.elementaldimensions.ncLayer.dev;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.items.AbstractItem;
import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.util.DateHelper;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import bitmovers.elementaldimensions.varia.Broadcaster;
import com.google.common.base.Preconditions;
import elec332.core.api.util.Area;
import elec332.core.util.IOHelper;
import elec332.core.util.NBTBuilder;
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
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }
            NBTBuilder nbt = NBTBuilder.from(Preconditions.checkNotNull(stack.getTagCompound()));
            if (player.isSneaking()) {
                nbt.setBlockPos("pos1", pos);

                Broadcaster.message(player, "Position 1: "+pos);
                Broadcaster.message(player, "Position 2: "+nbt.getBlockPos("pos2"));
            } else {
                nbt.setBlockPos("pos2", pos);

                Broadcaster.message(player, "Position 1: "+nbt.getBlockPos("pos1"));
                Broadcaster.message(player, "Position 2: "+pos);
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote && stack.hasTagCompound()) {
            String msg = "Created new schematic";
            NBTBuilder nbt = NBTBuilder.from(Preconditions.checkNotNull(stack.getTagCompound()));
            BlockPos pos1 = nbt.getBlockPos("pos1"), pos2 = nbt.getBlockPos("pos2");
            if (pos1.equals(BlockPos.ORIGIN) || pos2.equals(BlockPos.ORIGIN)){
                return new ActionResult<>(EnumActionResult.SUCCESS, stack);
            }
            Area area = new Area(pos1, pos2);
            NBTTagCompound s = SchematicHelper.INSTANCE.writeSchematic(SchematicHelper.INSTANCE.createModSchematic(world, area, (short) 0));
            File folder = new File(NCLayerMain.mcDir, "schematics");
            File file = new File(folder, "Schematic-"+ DateHelper.getDateAsNormalString()+".schematic");
            try {
                IOHelper.createFile(file);
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
