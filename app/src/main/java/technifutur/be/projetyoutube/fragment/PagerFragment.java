package technifutur.be.projetyoutube.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import technifutur.be.projetyoutube.R;
import technifutur.be.projetyoutube.adapter.PagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PagerFragment extends Fragment {


    @BindView(R.id.main_tab_layout)
    TabLayout mainTabLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;
    private PagerAdapter pagerAdapter;

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

        pagerAdapter = new PagerAdapter(getFragmentManager());
        mainViewpager.setAdapter(pagerAdapter);
        mainTabLayout.setupWithViewPager(mainViewpager);
        mainTabLayout.getTabAt(2).select();

        mainTabLayout.getTabAt(0).setIcon(R.drawable.play);
        mainTabLayout.getTabAt(1).setIcon(R.drawable.chat);
        mainTabLayout.getTabAt(2).setIcon(R.drawable.home);
        mainTabLayout.getTabAt(3).setIcon(R.drawable.social);
        mainTabLayout.getTabAt(4).setIcon(R.drawable.settings);

        return view;
    }

}
