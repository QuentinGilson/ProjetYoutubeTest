package technifutur.be.projetyoutube.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.animation.ResizeAnimationHeight;
import technifutur.be.projetyoutube.animation.ResizeAnimationWidth;
import technifutur.be.projetyoutube.model.youtube.Achievement;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowAchievementFragment extends Fragment {


    @BindView(R.id.image_achievement_reveal)
    ImageView imageAchievementReveal;
    @BindView(R.id.text_achievement_reveal)
    TextView textAchievementReveal;
    @BindView(R.id.layout_achievement_animate)
    RelativeLayout layoutAchievementAnimate;
    @BindView(R.id.layout_reveal_achievement)
    RelativeLayout layoutRevealAchievement;
    private Achievement achievement;

    public ShowAchievementFragment() {
        // Required empty public constructor
    }

    public static ShowAchievementFragment newInstance(Achievement achievement) {
        ShowAchievementFragment fragment = new ShowAchievementFragment();
        fragment.achievement = achievement;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_achievement, container, false);
        ButterKnife.bind(this, view);

        textAchievementReveal.setText(achievement.getName());
        imageAchievementReveal.setImageResource(achievement.getImage());

        ResizeAnimationHeight a = new ResizeAnimationHeight(layoutAchievementAnimate);
        a.setDuration(400);
        a.setParams(layoutAchievementAnimate.getLayoutParams().height, 600);

        ResizeAnimationWidth anim = new ResizeAnimationWidth(layoutAchievementAnimate);
        anim.setDuration(400);
        anim.setParams(layoutAchievementAnimate.getLayoutParams().width,500);

        AnimationSet animSet = new AnimationSet(true);
        animSet.addAnimation(a);
        animSet.addAnimation(anim);
        layoutAchievementAnimate.startAnimation(animSet);

        return view;
    }

    @OnClick(R.id.layout_reveal_achievement)
    public void onClick() {
        getFragmentManager().popBackStack();
    }
}
