package bitmovers.elementaldimensions.client;

import bitmovers.elementaldimensions.ElementalDimensions;
import elec332.core.api.discovery.ASMDataProcessor;
import elec332.core.api.discovery.IASMDataHelper;
import elec332.core.api.discovery.IASMDataProcessor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.objectweb.asm.Type;

/**
 * Created by Elec332 on 5-8-2016.
 */
@ASMDataProcessor(LoaderState.INITIALIZATION)
public class TESRHandler implements IASMDataProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public void processASMData(IASMDataHelper iasmDataHelper, LoaderState loaderState) {
        for (ASMDataTable.ASMData asmData : iasmDataHelper.getAnnotationList(RegisteredTESR.class)){
            try {
                Class<?> tesrClazz = Class.forName(asmData.getClassName());
                Class<?> tileClazz = Class.forName(((Type)asmData.getAnnotationInfo().get("value")).getClassName());
                Object o = tesrClazz.newInstance();
                if (TileEntity.class.isAssignableFrom(tileClazz) && o instanceof TileEntitySpecialRenderer){
                    ClientRegistry.bindTileEntitySpecialRenderer((Class<TileEntity>)tileClazz, (TileEntitySpecialRenderer)o);
                }
            } catch (Exception e){
                ElementalDimensions.setup.getLogger().error("Error registering TESR: "+asmData.getClassName());
            }
        }
    }

}
