package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 22/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaxResDetailVideo {
    private String url;

    public MaxResDetailVideo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
