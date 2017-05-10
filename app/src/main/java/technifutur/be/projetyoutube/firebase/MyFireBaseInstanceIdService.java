package technifutur.be.projetyoutube.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import technifutur.be.projetyoutube.data.MyServerRequest;

/**
 * Created by student5312 on 4/05/17.
 */

public class MyFireBaseInstanceIdService extends FirebaseInstanceIdService implements MyServerRequest.MyServerListener {

    private static final String TAG = "PUSH_IT_TO_THE_LIMIT";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        MyServerRequest.sendId(this,refreshedToken);

    }

    @Override
    public void didSendId() {
        Log.d("id","didsendid");
    }
}
