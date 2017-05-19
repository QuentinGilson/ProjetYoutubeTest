package technifutur.be.projetyoutube.item;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.model.youtube.Achievement;

/**
 * Created by student5312 on 18/05/17.
 */

public class AchievementItem extends AbstractItem<AchievementItem,AchievementItem.ViewHodler>{

    private Achievement achievement;

    public AchievementItem(Achievement achievement) {
        this.achievement = achievement;
    }

    @Override
    public int getType() {
        return R.id.image_achievement;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.achievement_row;
    }

    @Override
    public void bindView(ViewHodler holder, List<Object> payloads) {
        super.bindView(holder, payloads);
        holder.refresh(achievement);
    }

    public static class ViewHodler extends RecyclerView.ViewHolder{

        @BindView(R.id.image_achievement)
        ImageView image;
        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void refresh(Achievement achievement){
            image.setImageResource(achievement.getImage());

            ColorMatrix matrix = new ColorMatrix();
            if(achievement.getCount()==0){
                matrix.setSaturation(0);
            }else{
                matrix.setSaturation(1);
            }
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            image.setColorFilter(filter);
        }
    }
}
