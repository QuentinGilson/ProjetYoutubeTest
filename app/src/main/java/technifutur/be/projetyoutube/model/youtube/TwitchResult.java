package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 27/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitchResult {

    private Stream stream;

    public TwitchResult() {
    }

    public Stream getStream() {
        return stream;
    }

    public void setStream(Stream stream) {
        this.stream = stream;
    }
}
