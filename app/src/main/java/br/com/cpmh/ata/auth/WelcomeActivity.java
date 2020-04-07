package br.com.cpmh.ata.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.dashboard.DashboardActivity;

/**
 *
 */
public class WelcomeActivity extends AppCompatActivity implements Runnable, FirebaseAuthHelper.OnUserProfileFetchListener
{
	private static final String TAG = "WelcomeActivity";

	private static final int TIMEOUT = 1000;

	protected Handler handler;

	protected FirebaseAuth auth;

	protected FirebaseAuthHelper authHelper;

	protected UserProfile userProfile;

	protected TextView successfullyLoginMessage;

	protected TextView welcomeMessage;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		handler = new Handler();

		auth = FirebaseAuth.getInstance();
		authHelper = new FirebaseAuthHelper();
		authHelper.setOnUserProfileFetchListener(this);

		successfullyLoginMessage = findViewById(R.id.successfully_login_message);
		welcomeMessage = findViewById(R.id.welcome_message);

		Intent intent = getIntent();

		userProfile = (UserProfile) intent.getParcelableExtra(getString(R.string.intent_parcelable_user_profile_key));

		if (userProfile == null)
		{
			authHelper.retrieveUserProfile(auth.getCurrentUser());
		}
		else
		{
			successfullyLoginMessage.setText(String.format(getString(R.string.successfully_login_message), Objects.requireNonNull(userProfile.getFirstName())));
			successfullyLoginMessage.setVisibility(View.VISIBLE);

			welcomeMessage.setVisibility(View.VISIBLE);

			handler.postDelayed(this, TIMEOUT);
		}
	}

	/**
	 *
	 */
	@Override
	public void run()
	{
		displayDashboardActivity();
	}

	/**
	 * @param userProfile
	 */
	@Override
	public void onRetrievedUserProfile(UserProfile userProfile)
	{
		this.userProfile = userProfile;

		successfullyLoginMessage.setText(String.format(getString(R.string.successfully_login_message), Objects.requireNonNull(userProfile.getFirstName())));
		successfullyLoginMessage.setVisibility(View.VISIBLE);

		welcomeMessage.setVisibility(View.VISIBLE);

		handler.postDelayed(this, TIMEOUT);
	}

	/**
	 *
	 */
	protected void displayDashboardActivity()
	{
		Intent intent = new Intent(WelcomeActivity.this, DashboardActivity.class);

		if (userProfile != null)
		{
			Log.i(TAG, "Intent userName profile is not null.");
			intent.putExtra(getString(R.string.intent_parcelable_user_profile_key), (Parcelable) userProfile);
		}
		startActivity(intent);
		finish();
	}
}
