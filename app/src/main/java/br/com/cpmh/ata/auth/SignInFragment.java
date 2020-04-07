package br.com.cpmh.ata.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.Objects;

import br.com.cpmh.ata.R;

/**
 *
 */
public class SignInFragment extends Fragment implements View.OnClickListener, TextInputEditText.OnEditorActionListener
{
	private static final String TAG = "SignInFragment";

	private OnSignInAttemptListener listener;

	private FirebaseAuth auth;

	private FirebaseRemoteConfig remoteConfig;

	private SignInAttemptListener signInAttemptListener;

	private TextInputEditText emailField;

	private TextInputEditText passwordField;

	private Button signInButton;

	private Button signUpButton;

	private Button passwordRecoveryButton;

	/**
	 *
	 */
	public SignInFragment() {}

	/**
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 *
	 * @return
	 */
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_sign_in, container, false);
	}

	/**
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		auth = FirebaseAuth.getInstance();
		remoteConfig = FirebaseRemoteConfig.getInstance();

		emailField = view.findViewById(R.id.email_field);
		emailField.setOnEditorActionListener(this);

		passwordField = view.findViewById(R.id.password_field);
		passwordField.setOnEditorActionListener(this);

		signInButton = view.findViewById(R.id.sign_in_button);
		signInButton.setOnClickListener(this);

		signUpButton = view.findViewById(R.id.sign_up_button);
		signUpButton.setOnClickListener(this);

		passwordRecoveryButton = view.findViewById(R.id.password_recovery_button);
		passwordRecoveryButton.setOnClickListener(this);

		signInAttemptListener = new SignInAttemptListener();
	}

	/**
	 * @param context
	 */
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		if (context instanceof SignInFragment.OnSignInAttemptListener)
		{
			listener = (SignInFragment.OnSignInAttemptListener) context;
		}
		else
		{
			throw new RuntimeException(context.toString().concat(" must implement OnSignInAttemptListener."));
		}
	}

	/**
	 *
	 */
	@Override
	public void onDetach()
	{
		super.onDetach();

		listener = null;
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.sign_in_button:

				signInButton.setEnabled(false);
				passwordRecoveryButton.setEnabled(false);

				if (signInAttemptListener.hasAttempts())
				{
					attemptSignIn();
				}

				break;
			case R.id.sign_up_button:

				break;

				case R.id.password_recovery_button:

				displayAccountRecoveryActivity();

				break;
		}
	}

	/**
	 * @param view
	 * @param actionId
	 * @param event
	 *
	 * @return
	 */
	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
	{
		emailField.setError(null);
		passwordField.setError(null);

		switch (view.getId())
		{
			case R.id.password_field:

				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					if (!isPasswordValid(Objects.requireNonNull(passwordField.getText()).toString()))
					{
						passwordField.requestFocus();
					}
					else
					{
						if (signInAttemptListener.hasAttempts())
						{
							signInButton.performClick();
						}
					}
				}

				return true;

			case R.id.email_field:

				if (actionId == EditorInfo.IME_ACTION_NEXT)
				{
					if (!isEmailValid(Objects.requireNonNull(emailField.getText()).toString()))
					{
						emailField.requestFocus();
					}
					else
					{
						passwordField.requestFocus();
					}
				}

				return true;

			default:

				return false;
		}
	}

	/**
	 *
	 */
	private void displayAccountRecoveryActivity()
	{
		Intent intent = new Intent(getActivity(), PasswordRecoveryActivity.class);

		final String email = Objects.requireNonNull(emailField.getText()).toString();

		if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
		{
			intent.putExtra("email", email);
		}

		startActivity(intent);
	}

	/**
	 *
	 */
	protected void attemptSignIn()
	{
		emailField.setError(null);
		passwordField.setError(null);

		final String email = Objects.requireNonNull(emailField.getText()).toString();
		final String password = Objects.requireNonNull(passwordField.getText()).toString();

		boolean abort = false;
		View focus = null;

		if (!isPasswordValid(password))
		{
			focus = passwordField;
			abort = true;
		}

		if (!isEmailValid(email))
		{
			focus = emailField;
			abort = true;
		}

		if (abort)
		{
			focus.requestFocus();
			signInButton.setEnabled(true);
			passwordRecoveryButton.setEnabled(true);
		}
		else
		{
			Task<AuthResult> signInTask = auth.signInWithEmailAndPassword(email, password);
			signInTask.addOnCompleteListener(signInAttemptListener);
		}
	}

	/**
	 * @param email
	 *
	 * @return
	 */
	protected boolean isEmailValid(String email)
	{
		if (email == null || email.isEmpty())
		{
			emailField.setError(getString(R.string.error_empty_field));

			return false;
		}
		else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
		{
			emailField.setError(getString(R.string.error_badly_formatted_email));

			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * @param password
	 *
	 * @return
	 */
	protected boolean isPasswordValid(String password)
	{
		if (password == null || password.isEmpty())
		{
			passwordField.setError(getString(R.string.error_empty_field));

			return false;
		}
		else if (password.length() < remoteConfig.getLong(getString(R.string.password_minimum_length_key)))
		{
			passwordField.setError(getString(R.string.error_weak_password));

			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 *
	 */
	public interface OnSignInAttemptListener {}

	/**
	 *
	 */
	private class SignInAttemptListener implements OnCompleteListener<AuthResult>
	{
		private SharedPreferences sharedPreferences;

		private long attemptsTime;
		private long attemptsCount;

		private Timer timer;

		/**
		 *
		 */
		public SignInAttemptListener()
		{
			sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences(getString(R.string.shared_preferences_key), Context.MODE_PRIVATE);

			attemptsCount = sharedPreferences.getLong(getString(R.string.sign_in_failed_attempts_count_key), 0);
			attemptsTime = sharedPreferences.getLong(getString(R.string.sign_in_attempts_disabled_remaining_time_key), 0);

			attemptsTime -= (System.currentTimeMillis() - sharedPreferences.getLong(getString(R.string.sign_in_attempts_disabled_date_key), 0));

			if (attemptsTime <= 0)
			{
				enableAttempts(true);
			}

			if (attemptsCount >= remoteConfig.getLong(getString(R.string.sign_in_failed_attempts_limit_key)))
			{
				enableAttempts(false);
			}
		}

		/**
		 * @param task
		 */
		@Override
		public void onComplete(@NonNull Task<AuthResult> task)
		{
			if (task.isSuccessful())
			{
				Log.i(TAG, "Successfully sign in attempt.");

				Intent intent = new Intent(getActivity(), WelcomeActivity.class);
				startActivity(intent);

				Objects.requireNonNull(getActivity()).finish();
			}
			else
			{
				Log.i(TAG, "Failed to sign in attempt.");

				attemptsCount++;

				try
				{
					throw Objects.requireNonNull(task.getException());
				}
				catch (FirebaseAuthInvalidUserException invalidUserException)
				{
					emailField.setError(getString(R.string.error_wrong_email));
					emailField.requestFocus();
				}
				catch (FirebaseAuthInvalidCredentialsException invalidCredentialException)
				{
					passwordField.setError(getString(R.string.error_wrong_password));
					passwordField.requestFocus();
				}
				catch (Exception exception)
				{

					Log.i(TAG, exception.getMessage());

					//TODO: Implement a Crashlytics report here.

					Toast.makeText(getActivity(), R.string.error_authentication_failed, Toast.LENGTH_SHORT).show();

					attemptsCount--;
				}
				finally
				{
					SharedPreferences.Editor editor;
					editor = sharedPreferences.edit();
					editor.putLong(getString(R.string.sign_in_failed_attempts_count_key), attemptsCount);
					editor.putLong(getString(R.string.sign_in_attempts_disabled_date_key), System.currentTimeMillis());
					editor.apply();

					if (attemptsCount >= remoteConfig.getLong(getString(R.string.sign_in_failed_attempts_limit_key)))
					{
						enableAttempts(false);
					}
					else
					{
						signInButton.setEnabled(true);
						passwordRecoveryButton.setEnabled(true);
					}
				}
			}
		}

		/**
		 *
		 */
		private void enableAttempts(boolean value)
		{
			if (value)
			{
				//Todo log message.
				Log.i(TAG, "");

				attemptsCount = 0;
				attemptsTime = 0;

				SharedPreferences.Editor editor;
				editor = sharedPreferences.edit();
				editor.putLong(getString(R.string.sign_in_failed_attempts_count_key), attemptsCount);
				editor.putLong(getString(R.string.sign_in_attempts_disabled_remaining_time_key), attemptsTime);
				editor.apply();

				signInButton.setEnabled(true);
			}
			else
			{
				signInButton.setEnabled(false);

				int remainingTime = (int) (attemptsTime / 60000);

				if (remainingTime < 1)
				{
					remainingTime = 1;
				}

				Toast.makeText(getActivity(), String.format(getResources().getQuantityString(R.plurals.error_too_many_sign_in_attempts, remainingTime, remainingTime), remoteConfig.getLong(getString(R.string.sign_in_attempts_disabled_duration_key))), Toast.LENGTH_LONG).show();

				Log.i(TAG, getResources().getQuantityString(R.plurals.error_too_many_sign_in_attempts, remainingTime, remainingTime));

				if (attemptsTime == 0)
				{
					attemptsTime = (remoteConfig.getLong(getString(R.string.sign_in_attempts_disabled_duration_key)) * 60000);
				}

				this.timer = new Timer(attemptsTime);
				this.timer.start();
			}
		}

		/**
		 * @return
		 */
		private boolean hasAttempts()
		{
			if (attemptsCount >= remoteConfig.getLong(getString(R.string.sign_in_failed_attempts_limit_key)))
			{
				enableAttempts(false);

				return false;
			}
			else
			{
				return true;
			}
		}

		/**
		 *
		 */
		private class Timer extends CountDownTimer
		{
			/**
			 * @param millisInFuture
			 */
			Timer(long millisInFuture)
			{
				super(millisInFuture, 1000);
			}

			/**
			 * @param millisUntilFinished
			 */
			@Override
			public void onTick(long millisUntilFinished)
			{
				attemptsTime = millisUntilFinished;

				Log.i(TAG, "Attempts are deactivated for the remaining time:: " + attemptsTime);

				SharedPreferences.Editor editor;

				editor = sharedPreferences.edit();
				editor.putLong(getString(R.string.sign_in_attempts_disabled_remaining_time_key), attemptsTime);
				editor.apply();
			}

			/**
			 *
			 */
			@Override
			public void onFinish()
			{
				enableAttempts(true);
			}
		}
	}
}
