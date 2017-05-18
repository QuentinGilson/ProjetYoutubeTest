package technifutur.be.projetyoutube.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by student5312 on 18/05/17.
 */

public class AchievementItem extends AbstractItem<AchievementItem,AchievementItem.ViewHodler>{

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void bindView(ViewHodler holder, List<Object> payloads) {
        super.bindView(holder, payloads);
    }

    public static class ViewHodler extends RecyclerView.ViewHolder{

        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void refresh(){

        }
    }
}
