package bitmovers.elementaldimensions.proxy;

import bitmovers.elementaldimensions.ClientForgeEventHandlers;
import bitmovers.elementaldimensions.ElementalDimensions;
import bitmovers.elementaldimensions.mobs.*;
import bitmovers.elementaldimensions.ncLayer.overworldTweaks.client.ClientBlockHandler;
import bitmovers.elementaldimensions.sound.MobSounds;
import bitmovers.elementaldimensions.sound.SoundHandler;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import elec332.core.ElecCore;
import mcjty.lib.McJtyLibClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(new ClientForgeEventHandlers());

        super.preInit(e);
        McJtyLibClient.preInit(e);

        OBJLoader.INSTANCE.addDomain(ElementalDimensions.MODID);

        registerEntityRenderers();
        MobSounds.init();
    }

    private final Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
        elec332.core.loader.client.RenderingRegistry.instance().registerLoader(ClientBlockHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
        registerTESRs();
    }

    private void registerEntityRenderers(){
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtZombie.class, RenderDirtZombie.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGuard.class, RenderGuard.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterCreep.class, RenderWaterCreep.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityDirtZombieBoss.class, RenderDirtZombieBoss.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityWaterCreepBoss.class, RenderWaterCreepBoss.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhost.class, RenderGhost.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostBoss.class, RenderGhostBoss.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpirit.class, RenderSpirit.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlaster.class, RenderBlaster.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFireBoss.class, RenderFireBoss.FACTORY);
    }

    private void registerTESRs(){

    }

    @Override
    public Entity getPointedEntity() {
        float partialTicks = mc.getRenderPartialTicks();
        Entity pointedEntity = null;
        Entity entity = this.mc.getRenderViewEntity();

        if (entity != null)
        {
            if (ElecCore.proxy.getClientWorld() != null)
            {
                this.mc.mcProfiler.startSection("pick");
                this.mc.pointedEntity = null;
                double d0 = (double)this.mc.playerController.getBlockReachDistance() + 20;
                this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
                double d1 = d0;
                Vec3d vec3d = entity.getPositionEyes(partialTicks);
                boolean flag = false;
                int i = 3;

                if (this.mc.playerController.extendedReach() || true)
                {
                    d0 = 20;//6.0D;
                    d1 = 20;//6.0D;
                }
                else
                {
                    if (d0 > 3.0D)
                    {
                        flag = true;
                    }
                }

                if (this.mc.objectMouseOver != null)
                {
                    d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3d);
                }

                Vec3d vec3d1 = entity.getLook(partialTicks);
                Vec3d vec3d2 = vec3d.addVector(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
                pointedEntity = null;
                Vec3d vec3d3 = null;
                float f = 1.0F;
                List<Entity> list = ElecCore.proxy.getClientWorld().getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().offset(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0).expand(f, f, f), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
                {
                    @Override
                    public boolean apply(@Nullable Entity p_apply_1_)
                    {
                        return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
                    }
                }));
                double d2 = d1;

                for (int j = 0; j < list.size(); ++j)
                {
                    Entity entity1 = list.get(j);
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
                    RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);

                    if (axisalignedbb.contains(vec3d))
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
                            if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity.canRiderInteract())
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
/*
                if (pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > 3.0D)
                {
                    pointedEntity = null;
                    this.mc.objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
                }

                if (pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null))
                {
                    this.mc.objectMouseOver = new RayTraceResult(pointedEntity, vec3d3);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
                    {
                        this.mc.pointedEntity = pointedEntity;
                    }
                }*/

            }
        }
        return pointedEntity;
    }
}
