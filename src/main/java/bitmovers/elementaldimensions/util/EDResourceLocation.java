package bitmovers.elementaldimensions.util;

import bitmovers.elementaldimensions.ElementalDimensions;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class EDResourceLocation extends ResourceLocation {

    public EDResourceLocation(String resourcePathIn) {
        super(ElementalDimensions.MODID, resourcePathIn);
    }

}
