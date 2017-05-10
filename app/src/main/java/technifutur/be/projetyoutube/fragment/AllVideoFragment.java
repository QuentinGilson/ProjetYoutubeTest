package technifutur.be.projetyoutube.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.data.VideoRequest;
import technifutur.be.projetyoutube.item.VideoItem;
import technifutur.be.projetyoutube.model.youtube.Video;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllVideoFragment extends Fragment implements VideoRequest.VideoListener {


    @BindView(R.id.allvideo_recyclerview)
    RecyclerView allvideoRecyclerview;
    private FastItemAdapter<AbstractItem> videoAdapter;

    public AllVideoFragment() {
        // Required empty public constructor
    }

    public static AllVideoFragment newInstance() {

        Bundle args = new Bundle();

        AllVideoFragment fragment = new AllVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_video, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        videoAdapter = new FastItemAdapter<>();
        allvideoRecyclerview.setAdapter(videoAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        allvideoRecyclerview.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(getContext(),R.drawable.divider);
        dividerItemDecoration.setDrawable(drawable);
        allvideoRecyclerview.addItemDecoration(dividerItemDecoration);

        VideoRequest.getVideos(this,"50");
    }

    @Override
    public void didReceiveMovie(List<Video> allVideo) {
        for(Video video : allVideo){
            VideoItem videoItem = new VideoItem(video,getContext());
            videoAdapter.add(videoItem);
        }
    }

    @Override
    public void didFail() {

    }
}
