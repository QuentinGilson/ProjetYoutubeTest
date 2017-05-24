package technifutur.be.projetyoutube.fragment;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.adapter.PagerAdapter;
import technifutur.be.projetyoutube.sendBird.SendBirdManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment implements SendBirdManager.NotifListener{


    @BindView(R.id.main_tab_layout)
    TabLayout mainTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    private PagerAdapter pagerAdapter;
    private SendBirdManager sendBirdManager;

    public PagerFragment() {
        // Required empty public constructor
    }

    public static PagerFragment newInstance() {

        Bundle args = new Bundle();

        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, view);

        sendBirdManager = SendBirdManager.getSendBirdManager();
        sendBirdManager.setNotifListener(this);

        pagerAdapter = new PagerAdapter(getFragmentManager());
        mainViewpager.setAdapter(pagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewpager);
        mainTabLayout.getTabAt(2).select();

        mainTabLayout.getTabAt(0).setIcon(R.drawable.play);
        mainTabLayout.getTabAt(1).setIcon(R.drawable.chat);
        mainTabLayout.getTabAt(2).setIcon(R.drawable.home);
        mainTabLayout.getTabAt(3).setIcon(R.drawable.social);
        mainTabLayout.getTabAt(4).setIcon(R.drawable.settings);

        mainViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPageSelected(int position) {
                switch(position){
                    case 1:
                        ((ForumFragment)pagerAdapter.getItem(1)).refresh();
                        mainTabLayout.getTabAt(1).getIcon().setTint(Color.BLACK);
                        break;
                    case 2:
                        ((HomeFragment)pagerAdapter.getItem(2)).refresh();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void messageReceived() {
        mainTabLayout.getTabAt(1).getIcon().setTint(Color.RED);
    }

}
