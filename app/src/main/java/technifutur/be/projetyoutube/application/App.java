package technifutur.be.projetyoutube.application;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by student5312 on 21/03/17.
 */

public class App extends Application {

    private static App sharedInstance;
    private RequestQueue requestQueue;
    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedInstance = this;

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();

        SendBird.init("9D1ADEF1-06BD-4250-B676-2434621DCF15", getApplicationContext());

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        realm = Realm.getInstance(realmConfiguration);
    }

    public static App getSharedInstance(){
        return sharedInstance;
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public Realm getRealm() {
        return realm;
    }
}
