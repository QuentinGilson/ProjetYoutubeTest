package technifutur.be.projetyoutube.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.OpenChannel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.activity.PagerActivity;
import technifutur.be.projetyoutube.item.AllForumItem;
import technifutur.be.projetyoutube.model.youtube.User;
import technifutur.be.projetyoutube.realm.RealmManager;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment implements AllForumItem.OnItemClick, SendBirdManager.GestionForum {

    @BindView(R.id.recyclerview_all_forum)
    RecyclerView recyclerviewAllForum;
    @BindView(R.id.textview_number_of_people_online)
    TextView textviewNumberOfPeopleOnline;
    private SendBirdManager sendBirdManager;
    private FastItemAdapter<AbstractItem> allForumAdapter;
    private LinearLayoutManager linearLayoutManager;
    private User user;

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance() {

        Bundle args = new Bundle();

        ForumFragment fragment = new ForumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        ButterKnife.bind(this, view);

        user = RealmManager.getRealmManager().getUser();
        connectingToSenbird();

        return view;
    }

    public void connectingToSenbird() {
        sendBirdManager = SendBirdManager.getSendBirdManager();
        sendBirdManager.setGestionForum(this);
        sendBirdManager.connectSendBird(user);
    }

    public void loadForum() {
        allForumAdapter = new FastItemAdapter<AbstractItem>();
        recyclerviewAllForum.setAdapter(allForumAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewAllForum.setLayoutManager(linearLayoutManager);

        sendBirdManager.getAllOpenChannel();
    }

    @OnClick(R.id.button_add_new_forum)
    public void onViewClicked() {
        CreateNewForumFragment createNewForumFragment = CreateNewForumFragment.newInstance(new CreateNewForumFragment.ForumCreatedListener() {
            @Override
            public void forumCreated(String name) {
                getFragmentManager().popBackStack();
                sendBirdManager.createOpenChannel(name);
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.activity_pager, createNewForumFragment).addToBackStack(null).commit();
    }


    @Override
    public void onClick(OpenChannel openChannel) {
        ChatInstantaneFragment chatInstantaneFragment = ChatInstantaneFragment.newInstance(openChannel);
        getFragmentManager().beginTransaction().replace(R.id.activity_pager, chatInstantaneFragment).addToBackStack(null).commit();
    }

    @Override
    public void connectedToSendBird() {
        loadForum();
        sendBirdManager.getNumberOfUserOnline();
    }

    @Override
    public void allOpenChannel(List<OpenChannel> allopenChannel) {
        for (OpenChannel openChannel : allopenChannel) {
            allForumAdapter.add(new AllForumItem(openChannel, this));
        }
    }

    @Override
    public void openChannelCreated(OpenChannel openChannel) {
        allForumAdapter.add(new AllForumItem(openChannel, this));
    }

    @Override
    public void numberOfUserOnline(int cpt) {
        textviewNumberOfPeopleOnline.setText((cpt+1)+" membres connectés");
    }
}
