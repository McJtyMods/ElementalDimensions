package bitmovers.elementaldimensions.sound;

import bitmovers.elementaldimensions.sound.scapes.SoundScape;
import bitmovers.elementaldimensions.sound.scapes.SpiritsSoundScape;
import bitmovers.elementaldimensions.sound.scapes.WindSoundScape;
import bitmovers.elementaldimensions.util.EDResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public final class SoundController {

    public static final SoundScape HOWLINGWIND = new WindSoundScape();
    public static final SoundScape SPIRITS = new SpiritsSoundScape();

    private static final Map<EDResourceLocation, SoundScape> activeScapes = new HashMap<>();
    private static final Map<EDResourceLocation, ScapedSound> activeSounds = new HashMap<>();


    public static void playSoundScape(World worldObj, EntityPlayer player, SoundScape scape) {
        EDResourceLocation key = scape.getSoundResource();
        if (activeScapes.containsKey(key)) {
            return;
        }
        float volume = scape.calculateVolume(worldObj, player.getPosition(), player);
        ScapedSound soundRecord = new ScapedSound(
                scape,
                SoundCategory.AMBIENT,
                volume, 1.0F, true, scape.getRandomDelay(), ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
        Minecraft.getMinecraft().getSoundHandler().playSound(soundRecord);
        activeScapes.put(key, scape);
        activeSounds.put(key, soundRecord);
    }

    public static void stopSoundScape(SoundScape scape) {
        EDResourceLocation key = scape.getSoundResource();
        if (activeScapes.containsKey(key)) {
            SoundScape soundScape = activeScapes.get(key);
            Minecraft.getMinecraft().getSoundHandler().stopSound(activeSounds.get(key));
            activeScapes.remove(key);
            activeSounds.remove(key);
            return;
        }
    }

    public static void stopAllSoundScapes() {
        for (Map.Entry<EDResourceLocation, ScapedSound> entry : activeSounds.entrySet()) {
            Minecraft.getMinecraft().getSoundHandler().stopSound(entry.getValue());
        }
        activeScapes.clear();
        activeSounds.clear();
    }

    public static boolean isPlaying(SoundScape scape) {
        return activeScapes.containsKey(scape.getSoundResource());
    }

}
