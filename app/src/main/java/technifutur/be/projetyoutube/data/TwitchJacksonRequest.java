package technifutur.be.projetyoutube.data;

import com.android.volley.AuthFailureError;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by student5312 on 27/04/17.
 */

public class TwitchJacksonRequest<T> extends JacksonRequest<T> {

    public TwitchJacksonRequest(int method, String url, JacksonRequestListener<T> listener) {
        super(method, url, listener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> map = new HashMap();
        map.put("client-id","jbfo2cvco6r16mrvpm1v3t0jd3oly1");
        return map;
    }

}
