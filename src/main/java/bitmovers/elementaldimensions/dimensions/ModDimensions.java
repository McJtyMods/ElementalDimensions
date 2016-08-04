package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.dimensions.providers.*;
import bitmovers.elementaldimensions.util.Config;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModDimensions {

    public static DimensionType earthDimensionType;
    public static DimensionType waterDimensionType;
    public static DimensionType airDimensionType;
    public static DimensionType spiritDimensionType;
    public static DimensionType fireDimensionType;

    public static void preInit() {
        registerDimensionTypes();
        registerDimensions();
        ElementalDimensions.registerLoginHandler("config|dimid", new INBTSerializable<NBTTagCompound>() {

            @Override
            public NBTTagCompound serializeNBT() {
                NBTTagCompound ret = new NBTTagCompound();
                ret.setInteger("e", Config.Dimensions.Earth.dimensionID);
                ret.setInteger("w", Config.Dimensions.Water.dimensionID);
                ret.setInteger("a", Config.Dimensions.Air.dimensionID);
                ret.setInteger("s", Config.Dimensions.Spirit.dimensionID);
                ret.setInteger("f", Config.Dimensions.Fire.dimensionID);
                return ret;
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt) {
                //Should ALWAYS be true, but it doesn't hurt to check...
                if (FMLCommonHandler.instance().getSide().isClient()){
                    if (Config.Dimensions.Earth.dimensionID != nbt.getInteger("e")){
                        disconnect();
                    }
                    if (Config.Dimensions.Water.dimensionID != nbt.getInteger("w")){
                        disconnect();
                    }
                    if (Config.Dimensions.Air.dimensionID != nbt.getInteger("a")){
                        disconnect();
                    }
                    if (Config.Dimensions.Spirit.dimensionID != nbt.getInteger("s")){
                        disconnect();
                    }
                    if (Config.Dimensions.Fire.dimensionID != nbt.getInteger("f")){
                        disconnect();
                    }
                }
            }

            @SideOnly(Side.CLIENT)
            private void disconnect(){
                FMLClientHandler.instance().getWorldClient().sendQuittingDisconnectingPacket();
            }

        });
    }

    private static void registerDimensions() {
        DimensionManager.registerDimension(Config.Dimensions.Earth.dimensionID, earthDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Water.dimensionID, waterDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Air.dimensionID, airDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Spirit.dimensionID, spiritDimensionType);
        DimensionManager.registerDimension(Config.Dimensions.Fire.dimensionID, fireDimensionType);
    }

    private static void registerDimensionTypes() {

        /* AFAIK, the ID should be the dimension ID, example: http://prntscr.com/c1lmfb , Example 2: http://prntscr.com/c1lne0
            This ID also gets saved to the save files, so it shouldn't be dynamic
            Uncomment to revert
        earthDimensionType = DimensionType.register(ElementalDimensions.MODID, "_earth", fetchDimensionTypeId(), EarthWorldProvider.class, false);
        waterDimensionType = DimensionType.register(ElementalDimensions.MODID, "_water", fetchDimensionTypeId(), WaterWorldProvider.class, false);
        airDimensionType = DimensionType.register(ElementalDimensions.MODID, "_air", fetchDimensionTypeId(), AirWorldProvider.class, false);
        spiritDimensionType = DimensionType.register(ElementalDimensions.MODID, "_spirit", fetchDimensionTypeId(), SpiritWorldProvider.class, false);
        fireDimensionType = DimensionType.register(ElementalDimensions.MODID, "_fire", fetchDimensionTypeId(), FireWorldProvider.class, false);
        */

        earthDimensionType = DimensionType.register(ElementalDimensions.MODID, "_earth", Config.Dimensions.Earth.dimensionID, EarthWorldProvider.class, false);
        waterDimensionType = DimensionType.register(ElementalDimensions.MODID, "_water", Config.Dimensions.Water.dimensionID, WaterWorldProvider.class, false);
        airDimensionType = DimensionType.register(ElementalDimensions.MODID, "_air", Config.Dimensions.Air.dimensionID, AirWorldProvider.class, false);
        spiritDimensionType = DimensionType.register(ElementalDimensions.MODID, "_spirit", Config.Dimensions.Spirit.dimensionID, SpiritWorldProvider.class, false);
        fireDimensionType = DimensionType.register(ElementalDimensions.MODID, "_fire", Config.Dimensions.Fire.dimensionID, FireWorldProvider.class, false);

    }

    private static int fetchDimensionTypeId() {
        int id = -1;

        for (DimensionType type : DimensionType.values()) {
            if (type.getId() > id) {
                id = type.getId();
            }
        }
        id++;
        return id;
    }

}
