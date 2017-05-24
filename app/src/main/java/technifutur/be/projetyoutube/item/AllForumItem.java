package technifutur.be.projetyoutube.item;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.OpenChannel;
import com.sendbird.android.UserMessage;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.model.youtube.MyDate;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * Created by quentin on 09-04-17.
 */

public class AllForumItem extends AbstractItem<AllForumItem,AllForumItem.ViewHolder>{

    public interface OnItemClick{
        void onClick(OpenChannel openChannel);
    }

    private OpenChannel openChannel;
    private OnItemClick onItemClick;
    private UserMessage userMessage;
    private Context context;

    public AllForumItem(OpenChannel openChannel, OnItemClick onItemClick, UserMessage userMessage, Context context) {
        this.openChannel = openChannel;
        this.onItemClick = onItemClick;
        this.userMessage = userMessage;
        this.context = context;
    }

    @Override
    public int getType() {
        return R.id.main_layout_all_forum;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.all_forum_row;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.refresh(openChannel,userMessage,context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(openChannel);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.textview_cardview_all_forum)
        TextView name;
        @BindView(R.id.textview_cardview_all_forum_number_message)
        TextView numberMessage;
        @BindView(R.id.textview_cardview_all_forum_last_messsage)
        TextView lastMessage;
        @BindView(R.id.textview_cardview_all_forum_date)
        TextView date;
        @BindView(R.id.textview_cardview_all_forum_image)
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void refresh(OpenChannel openChannel,UserMessage userMessage,Context context){
            Glide.with(context).load(openChannel.getCoverUrl()).into(image);
            if(openChannel!=null){
                name.setText(openChannel.getName());
            }
            if(userMessage!=null){
                date.setText(MyDate.timeAgo(new Date(userMessage.getCreatedAt())));
                String message = userMessage.getMessage();
                if(message.contains("citation420")){
                    lastMessage.setText(message.split("citation420")[0]);
                }else{
                    lastMessage.setText(message);
                }
            }
        }
    }
}
