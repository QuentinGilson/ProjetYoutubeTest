package technifutur.be.projetyoutube.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.activity.PagerActivity;
import technifutur.be.projetyoutube.model.youtube.Achievement;
import technifutur.be.projetyoutube.model.youtube.User;
import technifutur.be.projetyoutube.realm.RealmManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    @BindView(R.id.login_button)
    LoginButton loginButton;
    private CallbackManager callbackManager;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        loginButton.setFragment(this);

        if(!isLoggedIn()) {
            callbackManager = CallbackManager.Factory.create();
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    startNextActivity();
                }

                @Override
                public void onCancel() {
                    Log.d("user", "cancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("user", error.getMessage());
                }
            });
        } else {
            startNextActivity();
        }
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(callbackManager!= null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public void startNextActivity(){
        if(RealmManager.getRealmManager().firstConnection()) {
            createAchievement();
            Bundle b = new Bundle();
            b.putString("fields", "id,name,picture");
            new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    AccessToken.getCurrentAccessToken().getUserId(), b, HttpMethod.GET, new GraphRequest.Callback() {
                public void onCompleted(GraphResponse response) {
                    User user = new User();

                    JSONObject responseJSON = response.getJSONObject();
                    try {
                        user.setId(responseJSON.getString("id"));
                        user.setName(responseJSON.getString("name"));
                        user.setImage("graph.facebook.com/"+user.getId()+"/picture?type=large");
                        RealmManager.getRealmManager().createUser(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            ).executeAsync();
        }
        startActivity(new Intent(getContext(),PagerActivity.class));
    }

    private void createAchievement(){
        RealmManager.getRealmManager().createAchievement(new Achievement("In your face",R.drawable.inyourface));
        RealmManager.getRealmManager().createAchievement(new Achievement("Enigma",R.drawable.enigma));
        RealmManager.getRealmManager().createAchievement(new Achievement("It's time to fly",R.drawable.firsttweet));
        RealmManager.getRealmManager().createAchievement(new Achievement("His name is !",R.drawable.knowmyname));
        RealmManager.getRealmManager().createAchievement(new Achievement("Il ne suffit pas de parler, il faut parler juste.",R.drawable.shakespear));
    }
}
