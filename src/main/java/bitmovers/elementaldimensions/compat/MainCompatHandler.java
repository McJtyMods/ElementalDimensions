package bitmovers.elementaldimensions.compat;

import bitmovers.elementaldimensions.compat.top.TOPCompatibility;
import bitmovers.elementaldimensions.compat.waila.WailaCompatibility;
import net.minecraftforge.fml.common.Loader;

public class MainCompatHandler {

    public static void registerWaila() {
        if (Loader.isModLoaded("Waila")) {
            WailaCompatibility.register();
        }
    }

    public static void registerTOP() {
        if (Loader.isModLoaded("theoneprobe")) {
            TOPCompatibility.register();
        }
    }

}
