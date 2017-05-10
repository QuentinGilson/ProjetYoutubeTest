package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 22/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDetail {
    private VideoDetail[] items;

    public ResultDetail() {
    }

    public VideoDetail[] getItems() {
        return items;
    }

    public void setItems(VideoDetail[] items) {
        this.items = items;
    }
}
