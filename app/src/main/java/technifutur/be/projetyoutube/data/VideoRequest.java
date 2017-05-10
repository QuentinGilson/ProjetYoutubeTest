package technifutur.be.projetyoutube.data;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.spothero.volley.JacksonRequest;
import com.spothero.volley.JacksonRequestListener;

import java.util.Arrays;
import java.util.List;

import technifutur.be.projetyoutube.application.App;
import technifutur.be.projetyoutube.model.youtube.Result;
import technifutur.be.projetyoutube.model.youtube.ResultDetail;
import technifutur.be.projetyoutube.model.youtube.Video;
import technifutur.be.projetyoutube.model.youtube.VideoDetail;

/**
 * Created by student5312 on 21/03/17.
 */

public class VideoRequest {

    public interface VideoListener{
        void didReceiveMovie(List<Video> allVideo);
        void didFail();
    }

    public interface DetailVideoListener{
        void didReceiveDetailVideo(List<VideoDetail> videoDetail);
        void didFail();
    }

    public static void getVideos(final VideoListener listener, final String nbrVideo){
        String url = "https://www.googleapis.com/youtube/v3/search?part=id&channelId=UCkE5QhxlelSLaEkkTtEAQoA&type=video&key=AIzaSyB-Z70IJ_t_XJ-YnjXOOv1K8A3IuTMM9dY&order=date&maxResults="+nbrVideo;

        JacksonRequest<Result> request = new JacksonRequest<Result>(Request.Method.GET, url, new JacksonRequestListener<Result>() {
            @Override
            public void onResponse(Result response, int statusCode, VolleyError error) {
                if(listener!=null){
                    if(response!=null){
                        listener.didReceiveMovie(Arrays.asList(response.getItems()));
                    } else if(error!=null){
                        listener.didFail();
                    }
                }
            }

            @Override
            public JavaType getReturnType() {
                return SimpleType.constructUnsafe(Result.class);
            }
        });
        App.getSharedInstance().getRequestQueue().add(request);
    }

    public static void getVideoDetail(final DetailVideoListener listener, String idVideo){
        //String url = "https://www.googleapis.com/youtube/v3/videos?part=id%2C+snippet&id="+idVideo+"&key=AIzaSyB-Z70IJ_t_XJ-YnjXOOv1K8A3IuTMM9dY";
        String url = "https://www.googleapis.com/youtube/v3/videos?part=id,snippet,statistics,contentDetails&id="+idVideo+"&key=AIzaSyB-Z70IJ_t_XJ-YnjXOOv1K8A3IuTMM9dY";

        JacksonRequest<ResultDetail> request = new JacksonRequest<ResultDetail>(Request.Method.GET, url, new JacksonRequestListener<ResultDetail>() {
            @Override
            public void onResponse(ResultDetail response, int statusCode, VolleyError error) {
                if(listener!=null){
                    if(response!=null){
                        listener.didReceiveDetailVideo(Arrays.asList(response.getItems()));
                    } else if(error!=null){
                        listener.didFail();
                    }
                }
            }

            @Override
            public JavaType getReturnType() {
                return SimpleType.constructUnsafe(ResultDetail.class);
            }
        });
        App.getSharedInstance().getRequestQueue().add(request);
    }
}
