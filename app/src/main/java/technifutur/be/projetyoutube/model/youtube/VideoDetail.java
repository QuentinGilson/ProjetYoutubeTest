package technifutur.be.projetyoutube.model.youtube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by student5312 on 22/03/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoDetail {
    private VideoSnippetDetail snippet;
    private VideoStatisticsDetail statistics;
    private VideoContentDetail contentDetails;

    public VideoDetail() {
    }

    public VideoSnippetDetail getSnippet() {
        return snippet;
    }

    public void setSnippet(VideoSnippetDetail snippet) {
        this.snippet = snippet;
    }

    public VideoStatisticsDetail getStatistics() {
        return statistics;
    }

    public void setStatistics(VideoStatisticsDetail statistics) {
        this.statistics = statistics;
    }

    public VideoContentDetail getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(VideoContentDetail contentDetails) {
        this.contentDetails = contentDetails;
    }
}
