package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 21/03/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    private String nextPageToken;
    private Video[] items;

    public Result() {
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public Video[] getItems() {
        return items;
    }

    public void setItems(Video[] items) {
        this.items = items;
    }
}
