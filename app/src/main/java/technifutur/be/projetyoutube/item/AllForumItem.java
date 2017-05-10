package technifutur.be.projetyoutube.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;
import com.sendbird.android.OpenChannel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;

/**
 * Created by quentin on 09-04-17.
 */

public class AllForumItem extends AbstractItem<AllForumItem,AllForumItem.ViewHolder>{

    public interface OnItemClick{
        void onClick(OpenChannel openChannel);
    }

    private OpenChannel openChannel;
    private OnItemClick onItemClick;


    public AllForumItem(OpenChannel openChannel, OnItemClick onItemClick) {
        this.openChannel = openChannel;
        this.onItemClick = onItemClick;
    }

    @Override
    public int getType() {
        return R.id.cardview_forum;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.all_forum_row;
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.refresh(openChannel);
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void refresh(OpenChannel openChannel){
            if(openChannel!=null){
                name.setText(openChannel.getName());

            }
        }

    }
}
