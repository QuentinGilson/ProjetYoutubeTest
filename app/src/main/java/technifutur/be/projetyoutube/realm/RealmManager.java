package technifutur.be.projetyoutube.realm;


import com.sendbird.android.OpenChannel;

import java.util.List;

import io.realm.Realm;
import technifutur.be.projetyoutube.application.App;
import technifutur.be.projetyoutube.model.youtube.User;

/**
 * Created by student5312 on 2/05/17.
 */

public class RealmManager {

    private static RealmManager realmManager;
    private Realm realm = App.getSharedInstance().getRealm();

    public static RealmManager getRealmManager(){
        if(realmManager==null){
            realmManager = new RealmManager();
        }
        return realmManager;
    }

    public void createUser(User user){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        realm.commitTransaction();
    }

    public boolean firstConnection(){
        return realm.where(User.class).findAll().size()==0;
    }

    public User getUser(){
        return realm.where(User.class).findFirst();
    }

    public void setImage(String url){
        realm.beginTransaction();
        getUser().setImage(url);
        realm.commitTransaction();
    }

    public void setName(String name){
        realm.beginTransaction();
        getUser().setName(name);
        realm.commitTransaction();
    }
}
