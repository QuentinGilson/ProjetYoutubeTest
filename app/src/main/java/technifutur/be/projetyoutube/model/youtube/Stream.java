package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 27/04/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stream {

    private String game;

    public Stream() {
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }
}
