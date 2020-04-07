package br.com.cpmh.ata.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.auth.UserProfile;

/**
 *
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener
{
	private UserProfile userProfile;

	protected TextView userName;
	protected TextView userTaxpayerNumber;

	/**
	 *
	 */
	public UserProfileFragment() {}

	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getArguments() != null)
		{
			userProfile = (UserProfile) getArguments().getParcelable(getString(R.string.bundle_parcelable_user_profile_key));
		}
	}

	/**
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 *
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_user_profile, container, false);
	}

	/**
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		userName = view.findViewById(R.id.user);
		userName.setText(userProfile.getFullName());

		userTaxpayerNumber = view.findViewById(R.id.user_taxpayer_number);
		userTaxpayerNumber.setText(userProfile.getTaxpayerNumber());
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{

	}

	/**
	 * @return
	 */
	public UserProfile getUserProfile()
	{
		return userProfile;
	}

	/**
	 * @param userProfile
	 */
	public void setUserProfile(UserProfile userProfile)
	{
		this.userProfile = userProfile;
	}
}
