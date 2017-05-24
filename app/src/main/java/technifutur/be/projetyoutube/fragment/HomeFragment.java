package technifutur.be.projetyoutube.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.UserMessage;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.data.TwitchRequest;
import technifutur.be.projetyoutube.data.VideoRequest;
import technifutur.be.projetyoutube.item.AllForumItem;
import technifutur.be.projetyoutube.model.youtube.Achievement;
import technifutur.be.projetyoutube.model.youtube.Stream;
import technifutur.be.projetyoutube.model.youtube.User;
import technifutur.be.projetyoutube.model.youtube.Video;
import technifutur.be.projetyoutube.model.youtube.VideoDetail;
import technifutur.be.projetyoutube.realm.RealmManager;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements VideoRequest.VideoListener, VideoRequest.DetailVideoListener, TwitchRequest.TwitchListener, SendBirdManager.HomeListener, AllForumItem.OnItemClick, ChatInstantaneFragment.ChatInstantaneDestroyListener {

    @BindView(R.id.bandeau_twitch)
    TextView bandeauTwitch;
    @BindView(R.id.layout_bandeau_twitch)
    RelativeLayout layoutBandeauTwitch;
    @BindView(R.id.textview_youtube_cell)
    TextView textviewYoutubeCell;
    @BindView(R.id.imageview_youtube_cell)
    ImageView imageviewYoutubeCell;
    @BindView(R.id.textview_youtube_cell_duration)
    TextView textviewYoutubeCellDuration;
    @BindView(R.id.textview_youtube_cell_numberview)
    TextView textviewYoutubeCellNumberview;
    @BindView(R.id.share_facebook_button)
    ShareButton shareFacebookButton;
    @BindView(R.id.share_tweeter_button)
    Button shareTweeterButton;
    @BindView(R.id.img_star1)
    ImageView imgStar1;
    @BindView(R.id.img_star2)
    ImageView imgStar2;
    @BindView(R.id.img_star3)
    ImageView imgStar3;
    @BindView(R.id.img_star4)
    ImageView imgStar4;
    @BindView(R.id.img_star5)
    ImageView imgStar5;
    @BindView(R.id.layout_option_video)
    RelativeLayout layoutOptionVideo;
    @BindView(R.id.main_layout_video)
    RelativeLayout mainLayoutVideo;
    @BindView(R.id.recyclerview_home_forum)
    RecyclerView recyclerviewHomeForum;
    private String id;
    private SendBirdManager sendBirdManager;
    private FastItemAdapter<AbstractItem> allForumAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean firstTimeStartFragment = false;
    private List<Long> allLastMessageDate;



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

        initRecyclerView();
        initSendBird();
        Log.d("test","test");
        refresh();

        return view;
    }

    public void refresh(){
        VideoRequest.getVideos(this, "1");
        TwitchRequest.checkTwitch(this);
        sendBirdManager.getThreeOpenChannel();
    }

    private void initSendBird(){
        User user = RealmManager.getRealmManager().getUser();
        sendBirdManager = SendBirdManager.getSendBirdManager();
        sendBirdManager.setHomeListener(this);
        sendBirdManager.connectSendBird(user);
    }

    private void initRecyclerView(){
        allForumAdapter = new FastItemAdapter<AbstractItem>();
        recyclerviewHomeForum.setAdapter(allForumAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewHomeForum.setLayoutManager(linearLayoutManager);

        if(!firstTimeStartFragment) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
            dividerItemDecoration.setDrawable(drawable);
            recyclerviewHomeForum.addItemDecoration(dividerItemDecoration);
            firstTimeStartFragment = true;
            allLastMessageDate = new ArrayList<>();
        }
    }

    @Override
    public void didReceiveMovie(List<Video> allVideo) {
        id = allVideo.get(0).getId().getVideoId();
        VideoRequest.getVideoDetail(this, id);
    }

    @Override
    public void didReceiveDetailVideo(List<VideoDetail> videoDetail) {
        final VideoDetail video = videoDetail.get(0);
        textviewYoutubeCell.setText(video.getSnippet().getTitle());
        Glide.with(getContext()).load(video.getSnippet().getThumbnails().getMedium().getUrl()).into(imageviewYoutubeCell);
        initRating(video);
        parseDuration(video);
        textviewYoutubeCellNumberview.setText("Vues : " + video.getStatistics().getViewCount());


        imageviewYoutubeCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + id)));
            }
        });

        final CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://www.youtube.com/watch?v=" + id)).build();
        shareFacebookButton.setShareContent(shareLinkContent);

        shareFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        shareTweeterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent builder = new TweetComposer.Builder(getContext()).url(new URL("https://www.youtube.com/watch?v=" + id)).createIntent();
                    startActivityForResult(builder, 404);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private void initRating(VideoDetail video) {
        Float like = Float.valueOf(video.getStatistics().getLikeCount());
        Float dislike = Float.valueOf(video.getStatistics().getDislikeCount());
        Float rating = (like / (like + dislike)) * 5;
        for (int i = 1; i <= rating; i++) {
            switch (i) {
                case 1:
                    imgStar2.setVisibility(View.VISIBLE);
                    imgStar5.setVisibility(View.INVISIBLE);
                    imgStar4.setVisibility(View.INVISIBLE);
                    imgStar3.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    imgStar3.setVisibility(View.VISIBLE);
                    imgStar5.setVisibility(View.INVISIBLE);
                    imgStar4.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    imgStar4.setVisibility(View.VISIBLE);
                    imgStar5.setVisibility(View.INVISIBLE);
                    break;
                case 4:
                    imgStar5.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    private void parseDuration(VideoDetail video) {
        String duration = video.getContentDetails().getDuration();
        duration = duration.substring(2);
        textviewYoutubeCellDuration.setText(duration.toLowerCase());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 404:
                if (resultCode == Activity.RESULT_OK) {
                    Achievement achievement = RealmManager.getRealmManager().getAchievement("It's time to fly");
                    RealmManager.getRealmManager().setAchievementCount(achievement);
                    ShowAchievementFragment showAchievementFragment = ShowAchievementFragment.newInstance(achievement);
                    getFragmentManager().beginTransaction().add(R.id.activity_pager, showAchievementFragment).addToBackStack(null).commit();
                }
                break;
        }
    }

    @Override
    public void connectedToSendBird() {
        sendBirdManager.getThreeOpenChannel();
    }

    @Override
    public void allOpenChannel(List<OpenChannel> allopenChannel) {
        allForumAdapter.clear();
        allLastMessageDate = new ArrayList<>();
        for (OpenChannel openChannel : allopenChannel) {
            sendBirdManager.getLastMessageForHome(openChannel);
        }
    }

    @Override
    public void getLastMessage(UserMessage userMessage, OpenChannel openChannel) {
        if(allLastMessageDate.size()>0) {
            boolean out = false;
            for (int i = 0; i < allLastMessageDate.size() && !out; i++) {
                if (userMessage.getCreatedAt() > allLastMessageDate.get(i)) {
                    allLastMessageDate.add(i, userMessage.getCreatedAt());
                    allForumAdapter.add(i, new AllForumItem(openChannel, this,userMessage,getContext()));
                    out = true;
                }
            }
            if(!out){
                allLastMessageDate.add( userMessage.getCreatedAt());
                allForumAdapter.add(new AllForumItem(openChannel, this,userMessage,getContext()));
            }
        }else{
            allLastMessageDate.add(userMessage.getCreatedAt());
            allForumAdapter.add(new AllForumItem(openChannel, this,userMessage,getContext()));
        }
        recyclerviewHomeForum.smoothScrollToPosition(0);
    }

    @Override
    public void onClick(OpenChannel openChannel) {
        ChatInstantaneFragment chatInstantaneFragment = ChatInstantaneFragment.newInstance(openChannel);
        chatInstantaneFragment.setChatInstantaneDestroyListener(this);
        getFragmentManager().beginTransaction().replace(R.id.activity_pager, chatInstantaneFragment).addToBackStack(null).commit();
    }

    @Override
    public void onDestroyFragmentChat() {

    }

    @Override
    public void onStop() {
        super.onStop();
        firstTimeStartFragment = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }
}
