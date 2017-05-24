package technifutur.be.projetyoutube.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import technifutur.be.projetyoutube.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwitterTimelineFragment extends Fragment {


    @BindView(R.id.twitter_timeline)
    ListView twitterTimeline;
    Unbinder unbinder;

    public TwitterTimelineFragment() {
        // Required empty public constructor
    }

    public static TwitterTimelineFragment newInstance() {
        TwitterTimelineFragment fragment = new TwitterTimelineFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_twitter_timeline, container, false);
        unbinder = ButterKnife.bind(this, view);

        final UserTimeline userTimeline = new UserTimeline.Builder().screenName("FollowRevi").build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext()).setTimeline(userTimeline).build();
        twitterTimeline.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
