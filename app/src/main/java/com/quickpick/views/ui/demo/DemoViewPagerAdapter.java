package com.quickpick.views.ui.demo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.quickpick.views.ui.dashboard.tabs.DrinksFragment;
import com.quickpick.views.ui.dashboard.tabs.EatsFragment;

import java.util.ArrayList;

/**
 *
 */
public class DemoViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragments = new ArrayList<>();
	private Fragment currentFragment;

	public DemoViewPagerAdapter(FragmentManager fm) {
		super(fm);

		fragments.clear();
		fragments.add(DrinksFragment.newInstance(0));
		fragments.add(EatsFragment.newInstance(1));
		fragments.add(DrinksFragment.newInstance(2));
		fragments.add(DrinksFragment.newInstance(3));
		fragments.add(DrinksFragment.newInstance(4));
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		if (getCurrentFragment() != object) {
			currentFragment = ((Fragment) object);
		}
		super.setPrimaryItem(container, position, object);
	}

	/**
	 * Get the current fragment
	 */
	public Fragment getCurrentFragment() {
		return currentFragment;
	}
}