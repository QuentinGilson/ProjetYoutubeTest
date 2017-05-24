package technifutur.be.projetyoutube.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.hoang8f.android.segmented.SegmentedGroup;
import technifutur.be.projetyoutube.R;

public class ReseauFragment extends Fragment {

    @BindView(R.id.logo_facebook)
    ImageView logoFacebook;
    @BindView(R.id.logo_twitter)
    ImageView logoTwitter;
    @BindView(R.id.logo_instagram)
    ImageView logoInstagram;
    @BindView(R.id.logo_snapchat)
    ImageView logoSnapchat;
    @BindView(R.id.radio_button_twitter)
    RadioButton radioButtonTwitter;
    @BindView(R.id.radio_button_facebook)
    RadioButton radioButtonFacebook;
    @BindView(R.id.radio_button_instagram)
    RadioButton radioButtonInstagram;
    @BindView(R.id.segment_button_reseau)
    SegmentedGroup segmentButtonReseau;
    @BindView(R.id.layout_reseau_timeline)
    RelativeLayout layoutReseauTimeline;

    public ReseauFragment() {
        // Required empty public constructor
    }

    public static ReseauFragment newInstance() {
        ReseauFragment fragment = new ReseauFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reseau, container, false);
        ButterKnife.bind(this, view);

        radioButtonInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagramTimeline();
            }
        });
        radioButtonTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twitterTimeline();
            }
        });
        radioButtonFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookTimeline();
            }
        });

        setLogo();

        return view;
    }

    private void twitterTimeline(){
        TwitterTimelineFragment twitterTimelineFragment = TwitterTimelineFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.layout_reseau_timeline,twitterTimelineFragment).commit();
    }

    private void instagramTimeline(){
        WebviewReseauFragment webviewReseauFragment = WebviewReseauFragment.newInstance("https://www.instagram.com/followrevi/");
        getFragmentManager().beginTransaction().replace(R.id.layout_reseau_timeline,webviewReseauFragment).commit();
    }

    private void facebookTimeline(){
        WebviewReseauFragment webviewReseauFragment = WebviewReseauFragment.newInstance("https://www.facebook.com/FollowRevi-578438115688799/");
        getFragmentManager().beginTransaction().replace(R.id.layout_reseau_timeline,webviewReseauFragment).commit();
    }

    private void setLogo() {
        logoFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/578438115688799")));

                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/FollowRevi-578438115688799/")));
                }
            }
        });

        logoInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/followrevi")));

                } catch (Exception e) {
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

                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/followrevi?lang=fr")));
                }
            }
        });
    }
}
