package bitmovers.elementaldimensions.util;

import elec332.core.config.Configurable;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class Config {

    @Configurable.Class
    public static class General {

    }

    @Configurable.Class
    public static class Client { //Maybe

    }



    @Configurable.Class(comment = "Dimension settings")
    public static class Dimensions {

        @Configurable(comment = "Chance for a portal dungeon to spawn (1 means 100%)", minValue = 0.0f, maxValue = 1.0f)
        public static float portalDungeonChance = 0.02f;

        @Configurable.Class(comment = "Settings for the Air dimension.")
        public static class Air {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 102;

        }

        @Configurable.Class(comment = "Settings for the Earth dimension.")
        public static class Earth {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 100;

        }

        @Configurable.Class(comment = "Settings for the Fire dimension.")
        public static class Fire {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 104;

        }

        @Configurable.Class(comment = "Settings for the Spirit dimension.")
        public static class Spirit {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 103;

        }

        @Configurable.Class(comment = "Settings for the Water dimension.")
        public static class Water {

            @Configurable(comment = "Dimension ID for the dimension", minValue = 2, maxValue = 1000)
            public static int dimensionID = 101;

        }

    }

}
