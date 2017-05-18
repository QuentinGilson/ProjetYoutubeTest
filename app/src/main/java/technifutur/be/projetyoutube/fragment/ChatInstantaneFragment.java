package technifutur.be.projetyoutube.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.BaseMessage;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.UserMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.item.ChatInstantaneItem;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatInstantaneFragment extends Fragment implements SendBirdManager.ChatInstantaneListener, ChatInstantaneItem.ButtonMessageListener {

    public interface ChatInstantaneDestroyListener {
        void onDestroyFragmentChat();
    }

    @BindView(R.id.editttext)
    EditText editttext;
    @BindView(R.id.recyclerview_chat_instantane)
    RecyclerView recyclerviewChatInstantane;
    @BindView(R.id.popup_image_citation)
    ImageView popupImageCitation;
    @BindView(R.id.popup_citation_textview)
    TextView popupCitationTextview;
    @BindView(R.id.cardview_citation_popup)
    CardView cardviewCitationPopup;
    private List<UserMessage> allUserMessage;
    private SendBirdManager sendBirdManager;
    private FastItemAdapter<AbstractItem> messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private OpenChannel openChannel;
    private String citation = "";
    private String firstMessage;
    private ChatInstantaneDestroyListener chatInstantaneDestroyListener;

    public ChatInstantaneFragment() {
        allUserMessage = new ArrayList<>();
    }

    public static ChatInstantaneFragment newInstance(OpenChannel openChannel) {
        ChatInstantaneFragment fragment = new ChatInstantaneFragment();
        fragment.openChannel = openChannel;
        return fragment;
    }

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }

    public void setChatInstantaneDestroyListener(ChatInstantaneDestroyListener chatInstantaneDestroyListener) {
        this.chatInstantaneDestroyListener = chatInstantaneDestroyListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_instantane, container, false);
        ButterKnife.bind(this, view);

        sendBirdManager = SendBirdManager.getSendBirdManager();
        sendBirdManager.setChatInstantaneListener(this);
        sendBirdManager.enterChatInstantane(openChannel);

        messageAdapter = new FastItemAdapter<>();
        recyclerviewChatInstantane.setAdapter(messageAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setReverseLayout(true);
        recyclerviewChatInstantane.setLayoutManager(linearLayoutManager);

        popupImageCitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeCitationPopup();
            }
        });

        return view;
    }

    @OnClick(R.id.button_send_message)
    public void onClick() {
        if(!editttext.getText().toString().isEmpty()){
            sendBirdManager.sendMessageToChatInstantane(editttext.getText().toString()+citation, openChannel);
            editttext.setText("");
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            closeCitationPopup();
        }
    }

    public void closeCitationPopup() {
        ViewGroup.LayoutParams params = cardviewCitationPopup.getLayoutParams();
        params.height = 0;
        cardviewCitationPopup.setLayoutParams(params);
        citation = "";
    }

    @Override
    public void connectedToChat(OpenChannel openChannel) {
        this.openChannel = openChannel;
        if (firstMessage != null) {
            sendBirdManager.sendMessageToChatInstantane(firstMessage, openChannel);
            firstMessage = null;
        }
        sendBirdManager.getAllChatInstantaneMessage(openChannel);
    }

    @Override
    public void messageReceived(BaseMessage baseMessage) {
        if (baseMessage instanceof UserMessage) {
            allUserMessage.add((UserMessage) baseMessage);
            messageAdapter.add(0, new ChatInstantaneItem((UserMessage) baseMessage, getContext(), this));
            recyclerviewChatInstantane.smoothScrollToPosition(0);
        }
    }

    @Override
    public void messageSent(UserMessage userMessage) {
        allUserMessage.add(userMessage);
        ChatInstantaneItem item = new ChatInstantaneItem(userMessage, getContext(), this);
        messageAdapter.add(0, item);
        recyclerviewChatInstantane.smoothScrollToPosition(0);
    }

    @Override
    public void allMessageSent(List<BaseMessage> list) {
        allUserMessage.clear();
        for (BaseMessage message : list) {
            if (message instanceof UserMessage) {
                allUserMessage.add((UserMessage) message);
            }
        }
        refreshAllMessage();//a ameliore : list allusermessage inutile ? refresh inutile ?
    }

    public void refreshAllMessage() {
        messageAdapter.clear();
        for (UserMessage message : allUserMessage) {
            ChatInstantaneItem chatInstantaneItem = new ChatInstantaneItem(message, getContext(), this);
            messageAdapter.add(chatInstantaneItem);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //sendBirdManager.leaveChatInstantane(openChannel);
        SendBirdManager.getSendBirdManager().setChatInstantaneListener(null);
        chatInstantaneDestroyListener.onDestroyFragmentChat();
    }

    @Override
    public void citation(String message) {
        cardviewCitationPopup.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        popupCitationTextview.setText(message);
        citation = "citation420" + message;
    }

    @Override
    public void messagePrive() {

    }
}
