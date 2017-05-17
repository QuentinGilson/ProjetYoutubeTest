package technifutur.be.projetyoutube.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import technifutur.be.projetyoutube.fragment.AllVideoFragment;
import technifutur.be.projetyoutube.fragment.ForumFragment;
import technifutur.be.projetyoutube.fragment.OptionFragment;
import technifutur.be.projetyoutube.fragment.HomeFragment;
import technifutur.be.projetyoutube.fragment.ReseauFragment;

/**
 * Created by student5312 on 21/03/17.
 */

public class PagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> allFragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);

        allFragments = new ArrayList<>();
        allFragments.add(AllVideoFragment.newInstance());
        allFragments.add(ForumFragment.newInstance());
        allFragments.add(HomeFragment.newInstance());
        allFragments.add(ReseauFragment.newInstance());
        allFragments.add(OptionFragment.newInstance());

    }

    @Override
    public Fragment getItem(int position) {
        return allFragments.get(position);
    }

    @Override
    public int getCount() {
        return allFragments.size();
    }


}
