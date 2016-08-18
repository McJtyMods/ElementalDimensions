package bitmovers.elementaldimensions.sound;

import bitmovers.elementaldimensions.util.EDResourceLocation;
import elec332.core.util.RegistryHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class MobSounds {

    public static void init() {
        GHOST = registerSound(new EDResourceLocation("ghost"));
        GHOST2 = registerSound(new EDResourceLocation("ghost2"));
        GHOST_SHOOT = registerSound(new EDResourceLocation("ghost_shoot"));
    }

    public static SoundEvent GHOST;
    public static SoundEvent GHOST2;
    public static SoundEvent GHOST_SHOOT;

    private static SoundEvent registerSound(ResourceLocation rl){
        SoundEvent ret = new SoundEvent(rl).setRegistryName(rl);
        RegistryHelper.getSoundEventRegistry().register(ret);
        return ret;
    }


}
