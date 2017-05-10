package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 5/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Push {

    private String pushid;

    public Push() {
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }
}
