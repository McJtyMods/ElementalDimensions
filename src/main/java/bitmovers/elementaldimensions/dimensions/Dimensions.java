package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;
import elec332.core.api.structure.GenerationType;

import javax.annotation.Nullable;

/**
 * Created by Elec332 on 4-8-2016.
 */
public enum Dimensions {

    EARTH(0, GenerationType.SURFACE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Earth.dimensionID;
        }

    },
    WATER(1, GenerationType.SURFACE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Water.dimensionID;
        }

    },
    AIR(2, GenerationType.SEA_LEVEL) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Air.dimensionID;
        }

    },
    SPIRIT(3, GenerationType.NONE) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Spirit.dimensionID;
        }

    },
    FIRE(4, GenerationType.SEA_LEVEL) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Fire.dimensionID;
        }

    };

    Dimensions(int level, GenerationType generationType){
        this.level = (byte)level;
        this.generationType = generationType;
    }

    private final byte level;
    private final GenerationType generationType;

    public abstract int getDimensionID();

    public byte getLevel(){
        return level;
    }

    public GenerationType getGenerationType() {
        return generationType;
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


}
