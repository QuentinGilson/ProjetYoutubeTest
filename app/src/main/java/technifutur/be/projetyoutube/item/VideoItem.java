package technifutur.be.projetyoutube.item;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.FacebookActivity;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Url;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.activity.FaceBookActivity;
import technifutur.be.projetyoutube.activity.MainActivity;
import technifutur.be.projetyoutube.data.VideoRequest;
import technifutur.be.projetyoutube.model.youtube.Video;
import technifutur.be.projetyoutube.model.youtube.VideoDetail;


/**
 * Created by student5312 on 21/03/17.
 */

public class VideoItem extends AbstractItem<VideoItem,VideoItem.VideoViewHolder> {

    private Video video;
    private Context context;

    public VideoItem(Video video, Context context) {
        this.video = video;
        this.context = context;
    }

    @Override
    public int getType() {
        return R.id.imageview_youtube_cell;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.allvideo_row;
    }

    @Override
    public void bindView(VideoViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.refresh(video,context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+video.getId().getVideoId())));
            }
        });
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder implements VideoRequest.DetailVideoListener {
        @BindView(R.id.imageview_youtube_cell)
        protected ImageView imageView;
        @BindView(R.id.textview_youtube_cell)
        protected TextView textView;
        @BindView(R.id.textview_youtube_cell_duration)
        protected TextView textViewDuration;
        @BindView(R.id.textview_youtube_cell_numberview)
        protected  TextView textViewNumberView;
        @BindView(R.id.img_star2)
        protected ImageView star2;
        @BindView(R.id.img_star3)
        protected ImageView star3;
        @BindView(R.id.img_star4)
        protected ImageView star4;
        @BindView(R.id.img_star5)
        protected ImageView star5;
        @BindView(R.id.share_facebook_button)
        protected ShareButton shareButton;
        @BindView(R.id.share_tweeter_button)
        protected Button shareTweeter;
        private Context context;
        private String videoId;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void refresh(Video video, Context context){
            this.context = context;
            videoId = video.getId().getVideoId();
            VideoRequest.getVideoDetail(this,videoId);
        }

        @Override
        public void didReceiveDetailVideo(List<VideoDetail> videoDetail) {
            VideoDetail video = videoDetail.get(0);
            textView.setText(video.getSnippet().getTitle());
            textViewNumberView.setText("Vues : "+video.getStatistics().getViewCount());

            final String imageUrl = video.getSnippet().getThumbnails().getMedium().getUrl();
            Glide.with(context).load(imageUrl).into(imageView);

            initRating(video);
            parseDuration(video);

            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://www.youtube.com/watch?v="+videoId)).build();
            shareButton.setShareContent(shareLinkContent);

            shareTweeter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        TweetComposer.Builder builder = new TweetComposer.Builder(context).url(new URL("https://www.youtube.com/watch?v="+videoId));
                        builder.show();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void didFail() {

        }

        private void parseDuration(VideoDetail video){
            String duration = video.getContentDetails().getDuration();
            duration = duration.substring(2);
            textViewDuration.setText(duration.toLowerCase());
        }

        private void initRating(VideoDetail video){
            Float like = Float.valueOf(video.getStatistics().getLikeCount());
            Float dislike = Float.valueOf(video.getStatistics().getDislikeCount());
            Float rating = (like/(like+dislike))*5;
            for(int i=1; i<=rating;i++){
                switch (i){
                    case 1:
                        star2.setVisibility(View.VISIBLE);
                        star5.setVisibility(View.INVISIBLE);
                        star4.setVisibility(View.INVISIBLE);
                        star3.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        star3.setVisibility(View.VISIBLE);
                        star5.setVisibility(View.INVISIBLE);
                        star4.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        star4.setVisibility(View.VISIBLE);
                        star5.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        star5.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
