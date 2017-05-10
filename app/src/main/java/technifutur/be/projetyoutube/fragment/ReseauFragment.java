package technifutur.be.projetyoutube.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;

public class ReseauFragment extends Fragment {

    @BindView(R.id.twitter_timeline)
    ListView twitterTimeline;
    @BindView(R.id.logo_facebook)
    ImageView logoFacebook;
    @BindView(R.id.logo_twitter)
    ImageView logoTwitter;
    @BindView(R.id.logo_instagram)
    ImageView logoInstagram;
    @BindView(R.id.logo_snapchat)
    ImageView logoSnapchat;

    public ReseauFragment() {
        // Required empty public constructor
    }

    public static ReseauFragment newInstance() {

        Bundle args = new Bundle();

        ReseauFragment fragment = new ReseauFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reseau, container, false);
        ButterKnife.bind(this, view);
        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("FollowRevi").build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext()).setTimeline(userTimeline).build();
        twitterTimeline.setAdapter(adapter);

        setLogo();

        return view;
    }

    private void setLogo() {
        logoFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/578438115688799")));

                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/FollowRevi-578438115688799/")));
                }
            }
        });

        logoInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/followrevi")));

                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/followrevi/")));
                }
            }
        });

        logoSnapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://snapchat.com/add/followrevi")));
            }
        });

        logoTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=followrevi")));

                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/followrevi?lang=fr")));
                }
            }
        });
    }
}
