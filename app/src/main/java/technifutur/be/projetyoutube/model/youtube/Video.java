package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 21/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    private VideoID id;

    public Video() {
    }

    public VideoID getId() {
        return id;
    }

    public void setId(VideoID id) {
        this.id = id;
    }
}
