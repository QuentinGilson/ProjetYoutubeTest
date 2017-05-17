package technifutur.be.projetyoutube.item;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.UserMessage;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.model.youtube.MyDate;

/**
 * Created by quentin on 08-04-17.
 */

public class ChatInstantaneItem extends AbstractItem<ChatInstantaneItem,ChatInstantaneItem.ViewHolder> {

    public interface ButtonMessageListener{
        void citation(String message);
        void messagePrive();
    }

    private UserMessage userMessage;
    private Context context;
    private ButtonMessageListener listener;

    public ChatInstantaneItem(UserMessage userMessage, Context context, ButtonMessageListener listener) {
        this.userMessage = userMessage;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getType() {
        return R.id.chat_instantane_row_textview_username;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.chat_instantane_message_row;
    }

    @Override
    public void bindView(final ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.refresh(userMessage,context,listener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.citation.getVisibility() == View.GONE) {
                    holder.citation.setVisibility(View.VISIBLE);
                    holder.messagePrive.setVisibility(View.VISIBLE);
                }else{
                    holder.citation.setVisibility(View.GONE);
                    holder.messagePrive.setVisibility(View.GONE);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.chat_instantane_row_textview_username)
        TextView username;
        @BindView(R.id.chat_instantane_row_textview_usermessage)
        TextView message;
        @BindView(R.id.chat_instantane_row_image)
        ImageView imageUser;
        @BindView(R.id.chat_instantane_row_textview_date)
        TextView date;
        @BindView(R.id.button_cardview_citation)
        Button citation;
        @BindView(R.id.button_cardview_message_prive)
        Button messagePrive;
        @BindView(R.id.chat_instantane_row_cardview)
        CardView cardview;
        @BindView(R.id.cardview_citation)
        CardView cardviewCitation;
        @BindView(R.id.cardview_citation_textview)
        TextView citationTextviex;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void refresh(final UserMessage userMessage, Context context, final ButtonMessageListener listener){
            username.setText(userMessage.getSender().getNickname());
            if(context!=null) {
                Glide.with(context).load(userMessage.getSender().getProfileUrl()).into(imageUser);
            }
            String dateString = MyDate.timeAgo(new Date(userMessage.getCreatedAt()));
            date.setText(dateString);
            citation.setVisibility(View.GONE);
            messagePrive.setVisibility(View.GONE);
            cardviewCitation.setVisibility(View.GONE);

            if(userMessage.getMessage().contains("citation420")){
                cardviewCitation.setVisibility(View.VISIBLE);
                String[] cita = userMessage.getMessage().split("citation420");
                citationTextviex.setText(cita[1]);
                message.setText(cita[0]);
            } else {
                message.setText(userMessage.getMessage());

            }

            citation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.citation(userMessage.getSender().getNickname() + " : \"" + message.getText().toString() + "\"");
                }
            });
            messagePrive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.messagePrive();
                }
            });
        }
    }
}
