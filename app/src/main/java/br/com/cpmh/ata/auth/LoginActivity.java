package br.com.cpmh.ata.auth;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.UnityPlayerFragment;
import br.com.cpmh.ata.utilities.ViewPagerAdapter;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements SignUpFragment.OnSignUpAttemptListener, SignInFragment.OnSignInAttemptListener
{
	private static final String TAG = "LoginActivity";

	private ViewPagerAdapter viewPagerAdapter;
	private ViewPager viewPager;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		viewPagerAdapter.addFragment(new SignInFragment());
		viewPagerAdapter.addFragment(new SignUpFragment());

		viewPager = findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(viewPagerAdapter);
	}

	/**
	 * @return
	 */
	public ViewPagerAdapter getViewPagerAdapter()
	{
		return viewPagerAdapter;
	}

	/**
	 * @param viewPagerAdapter
	 */
	public void setViewPagerAdapter(ViewPagerAdapter viewPagerAdapter)
	{
		this.viewPagerAdapter = viewPagerAdapter;
	}

	/**
	 * @return
	 */
	public ViewPager getViewPager()
	{
		return viewPager;
	}

	/**
	 * @param viewPager
	 */
	public void setViewPager(ViewPager viewPager)
	{
		this.viewPager = viewPager;
	}
}
