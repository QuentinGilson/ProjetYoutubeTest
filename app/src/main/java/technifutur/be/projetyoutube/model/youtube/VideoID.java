package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 21/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoID {

    private String videoId;

    public VideoID() {

    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
