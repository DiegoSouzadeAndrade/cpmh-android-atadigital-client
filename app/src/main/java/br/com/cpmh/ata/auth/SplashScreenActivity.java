package br.com.cpmh.ata.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import br.com.cpmh.ata.R;

/**
 *
 */
public class SplashScreenActivity extends AppCompatActivity implements Runnable, FirebaseAuthHelper.OnUserIdTokenFetchListener, FirebaseAuthHelper.OnUserProfileFetchListener
{
	private static final String TAG = "SplashScreenActivity";
	private static final int TIMEOUT = 2000;

	protected Handler handler;

	protected FirebaseAuth auth;
	protected FirebaseAuthHelper authHelper;

	protected FirebaseRemoteConfig remoteConfig;

	protected UserProfile userProfile;

	protected ProgressBar progressBar;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		handler = new Handler();

		auth = FirebaseAuth.getInstance();
		authHelper = new FirebaseAuthHelper();
		authHelper.setOnUserIdTokenFetchListener(this);
		authHelper.setOnUserProfileFetchListener(this);

		remoteConfig = FirebaseRemoteConfig.getInstance();
		remoteConfig.setDefaults(R.xml.remote_config_defaults);

		progressBar = findViewById(R.id.progress_bar);

		remoteConfig.activateFetched();

		if (hasUrgentUpdatePending())
		{
			Task<Void> fetchTask = remoteConfig.fetch(0);
			fetchTask.addOnCompleteListener(new FirebaseRemoteConfigFetchListener());

			displayProgressBar(true);
		}
		else
		{
			Log.i(TAG, "Remote config does not have an urgent updated pending.");

			remoteConfig.fetch(TimeUnit.HOURS.toSeconds(12));

			authHelper.validateUser(auth.getCurrentUser());
		}
	}

	/**
	 *
	 */
	@Override
	public void run()
	{
		authHelper.validateUser(auth.getCurrentUser());
	}

	/**
	 *
	 */
	@Override
	public void onValidUser()
	{
		authHelper.retrieveUserProfile(auth.getCurrentUser());
	}

	/**
	 *
	 */
	@Override
	public void onInvalidUser()
	{
		authHelper.displayLoginActivity();
		//
	}

	/**
	 * @param userProfile
	 */
	@Override
	public void onRetrievedUserProfile(UserProfile userProfile)
	{
		this.userProfile = userProfile;
		this.displayWelcomeActivity();
	}

	/**
	 * @return
	 */
	protected boolean hasUrgentUpdatePending()
	{
		SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE);

		if (!sharedPreferences.contains(getString(R.string.urgent_update_pending_key)))
		{
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean(getString(R.string.urgent_update_pending_key), true);
			editor.apply();

			Log.i(TAG, "Created the urgent updated pending flag.");
		}

		if (sharedPreferences.getBoolean(getString(R.string.urgent_update_pending_key), true))
		{
			Log.i(TAG, "Remote config has an urgent updated pending.");

			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * @param value
	 */
	protected void displayProgressBar(boolean value)
	{
		progressBar.setVisibility(value ? View.VISIBLE : View.GONE);
	}

	/**
	 *
	 */
	protected void displayWelcomeActivity()
	{
		Intent intent = new Intent(SplashScreenActivity.this, WelcomeActivity.class);
		if (userProfile != null)
		{
			intent.putExtra(getString(R.string.intent_parcelable_user_profile_key), (Parcelable) userProfile);
		}
		startActivity(intent);
		finish();
	}

	/**
	 *
	 */
	private class FirebaseRemoteConfigFetchListener implements OnCompleteListener<Void>
	{
		/**
		 * @param task
		 */
		@Override
		public void onComplete(@NonNull Task<Void> task)
		{
			if (task.isSuccessful())
			{
				Log.i(TAG, "Successfully fetched the remote config update.");
			}
			else
			{
				Log.w(TAG, "Failed to fetch the remote config update.");

				try
				{
					throw Objects.requireNonNull(task.getException());
				}
				catch (Exception exception)
				{
					// TODO: Implement a Firebase Crashlytics report here.
				}
			}

			handler.postDelayed(SplashScreenActivity.this, TIMEOUT);
		}
	}
}
