package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by student5312 on 22/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDetailThumbnail {
    private MaxResDetailVideo medium;

    public VideoDetailThumbnail() {
    }

    public MaxResDetailVideo getMedium() {
        return medium;
    }

    public void setMedium(MaxResDetailVideo medium) {
        this.medium = medium;
    }
}
