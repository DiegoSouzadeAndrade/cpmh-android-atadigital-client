package br.com.cpmh.ata.utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
	private final List<Fragment> fragments;

	/**
	 * @param fragmentManager
	 */
	public ViewPagerAdapter(FragmentManager fragmentManager)
	{
		super(fragmentManager);
		fragments = new ArrayList<>();
	}

	/**
	 * @param index
	 *
	 * @return
	 */
	@Override
	public Fragment getItem(int index)
	{
		return fragments.get(index);
	}

	/**
	 * @return
	 */
	@Override
	public int getCount()
	{
		return fragments.size();
	}

	/**
	 * @param fragment
	 */
	public void addFragment(Fragment fragment)
	{
		fragments.add(fragment);
	}

	/**
	 * @param index
	 * @param fragment
	 */
	public void addFragment(int index, Fragment fragment)
	{
		fragments.add(index, fragment);
	}

	/**
	 * @param fragment
	 */
	public void removeFragment(Fragment fragment)

	{
		fragments.remove(fragment);
	}

	/**
	 * @param index
	 */
	public void removeFragment(int index)
	{
		fragments.remove(index);
	}
}
