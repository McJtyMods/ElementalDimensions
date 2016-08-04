package bitmovers.elementaldimensions.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Elec332 on 4-8-2016.
 */
public class DateHelper {

    public static String getDateAsNormalString(){
        return (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date());
    }

}
