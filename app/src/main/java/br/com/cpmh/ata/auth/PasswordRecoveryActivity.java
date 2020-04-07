package br.com.cpmh.ata.auth;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.List;
import java.util.Objects;

import br.com.cpmh.ata.R;

/**
 *
 */
public class PasswordRecoveryActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuthHelper.OnFetchSignInMethodsForEmailListener, FirebaseAuthHelper.OnSendPasswordResetEmailListener
{
	private static final String TAG = "PasswordRecoveryActivity";

	private FirebaseAuth auth;
	private FirebaseAuthHelper authHelper;

	private TextInputEditText emailField;
	private Button passwordRecoveryButton;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password_recovery);

		auth = FirebaseAuth.getInstance();
		authHelper = new FirebaseAuthHelper();
		authHelper.setOnFetchSignInMethodsForEmailListener(this);
		authHelper.setOnSendPasswordResetEmailListener(this);

		emailField = findViewById(R.id.email_field);

		Bundle bundle = getIntent().getExtras();

		if (bundle != null && bundle.get("email") != null)
		{
			emailField.setText(bundle.getString("email"));
		}

		passwordRecoveryButton = findViewById(R.id.password_recovery_button);
		passwordRecoveryButton.setOnClickListener(this);
	}

	/**
	 *
	 */
	private void attemptPasswordRecovery()
	{
		final String email = Objects.requireNonNull(emailField.getText()).toString();

		if (!isEmailValid(email))
		{
			emailField.requestFocus();
		}
		else
		{
			authHelper.retrieveSignInMethodsForEmail(email);
		}
	}

	/**
	 * @param email
	 *
	 * @return
	 */
	private boolean isEmailValid(String email)
	{
		if (email == null || email.isEmpty())
		{
			emailField.setError(getString(R.string.error_empty_field));
			return false;
		}
		else return Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.password_recovery_button:
				attemptPasswordRecovery();
				passwordRecoveryButton.setEnabled(false);
				break;
		}
	}

	/**
	 *
	 */
	@Override
	public void onSendPasswordResetEmailSuccess()
	{
		//TODO: Add feedback message here.
		//Toast.makeText(getBaseContext(), "", Toast.LENGTH_LONG).show();
	}

	/**
	 * @param exception
	 */
	@Override
	public void onSendPasswordResetEmailFailure(Exception exception)
	{
		try
		{
			throw Objects.requireNonNull(exception);
		}
		catch (FirebaseAuthInvalidUserException invalidUserException)
		{
			emailField.setError(getString(R.string.error_wrong_email));
			emailField.requestFocus();
		}
		catch (Exception genericException)
		{
			Toast.makeText(getBaseContext(), R.string.error_password_recovery_failed, Toast.LENGTH_SHORT).show();
		}
		finally
		{
			passwordRecoveryButton.setEnabled(true);
		}
	}

	/**
	 *
	 */
	@Override
	public void onFetchedSignMethodsForEmailSuccess(List<String> signInMethodsList)
	{
		if (Objects.requireNonNull(Objects.requireNonNull(signInMethodsList.isEmpty())))
		{
			emailField.setError(getString(R.string.error_wrong_email));
		}
		else
		{
			final String email = Objects.requireNonNull(emailField.getText()).toString();

			authHelper.sendPasswordResetEmail(email);
		}
	}

	/**
	 * @param exception
	 */
	@Override
	public void onFetchSignMethodsForEmailFailure(Exception exception)
	{
		try
		{
			throw Objects.requireNonNull(exception);
		}
		catch (FirebaseAuthInvalidCredentialsException invalidCredentialException)
		{
			emailField.setError(getString(R.string.error_badly_formatted_email));
			emailField.requestFocus();
		}
		catch (Exception genericException)
		{
			Toast.makeText(getBaseContext(), R.string.error_password_recovery_failed, Toast.LENGTH_SHORT).show();
		}
	}
}
