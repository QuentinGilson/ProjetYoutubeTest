package technifutur.be.projetyoutube.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by student5312 on 19/05/17.
 */

public class ResizeAnimationWidth extends Animation {

    private int startWidth;
    private int deltaWidth; // distance between start and end height
    private View view;

    public ResizeAnimationWidth(View v) {
        this.view = v;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        view.getLayoutParams().width = (int) (startWidth + deltaWidth * interpolatedTime);
        view.requestLayout();
    }

    public void setParams(int start, int end) {

        this.startWidth = start;
        deltaWidth = end - startWidth;
    }

    @Override
    public void setDuration(long durationMillis) {
        super.setDuration(durationMillis);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
