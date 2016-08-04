package bitmovers.elementaldimensions.dimensions;

import bitmovers.elementaldimensions.util.Config;

/**
 * Created by Elec332 on 4-8-2016.
 */
public enum Dimensions {

    EARTH(0) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Earth.dimensionID;
        }

    },
    WATER(1) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Water.dimensionID;
        }

    },
    AIR(2) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Air.dimensionID;
        }

    },
    SPIRIT(3) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Spirit.dimensionID;
        }

    },
    FIRE(4) {

        @Override
        public int getDimensionID() {
            return Config.Dimensions.Fire.dimensionID;
        }

    };

    Dimensions(int level){
        this.level = (byte)level;
    }

    private final byte level;

    public abstract int getDimensionID();

    public byte getLevel(){
        return level;
    }

}
