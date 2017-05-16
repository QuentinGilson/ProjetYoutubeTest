package technifutur.be.projetyoutube.sendBird;


import android.util.Log;

import com.sendbird.android.AdminMessage;
import com.sendbird.android.BaseChannel;
import com.sendbird.android.BaseMessage;

import com.sendbird.android.OpenChannel;
import com.sendbird.android.OpenChannelListQuery;
import com.sendbird.android.PreviousMessageListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.sendbird.android.UserListQuery;
import com.sendbird.android.UserMessage;

import java.util.List;


/**
 * Created by student5312 on 28/03/17.
 */

public class SendBirdManager {

    public interface ChatInstantaneListener{
        void connectedToChat(OpenChannel openChannel);
        void messageReceived(BaseMessage baseMessage);
        void messageSent(UserMessage userMessage);
        void allMessageSent(List<BaseMessage> list);
    }

    public interface GestionForum{
        void connectedToSendBird();
        void allOpenChannel(List<OpenChannel> allopenChannel);
        void openChannelCreated(OpenChannel openChannel);
        void numberOfUserOnline(int cpt);
        void getLastMessage(UserMessage userMessage,OpenChannel openChannel);
    }

    public interface NotifListener{
        void messageReceived();
    }

    private static SendBirdManager sendBirdManager;
    private ChatInstantaneListener chatInstantaneListener;
    private GestionForum gestionForum;
    private NotifListener notifListener;

    public static SendBirdManager getSendBirdManager(){
        if(sendBirdManager==null){
            sendBirdManager = new SendBirdManager();
        }
        return sendBirdManager;
    }

    public void setChatInstantaneListener(ChatInstantaneListener chatInstantaneListener) {
        this.chatInstantaneListener = chatInstantaneListener;
    }

    public void setNotifListener(NotifListener notifListener) {
        this.notifListener = notifListener;
    }

    public void setGestionForum(GestionForum gestionForum) {
        this.gestionForum = gestionForum;
    }

    public void connectSendBird(final technifutur.be.projetyoutube.model.youtube.User myuser){
        SendBird.connect(myuser.getId(), new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                gestionForum.connectedToSendBird();
                updateUserSetting(myuser.getName(),myuser.getImage());
            }
        });
    }

    public static void updateUserSetting(String name, String image){
        SendBird.updateCurrentUserInfo(name,"http://"+image, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {

            }
        });
    }

    public void enterChatInstantane(final OpenChannel openChannel){
        openChannel.enter(new OpenChannel.OpenChannelEnterHandler() {
            @Override
            public void onResult(SendBirdException e) {
                receiveMessage();
                chatInstantaneListener.connectedToChat(openChannel);
            }
        });
    }

    public void createOpenChannel(String name){
        OpenChannel.createChannel(name, null,null,null,new OpenChannel.OpenChannelCreateHandler() {
            @Override
            public void onResult(OpenChannel openChannel, SendBirdException e) {
                gestionForum.openChannelCreated(openChannel);
            }
        });
    }

    public void getAllOpenChannel(){
        OpenChannelListQuery channelListQuery = OpenChannel.createOpenChannelListQuery();
        channelListQuery.next(new OpenChannelListQuery.OpenChannelListQueryResultHandler() {
            @Override
            public void onResult(List<OpenChannel> channels, SendBirdException e) {
                gestionForum.allOpenChannel(channels);
            }
        });
    }

    private void receiveMessage() {
        SendBird.addChannelHandler("channel_handler", new SendBird.ChannelHandler() {
            @Override
            public void onMessageReceived(BaseChannel baseChannel, BaseMessage baseMessage) {
                if(chatInstantaneListener!=null){
                    chatInstantaneListener.messageReceived(baseMessage);
                }else{
                    notifListener.messageReceived();
                }

            }
        });
    }

    public void sendMessageToChatInstantane(String message,OpenChannel openChannel){
        openChannel.sendUserMessage(message, new BaseChannel.SendUserMessageHandler() {
            @Override
            public void onSent(UserMessage userMessage, SendBirdException e) {
                if (chatInstantaneListener != null) {
                    chatInstantaneListener.messageSent(userMessage);
                }
            }
        });
    }

    public void getAllChatInstantaneMessage(OpenChannel openChannel){
        PreviousMessageListQuery previousMessageListQuery = openChannel.createPreviousMessageListQuery();
        previousMessageListQuery.load(200, true, new PreviousMessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> list, SendBirdException e) {
                chatInstantaneListener.allMessageSent(list);
            }
        });
    }

    public void leaveChatInstantane(OpenChannel openChannel){
        openChannel.exit(new OpenChannel.OpenChannelExitHandler() {
            @Override
            public void onResult(SendBirdException e) {

            }
        });
    }

    public void getNumberOfUserOnline(){
        UserListQuery userListQuery = SendBird.createUserListQuery();
        userListQuery.next(new UserListQuery.UserListQueryResultHandler() {
            @Override
            public void onResult(List<User> list, SendBirdException e) {
                int cpt=0;
                for(User user : list){
                    if(user.getConnectionStatus().equals(User.ConnectionStatus.ONLINE)){
                        cpt++;
                    }
                }
                gestionForum.numberOfUserOnline(cpt);
            }
        });
    }

    public void getLastMessage(final OpenChannel openChannel){
        PreviousMessageListQuery previousMessageListQuery = openChannel.createPreviousMessageListQuery();
        previousMessageListQuery.load(1, true, new PreviousMessageListQuery.MessageListQueryResult() {
            @Override
            public void onResult(List<BaseMessage> list, SendBirdException e) {
                if(list.size()>0){
                    BaseMessage message = list.get(0);
                    if(message instanceof UserMessage){
                        gestionForum.getLastMessage((UserMessage) message,openChannel);
                    }
                }
            }
        });
    }

}