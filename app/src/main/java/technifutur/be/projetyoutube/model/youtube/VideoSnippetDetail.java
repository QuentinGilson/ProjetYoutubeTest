package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * Created by student5312 on 22/03/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoSnippetDetail {

    private String title;
    private VideoDetailThumbnail thumbnails;

    public  VideoSnippetDetail() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public VideoDetailThumbnail getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(VideoDetailThumbnail thumbnails) {
        this.thumbnails = thumbnails;
    }
}
