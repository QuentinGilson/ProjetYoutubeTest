package technifutur.be.projetyoutube.model.youtube;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by student5312 on 11/04/17.
 */

public class MyDate {

    public static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    public static String timeAgo(Date date){
        Date now = new Date();
        long difference = now.getTime()-date.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        if(difference<secondsInMilli){
            return "a l'instant";
        } else if(difference<minutesInMilli){
            return "Il y a "+difference/secondsInMilli+" secondes";
        } else if(difference<hoursInMilli){
            return "Il y a "+difference/minutesInMilli+" minutes";
        } else if(difference<daysInMilli){
            return "Il y a "+difference/hoursInMilli+" heures";
        } else if(difference<7*daysInMilli){
            return "Il y a "+difference/daysInMilli+" jours";
        } else {
            return format.format(date);
        }
    }
}
