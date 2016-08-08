package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.dimensions.generators.tools.FireTerrainGenerator;
import bitmovers.elementaldimensions.dimensions.generators.tools.WaterTerrainGenerator;
import bitmovers.elementaldimensions.util.Config;
import com.google.common.collect.Maps;
import elec332.core.api.structure.GenerationType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

/**
 * Created by Elec332 on 4-8-2016.
 */
public enum Dimensions implements IStringSerializable {

    EARTH(0, GenerationType.SURFACE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Earth.dimensionID;
        }

        @Override
        public String[] getTaskDescriptions() {
            return new String[] { "Kill dirt zombies for the first water rune part", "Find a dungeon for the second part", "Kill the boss at midnight for the last part" };
        }
    },
    WATER(1, GenerationType.NONE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Water.dimensionID;
        }

        @Override
        public BlockPos adjustHeight(int chunkX, int chunkZ, BlockPos pos, World world, Random random) {
            BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);
            if (topBlock.getY() >= WaterTerrainGenerator.SEALEVEL) {
                return topBlock;
            } else {
                return new BlockPos(pos.getX(), WaterTerrainGenerator.SEALEVEL, pos.getZ());
            }
        }

        @Override
        public String[] getTaskDescriptions() {
            return new String[] { "Kill water creeps for the first air rune part", "Find a water dungeon for the second part", "Throw a water boss seed in water for the last part" };
        }
    },
    AIR(2, GenerationType.NONE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Air.dimensionID;
        }

        @Override
        public BlockPos adjustHeight(int chunkX, int chunkZ, BlockPos pos, World world, Random random) {
            return new BlockPos(pos.getX(), random.nextInt(50)+30, pos.getZ());
        }

        @Override
        public String[] getTaskDescriptions() {
            return new String[] { "Kill ghosts for the first spirit rune part", "Find a dungeon for the second part", "Throw an air boss seed in the air for the last part" };
        }
    },
    SPIRIT(3, GenerationType.SURFACE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Spirit.dimensionID;
        }

        @Override
        public String[] getTaskDescriptions() {
            return new String[] { "Kill spirits for the first spirit rune part", "Find a dungeon for the second part", "WIP: There is no boss yet, kill other spirits" };
        }
    },
    FIRE(4, GenerationType.NONE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Fire.dimensionID;
        }

        @Override
        public BlockPos adjustHeight(int chunkX, int chunkZ, BlockPos pos, World world, Random random) {
            BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);
            if (topBlock.getY() >= FireTerrainGenerator.LAVALEVEL) {
                return topBlock;
            } else {
                return new BlockPos(pos.getX(), FireTerrainGenerator.LAVALEVEL, pos.getZ());
            }
        }

        @Override
        public String[] getTaskDescriptions() {
            return new String[] { "Throw a fire boss seed in the air for the final reward!" };
        }
    },
    OVERWORLD(-1, GenerationType.NONE) {

        @Override
        public int getDimensionID() {
            return 0;
        }

        @Override
        public BlockPos adjustHeight(int chunkX, int chunkZ, BlockPos pos, World world, Random random) {
            BlockPos topBlock = world.getTopSolidOrLiquidBlock(pos);
            if (topBlock.getY() >= 63) {
                return topBlock;
            } else {
                return new BlockPos(pos.getX(), 63, pos.getZ());
            }
        }
    };

    Dimensions(int level, GenerationType generationType){
        this.level = (byte)level;
        this.generationType = generationType;
        this.name = toString().toLowerCase();
    }

    private final byte level;
    private final GenerationType generationType;
    private final String name;

    public abstract int getDimensionID();

    public String[] getTaskDescriptions() {
        return null;
    }

    public byte getLevel(){
        return level;
    }

    public GenerationType getGenerationType() {
        return generationType;
    }

    public BlockPos adjustHeight(int chunkX, int chunkZ, BlockPos pos, World world, Random random) {
        return pos;
    }

    @Nullable
    public static Dimensions findDimension(int id) {
        for (Dimensions dimension : Dimensions.values()){
            if (dimension.getDimensionID() == id){
                return dimension;
            }
        }
        return null;
    }

    @Nullable
    public static Dimensions findLevel(int level) {
        for (Dimensions dimension : Dimensions.values()){
            if (dimension.getLevel() == level){
                return dimension;
            }
        }
        return null;
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    public static Dimensions getDimensionFromLevel(int level){
        return LEVEL_MAP.get(level);
    }

    public static final Dimensions[] VALUES;
    private static final Map<Integer, Dimensions> LEVEL_MAP;

    static {
        VALUES = values();
        LEVEL_MAP = Maps.newHashMap();
        for (Dimensions dim : VALUES){
            LEVEL_MAP.put((int)dim.getLevel(), dim);
        }
    }

}
