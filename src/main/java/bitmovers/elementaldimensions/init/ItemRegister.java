package bitmovers.elementaldimensions.init;

import bitmovers.elementaldimensions.ncLayer.dev.SchematicCreatorItem;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import elec332.core.main.ElecCore;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class ItemRegister {

    public static void init(){
        if (ElecCore.developmentEnvironment){
            GameRegistry.register(new SchematicCreatorItem(), new EDResourceLocation("schematicCreator_DEV"));
        }
    }

}
