package bitmovers.elementaldimensions.sound;

import bitmovers.elementaldimensions.sound.scapes.SoundScape;
import mcjty.lib.tools.MinecraftTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.SoundCategory;

public class ScapedSound extends PositionedSoundRecord implements ITickableSound {

    private final SoundScape scape;
    private float desiredVolume;

    public ScapedSound(SoundScape scape, SoundCategory categoryIn, float volumeIn, float pitchIn, boolean repeatIn, int repeatDelayIn, AttenuationType attenuationTypeIn, float xIn, float yIn, float zIn) {
        super(scape.getSoundResource(), categoryIn, volumeIn, pitchIn, repeatIn, repeatDelayIn, attenuationTypeIn, xIn, yIn, zIn);
        this.scape = scape;
        desiredVolume = volumeIn;
    }

    @Override
    public boolean isDonePlaying() {
        return false;
    }

    @Override
    public void update() {
        if (scape.needsUpdate()) {
            EntityPlayerSP player = MinecraftTools.getPlayer(Minecraft.getMinecraft());
            WorldClient world = MinecraftTools.getWorld(Minecraft.getMinecraft());
            desiredVolume = scape.calculateVolume(world, player.getPosition(), player);
        }

        if (Math.abs(desiredVolume-volume) > 0.01f) {
            this.volume = (volume * .90f) + (desiredVolume * .1f);
        }
    }
}
