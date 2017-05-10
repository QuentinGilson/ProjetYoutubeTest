package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 28/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoContentDetail {
    private String duration;

    public VideoContentDetail() {
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
