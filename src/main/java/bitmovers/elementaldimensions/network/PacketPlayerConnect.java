package bitmovers.elementaldimensions.network;

import com.google.common.collect.Maps;
import elec332.core.network.packets.AbstractPacket;
import joptsimple.internal.Strings;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Map;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class PacketPlayerConnect extends AbstractPacket {

    public PacketPlayerConnect(){
        NBTTagCompound data = new NBTTagCompound();
        for (Map.Entry<String, INBTSerializable> entry : map.entrySet()){
            data.setTag(entry.getKey(), entry.getValue().serializeNBT());
        }
        networkPackageObject = data;
    }

    private static final Map<String, INBTSerializable> map;

    @Override
    @SuppressWarnings("unchecked")
    public IMessage onMessageThreadSafe(NBTTagCompound abstractPacket, MessageContext messageContext) {
        for (Map.Entry<String, INBTSerializable> entry : map.entrySet()){
            entry.getValue().deserializeNBT(abstractPacket.getTag(entry.getKey()));
        }
        return null;
    }

    public static void registerLoginHandler(String name, INBTSerializable<?> handler){
        if (Strings.isNullOrEmpty(name) || handler == null){
            return;
        }
        map.put(name, handler);
    }

    static {
        map = Maps.newHashMap();
    }

}
