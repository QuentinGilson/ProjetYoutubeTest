package technifutur.be.projetyoutube.data;

import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequestListener;


import technifutur.be.projetyoutube.application.App;
import technifutur.be.projetyoutube.model.youtube.Stream;
import technifutur.be.projetyoutube.model.youtube.TwitchResult;

/**
 * Created by student5312 on 27/04/17.
 */

public class TwitchRequest {

    public interface TwitchListener{
        void isOnline(@Nullable Stream stream);
    }

    public static void checkTwitch(final TwitchListener listener){
        String url ="https://api.twitch.tv/kraken/streams/nl_kripp";

        TwitchJacksonRequest<TwitchResult> request = new TwitchJacksonRequest<>(Request.Method.GET, url, new JacksonRequestListener<TwitchResult>() {
            @Override
            public void onResponse(TwitchResult response, int statusCode, VolleyError error) {
                if(listener!=null){
                    if(response!=null){
                        listener.isOnline(response.getStream());
                    }
                }
            }

            @Override
            public JavaType getReturnType() {
                return SimpleType.constructUnsafe(TwitchResult.class);
            }
        });
        App.getSharedInstance().getRequestQueue().add(request);
    }
}
