package technifutur.be.projetyoutube.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;


import technifutur.be.projetyoutube.application.App;
import technifutur.be.projetyoutube.model.youtube.Push;

/**
 * Created by student5312 on 5/05/17.
 */

public class MyServerRequest {

    public interface MyServerListener{
        void didSendId();
    }

    public static void sendId(final MyServerListener listener, String id){
        String url="http://10.53.1.1:8080/push/"+id;

        JacksonRequest<Push> request = new JacksonRequest<Push>(Request.Method.POST, url, new JacksonRequestListener<Push>() {
            @Override
            public void onResponse(Push response, int statusCode, VolleyError error) {
                Log.d("response", String.valueOf(response));
                Log.d("statusCode", String.valueOf(statusCode));
                Log.d("error", String.valueOf(error));
                if(listener!=null){
                    if(response!=null){
                        Log.d("push",response.getPushid());
                        listener.didSendId();
                    }
                }
            }

            @Override
            public JavaType getReturnType() {
                return SimpleType.constructUnsafe(Push.class);
            }
        });
        App.getSharedInstance().getRequestQueue().add(request);
    }
}
