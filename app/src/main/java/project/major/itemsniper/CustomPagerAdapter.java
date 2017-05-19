package project.major.itemsniper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Kurt on 3/26/2017.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();

    public CustomPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }

    public void addFragment(Fragment f){
        fragments.add(f);
        notifyDataSetChanged();
    }
    public void setDataSource(ArrayList<Fragment> fragments){
        this.fragments = fragments;
        notifyDataSetChanged();
    }
    public void removeFragment(Fragment f){
        fragments.remove(f);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
