package bitmovers.elementaldimensions.items;

import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.network.PacketPointedEntity;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import elec332.core.util.RayTraceHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemFocusDamage extends ItemFocus {

    public ItemFocusDamage() {
        super("focusdamage");
    }

    @Override
    public void execute(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote){
            ElementalDimensions.networkHandler.getNetworkWrapper().sendToServer(new PacketPointedEntity());
        }
        /*
        RayTraceResult rtr = RayTraceHelper.rayTrace(player, 20);
        //if (rtr.typeOfHit == RayTraceResult.Type.ENTITY){
            //Entity entity = rtr.entityHit;
            //if (entity instanceof EntityLiving){
                Entity pointedEntity = null;
                double d0 = 200;
                RayTraceResult objectMouseOver = RayTraceHelper.rayTrace(player, d0);
                double d1 = d0;
                Vec3d vec3d = new Vec3d(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
                boolean flag = true;
                int i = 3;

                if (objectMouseOver != null)
                {
                    d1 = objectMouseOver.hitVec.distanceTo(vec3d);
                }

        d1 = 30;

                float f1 = MathHelper.cos(-player.rotationYawHead * 0.017453292F - 3.1415927F);
                float f11 = MathHelper.sin(-player.rotationYawHead * 0.017453292F - 3.1415927F);
                float f12 = -MathHelper.cos(-player.rotationPitch * 0.017453292F);
                float f13 = MathHelper.sin(-player.rotationPitch * 0.017453292F);
                Vec3d vec3d1 = new Vec3d((double)(f11 * f12), (double)f13, (double)(f1 * f12));
                Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0);
                Vec3d vec3d3 = null;
                float f = 1.0F;
                List<Entity> list = world.getEntitiesInAABBexcluding(player, player.getEntityBoundingBox().addCoord(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
                {
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
                    }
                }));
                double d2 = d1;

                for (int j = 0; j < list.size(); ++j)
                {
                    Entity entity1 = (Entity)list.get(j);
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz((double)entity1.getCollisionBorderSize());
                    RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

                    if (axisalignedbb.isVecInside(vec3d))
                    {
                        if (d2 >= 0.0D)
                        {
                            pointedEntity = entity1;
                            vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
                            d2 = 0.0D;
                        }
                    }
                    else if (raytraceresult != null)
                    {
                        double d3 = vec3d.distanceTo(raytraceresult.hitVec);

                        if (d3 < d2 || d2 == 0.0D)
                        {
                            if (entity1.getLowestRidingEntity() == player.getLowestRidingEntity() && !player.canRiderInteract())
                            {
                                if (d2 == 0.0D)
                                {
                                    pointedEntity = entity1;
                                    vec3d3 = raytraceresult.hitVec;
                                }
                            }
                            else
                            {
                                pointedEntity = entity1;
                                vec3d3 = raytraceresult.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }

                if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > 3.0D)
                {
                    pointedEntity = null;
                    objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
                }

                if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
                {
                    objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    {
                        pointedEntity = pointedEntity;
                    }
                }
                pointedEntity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, pointedEntity), 7);
                //((EntityPlayerMP)player).
                //entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, entity), 7);
                //PlayerHelper.smiteEntity(player, (EntityLivingBase) entity); Nah
            //}
        //}*/
    }

}
