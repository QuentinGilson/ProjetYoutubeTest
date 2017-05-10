package technifutur.be.projetyoutube.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.data.TwitchRequest;
import technifutur.be.projetyoutube.data.VideoRequest;
import technifutur.be.projetyoutube.model.youtube.Stream;
import technifutur.be.projetyoutube.model.youtube.Video;
import technifutur.be.projetyoutube.model.youtube.VideoDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements VideoRequest.VideoListener, VideoRequest.DetailVideoListener, TwitchRequest.TwitchListener {


    @BindView(R.id.textview_youtube_home)
    TextView textviewYoutubeHome;
    @BindView(R.id.imageview_youtube_home)
    ImageView imageviewYoutubeHome;
    @BindView(R.id.bandeau_twitch)
    TextView bandeauTwitch;
    @BindView(R.id.layout_bandeau_twitch)
    RelativeLayout layoutBandeauTwitch;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        VideoRequest.getVideos(this, "1");
        TwitchRequest.checkTwitch(this);

        return view;
    }

    @Override
    public void didReceiveMovie(List<Video> allVideo) {
        String id = allVideo.get(0).getId().getVideoId();
        VideoRequest.getVideoDetail(this, id);
    }

    @Override
    public void didReceiveDetailVideo(List<VideoDetail> videoDetail) {
        VideoDetail video = videoDetail.get(0);
        textviewYoutubeHome.setText(video.getSnippet().getTitle());
        Glide.with(getContext()).load(video.getSnippet().getThumbnails().getMedium().getUrl()).into(imageviewYoutubeHome);

    }

    @Override
    public void didFail() {

    }

    @Override
    public void isOnline(Stream stream) {
        if (stream != null) {
            layoutBandeauTwitch.setVisibility(View.VISIBLE);
            layoutBandeauTwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitch.tv/nl_kripp")));
                }
            });
        }
    }
}
