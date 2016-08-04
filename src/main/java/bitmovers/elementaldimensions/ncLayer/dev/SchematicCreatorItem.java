package bitmovers.elementaldimensions.ncLayer.dev;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.items.AbstractItem;
import bitmovers.elementaldimensions.ncLayer.NCLayerMain;
import bitmovers.elementaldimensions.util.DateHelper;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import elec332.core.util.IOUtil;
import elec332.core.util.NBTHelper;
import elec332.core.world.schematic.Area;
import elec332.core.world.schematic.SchematicHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class SchematicCreatorItem extends AbstractItem {

    public SchematicCreatorItem() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ElementalDimensions.creativeTab);
        this.setUnlocalizedName(ElementalDimensions.MODID + ".schematicCreator");
    }

    @Override
    protected ResourceLocation[] getTextureLocations() {
        return new ResourceLocation[]{
                new EDResourceLocation("items/sci")
        };
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!stack.hasTagCompound()) {
                stack.setTagCompound(new NBTTagCompound());
            }
            NBTHelper nbt = new NBTHelper(stack.getTagCompound());
            if (player.isSneaking()) {
                nbt.addToTag(pos, "pos1");

                player.addChatComponentMessage(new TextComponentString("Position 1: "+pos));
                player.addChatComponentMessage(new TextComponentString("Position 2: "+nbt.getPos("pos2")));
            } else {
                nbt.addToTag(pos, "pos2");

                player.addChatComponentMessage(new TextComponentString("Position 1: "+nbt.getPos("pos1")));
                player.addChatComponentMessage(new TextComponentString("Position 2: "+pos));
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote && stack.hasTagCompound()) {
            String msg = "Created new schematic";
            NBTHelper nbt = new NBTHelper(stack.getTagCompound());
            Area area = new Area(nbt.getPos("pos1"), nbt.getPos("pos2"));
            NBTTagCompound s = SchematicHelper.INSTANCE.writeSchematic(SchematicHelper.INSTANCE.createModSchematic(world, area, (short) 0));
            File folder = new File(NCLayerMain.mcDir, "schematics");
            File file = new File(folder, "Schematic-"+ DateHelper.getDateAsNormalString()+".schematic");
            try {
                IOUtil.createFile(file);
                IOUtil.NBT.write(file, s);
                stack.setTagCompound(new NBTTagCompound());
            } catch (Exception e){
                msg = "Failed to write schematic";
            }
            player.addChatComponentMessage(new TextComponentString(msg));
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

}
