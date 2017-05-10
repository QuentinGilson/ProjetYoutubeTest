package technifutur.be.projetyoutube.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import technifutur.be.projetyoutube.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import technifutur.be.projetyoutube.firebase.MyFireBaseInstanceIdService;
import technifutur.be.projetyoutube.firebase.MyFireBaseMessagingService;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "BVAlhbbqRTeGLb9PQzeut0iks";
    private static final String TWITTER_SECRET = "rYhb7lRZ0FurWKAoLdbuSjfyBEo5mejAXOvoF7UJyGY4DFCIPP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

    }
}
