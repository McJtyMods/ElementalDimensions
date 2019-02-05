package bitmovers.elementaldimensions.network;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.init.ItemRegister;
import elec332.core.ElecCore;
import elec332.core.network.packets.AbstractPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * Created by Elec332 on 8-8-2016.
 */
public class PacketPointedEntity extends AbstractPacket {

    public PacketPointedEntity(){
        if (ElecCore.proxy.isClient()){
            NBTTagCompound tag = new NBTTagCompound();
            Entity e = ElementalDimensions.proxy.getPointedEntity();
            tag.setInteger("eID", e == null ? -1 : e.getEntityId());
            networkPackageObject = tag;
        }
    }

    @Override
    public IMessage onMessageThreadSafe(NBTTagCompound abstractPacket, MessageContext messageContext) {
        int i = abstractPacket.getInteger("eID");
        if (i > -1){
            EntityPlayer p = messageContext.getServerHandler().player;
            Entity e = p.getEntityWorld().getEntityByID(i);
            if (e instanceof EntityLiving){
                if (!p.getHeldItemMainhand().isEmpty() && p.getHeldItemMainhand().getItem() == ItemRegister.elementalWand && !p.getHeldItemOffhand().isEmpty() && p.getHeldItemOffhand().getItem() == ItemRegister.focusDamage){
                    e.attackEntityFrom(DamageSource.causeIndirectMagicDamage(p, e), 7);
                }
            }
        }
        return null;
    }

}
