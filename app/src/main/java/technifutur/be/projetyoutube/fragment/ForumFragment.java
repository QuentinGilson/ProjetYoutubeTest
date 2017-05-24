package technifutur.be.projetyoutube.fragment;


import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.SendBird;
import com.sendbird.android.UserMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class ForumFragment extends Fragment implements AllForumItem.OnItemClick, SendBirdManager.GestionForum, ChatInstantaneFragment.ChatInstantaneDestroyListener {

    @BindView(R.id.recyclerview_all_forum)
    RecyclerView recyclerviewAllForum;
    @BindView(R.id.textview_number_of_people_online)
    TextView textviewNumberOfPeopleOnline;
    private SendBirdManager sendBirdManager;
    private FastItemAdapter<AbstractItem> allForumAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String firstMessage;
    private List<Long> allLastMessageDate;
    private boolean firstTimeStartFragment = false;

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

        sendBirdManager = SendBirdManager.getSendBirdManager();
        sendBirdManager.setGestionForum(this);

        allForumAdapter = new FastItemAdapter<AbstractItem>();
        recyclerviewAllForum.setAdapter(allForumAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerviewAllForum.setLayoutManager(linearLayoutManager);

        if(!firstTimeStartFragment) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
            dividerItemDecoration.setDrawable(drawable);
            recyclerviewAllForum.addItemDecoration(dividerItemDecoration);
            firstTimeStartFragment = true;
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    public void refresh(){
        connectingToSenbird();
        allLastMessageDate = new ArrayList<>();
        allForumAdapter.clear();
    }

    public void connectingToSenbird() {
        sendBirdManager.getAllOpenChannel();
        sendBirdManager.getNumberOfUserOnline();
    }

    @OnClick(R.id.button_add_new_forum)
    public void onViewClicked() {
        CreateNewForumFragment createNewForumFragment = CreateNewForumFragment.newInstance(new CreateNewForumFragment.ForumCreatedListener() {
            @Override
            public void forumCreated(String name, String message, String imagePath) {
                getFragmentManager().popBackStack();
                firstMessage = message;
                sendBirdManager.createOpenChannel(name, imagePath);
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.activity_pager, createNewForumFragment).addToBackStack(null).commit();
    }


    @Override
    public void onClick(OpenChannel openChannel) {
        ChatInstantaneFragment chatInstantaneFragment = ChatInstantaneFragment.newInstance(openChannel);
        chatInstantaneFragment.setChatInstantaneDestroyListener(this);
        getFragmentManager().beginTransaction().replace(R.id.activity_pager, chatInstantaneFragment).addToBackStack(null).commit();
    }

    @Override
    public void allOpenChannel(List<OpenChannel> allopenChannel) {
        if(allopenChannel!=null) {
            allForumAdapter.clear();
            for (OpenChannel openChannel : allopenChannel) {
                sendBirdManager.getLastMessage(openChannel);
            }
        }
    }

    @Override
    public void openChannelCreated(OpenChannel openChannel) {
        allForumAdapter.add(new AllForumItem(openChannel, this,null,getContext()));
        ChatInstantaneFragment chatInstantaneFragment = ChatInstantaneFragment.newInstance(openChannel);
        chatInstantaneFragment.setFirstMessage(firstMessage);
        chatInstantaneFragment.setChatInstantaneDestroyListener(this);
        getFragmentManager().beginTransaction().replace(R.id.activity_pager, chatInstantaneFragment).addToBackStack(null).commit();
        firstMessage = null;
    }

    @Override
    public void numberOfUserOnline(int cpt) {
        if(cpt<=1){
            textviewNumberOfPeopleOnline.setText((cpt)+" membre connecté");
        }else{
            textviewNumberOfPeopleOnline.setText((cpt)+" membres connectés");
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
        recyclerviewAllForum.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyFragmentChat() {
        refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        firstTimeStartFragment = false;
    }
}
