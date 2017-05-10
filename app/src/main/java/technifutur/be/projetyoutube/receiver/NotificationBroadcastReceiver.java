package technifutur.be.projetyoutube.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by student5312 on 5/05/17.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null){
            int page = intent.getIntExtra("page",0);


        }
    }
}
