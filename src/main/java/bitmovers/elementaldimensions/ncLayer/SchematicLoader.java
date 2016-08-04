package bitmovers.elementaldimensions.ncLayer;

import bitmovers.elementaldimensions.ElementalDimensions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import elec332.core.world.schematic.Schematic;
import elec332.core.world.schematic.SchematicHelper;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

/**
 * Created by Elec332 on 4-8-2016.
 */
public enum SchematicLoader {

    INSTANCE;

    SchematicLoader(){
        this.registeredSchematics = Sets.newHashSet();
        this.cache = Maps.newHashMap();
    }

    private Set<ResourceLocation> registeredSchematics;
    private Map<ResourceLocation, Schematic> cache;

    public void reloadCache(){
        cache.clear();
        for (ResourceLocation resourceLocation : registeredSchematics){
            try {
                cache.put(resourceLocation, SchematicHelper.INSTANCE.loadSchematic(resourceLocation));
            } catch (Exception e){ //Possible IO issues
                ElementalDimensions.logger.error("Failed to load schemtic: "+resourceLocation);
            }
        }
    }

    public boolean registerSchematic(ResourceLocation resourceLocation){
        return registeredSchematics.add(resourceLocation);
    }

    @Nullable
    public Schematic getSchematic(ResourceLocation resourceLocation){
        return cache.get(resourceLocation);
    }

}
