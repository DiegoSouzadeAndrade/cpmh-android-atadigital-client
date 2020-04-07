package br.com.cpmh.ata.auth;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.utilities.DatePickerFragment;
import br.com.cpmh.ata.utilities.TaxpayerNumberHelper;

/**
 *
 */
public class SignUpFragment extends Fragment implements View.OnClickListener, TextInputEditText.OnEditorActionListener, OnCompleteListener<Void>,DatePickerFragment.OnDateSetListener
{
	private static final String TAG = "SignUpFragment";

	private OnSignUpAttemptListener listener;

	private FirebaseAuth auth;

	private FirebaseUser user;

	private FirebaseFirestore firestore;

	private FirebaseRemoteConfig remoteConfig;

	private UserProfile userProfile;

	private TextInputEditText firstNameField;

	private TextInputEditText lastNameField;

	private TextInputEditText taxpayerNumberField;

	private TextInputEditText birthdayField;

	private TextInputEditText emailField;

	private TextInputEditText passwordField;

	private TextInputEditText confirmPasswordField;

	private Button nextButton;

	private Button submitButton;

	private Button doctorButton;

	private Button patientButton;

	private ImageButton backButton;

	private ImageButton datePickerButton;

	private ImageButton profilePictureButton;

	private ConstraintLayout constraintLayout;

	private ConstraintSet constraintSet;

	private int currentPageIndex;

	/**
	 *
	 */
	public SignUpFragment() {}

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
		return inflater.inflate(R.layout.fragment_sign_up_01, container, false);
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
		firestore = FirebaseFirestore.getInstance();
		remoteConfig = FirebaseRemoteConfig.getInstance();

		userProfile = new UserProfile();

		firstNameField = view.findViewById(R.id.first_name_field);
		firstNameField.setOnEditorActionListener(this);

		lastNameField = view.findViewById(R.id.last_name_field);
		lastNameField.setOnEditorActionListener(this);

		taxpayerNumberField = view.findViewById(R.id.taxpayer_number_field);
		taxpayerNumberField.setOnEditorActionListener(this);
		taxpayerNumberField.addTextChangedListener(new TaxpayerNumberHelper(taxpayerNumberField));

		birthdayField = view.findViewById(R.id.birthday_field);
		birthdayField.setOnEditorActionListener(this);

		emailField = view.findViewById(R.id.email_field);
		emailField.setOnEditorActionListener(this);

		passwordField = view.findViewById(R.id.password_field);
		passwordField.setOnEditorActionListener(this);

		confirmPasswordField = view.findViewById(R.id.confirm_password_field);
		confirmPasswordField.setOnEditorActionListener(this);

		nextButton = view.findViewById(R.id.next_button);
		nextButton.setOnClickListener(this);

		backButton = view.findViewById(R.id.back_button);
		backButton.setOnClickListener(this);

		submitButton = view.findViewById(R.id.submit_button);
		submitButton.setOnClickListener(this);

		doctorButton = view.findViewById(R.id.doctor_button);
		doctorButton.setOnClickListener(this);

		patientButton = view.findViewById(R.id.patient_button);
		patientButton.setOnClickListener(this);

		profilePictureButton = view.findViewById(R.id.profile_picture_button);
		profilePictureButton.setOnClickListener(this);

		datePickerButton = view.findViewById(R.id.date_picker);
		datePickerButton.setOnClickListener(this);

		currentPageIndex = 1;

		constraintLayout = (ConstraintLayout) view;
		constraintSet = new ConstraintSet();
	}

	/**
	 * @param context
	 */
	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);

		if (context instanceof SignUpFragment.OnSignUpAttemptListener)
		{
			listener = (SignUpFragment.OnSignUpAttemptListener) context;
		}
		else
		{
			throw new RuntimeException(context.toString().concat(" must implement OnSignUpAttemptListener"));
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
	 * @param task
	 */
	@Override
	public void onComplete(@NonNull Task<Void> task)
	{
		if (task.isSuccessful())
		{
			//firestore.collection("users").document(userName.getUid()).set(userProfile).addOnCompleteListener(new UserProfileCreationListener());

			// TODO: Case is doctor or patient.
			//if(userProfile.)
		}
		else
		{
			// TODO handle exception.
		}
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.next_button:

				nextPage();
				break;

			case R.id.back_button:

				previousPage();
				break;

			case R.id.doctor_button:

				nextButton.setVisibility(View.VISIBLE);
				//TODO set userName as Doctor.
				break;

			case R.id.patient_button:

				nextButton.setVisibility(View.VISIBLE);
				//TODO set userName as Patient.
				break;

			case R.id.submit_button:

				attemptSignUp();
				break;

			case R.id.profile_picture_button:

				break;
			case R.id.date_picker:

				DatePickerFragment datePickerFragment = new DatePickerFragment();
				datePickerFragment.show(getChildFragmentManager(), "datePicker");
				datePickerFragment.setOnDateSetListener(this);
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
		return true;
	}

	/**
	 *
	 */
	protected void attemptSignUp()
	{
		emailField.setError(null);
		passwordField.setError(null);
		confirmPasswordField.setError(null);

		final String email = emailField.getText().toString();
		final String password = passwordField.getText().toString();
		final String confirmPassword = confirmPasswordField.getText().toString();

		boolean abort = false;
		View focus = null;

		if (!isConfirmPasswordField(confirmPassword))
		{
			focus = confirmPasswordField;
			abort = true;
		}
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
		}
		else
		{
			auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new SignUpAttemptListener());
		}
	}

	/**
	 *
	 */
	private boolean isInputValid()
	{
		boolean abort = false;
		View focus = null;

		final String firstName = firstNameField.getText().toString();

		final String lastName = lastNameField.getText().toString();

		final String taxpayerNumber = taxpayerNumberField.getText().toString();

		final String birthday = birthdayField.getText().toString();

		switch (currentPageIndex)
		{
			case 1:

				firstNameField.setError(null);
				lastNameField.setError(null);
				taxpayerNumberField.setError(null);

				if (!isTaxpayerNumberValid(taxpayerNumber))
				{
					focus = taxpayerNumberField;
					abort = true;
				}
				if (!isLastNameValid(lastName))
				{
					focus = lastNameField;
					abort = true;
				}
				if (!isFirstNameValid(firstName))
				{
					focus = firstNameField;
					abort = true;
				}
				break;

			case 2:

				//TODO Choice Buttons
				break;

			case 3:

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

				Date date;
				try
				{
					dateFormat.parse(birthday);
				}
				catch (ParseException exception)
				{
					//TODO handle exception.

					Log.i(TAG, exception.getMessage());
					focus = birthdayField;
					abort = true;
				}

				//TODO Picture

				break;
		}

		if (abort)
		{
			focus.requestFocus();
			return false;
		}
		else
		{
			userProfile.setFirstName(firstName);
			userProfile.setLastName(lastName);
			userProfile.setTaxpayerNumber(taxpayerNumber);

			return true;
		}
	}

	/**
	 * @param firstName
	 *
	 * @return
	 */
	private boolean isFirstNameValid(String firstName)
	{
		if (firstName.isEmpty() || firstName == null)
		{
			firstNameField.setError(getString(R.string.error_empty_field));
			return false;
		}

		return true;
	}

	/**
	 * @param lastName
	 *
	 * @return
	 */
	private boolean isLastNameValid(String lastName)
	{
		if (lastName == null || lastName.isEmpty())
		{
			lastNameField.setError(getString(R.string.error_empty_field));
			return false;
		}

		return true;
	}

	/**
	 * @param taxpayerNumber
	 *
	 * @return
	 */
	private boolean isTaxpayerNumberValid(String taxpayerNumber)
	{
		taxpayerNumber = TaxpayerNumberHelper.unmask(taxpayerNumber);

		if (taxpayerNumber == null || taxpayerNumber.isEmpty())
		{
			taxpayerNumberField.setError(getString(R.string.error_empty_field));
			return false;
		}

		if (taxpayerNumber.length() != 11 || !TaxpayerNumberHelper.validate(taxpayerNumber))
		{
			taxpayerNumberField.setError(getString(R.string.error_invalid_taxpayer_number));
			return false;
		}

		return true;
	}

	/**
	 * @param birthday
	 *
	 * @return
	 */
	private boolean isBirthdayValid(Date birthday)
	{
		if (birthday == null)
		{
			birthdayField.setError(getString(R.string.error_empty_field));
			return false;
		}
		else if (birthday.after(new Date()))
		{
			birthdayField.setError(getString(R.string.error_invalid_birthday));
		}
		return true;
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
		else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
		{
			emailField.setError(getString(R.string.error_badly_formatted_email));
			return false;
		}

		return true;
	}

	/**
	 * @param password
	 *
	 * @return
	 */
	private boolean isPasswordValid(String password)
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

		return true;
	}

	/**
	 * @param confirmPassword
	 *
	 * @return
	 */
	private boolean isConfirmPasswordField(String confirmPassword)
	{
		if (confirmPassword == null || confirmPassword.isEmpty())
		{
			confirmPasswordField.setError(getString(R.string.error_empty_field));
			return false;
		}
		else if (confirmPassword != passwordField.getText().toString())
		{
			confirmPasswordField.setError(getString(R.string.error_password_mismatch));
			return false;
		}
		return true;
	}

	/**
	 *
	 */
	public void nextPage()
	{
		if (isInputValid())
		{
			currentPageIndex++;
			switchPage(currentPageIndex);
		}
	}

	/**
	 *
	 */
	public void previousPage()
	{
		if (currentPageIndex > 1)
		{
			currentPageIndex--;
			switchPage(currentPageIndex);
		}
	}

	/**
	 * @param index
	 */
	private void switchPage(int index)
	{
		switch (index)
		{
			case 1:

				constraintSet.clone(getContext(), R.layout.fragment_sign_up_01);
				constraintSet.applyTo(constraintLayout);
				break;

			case 2:

				constraintSet.clone(getContext(), R.layout.fragment_sign_up_02);
				constraintSet.applyTo(constraintLayout);
				break;

			case 3:

				constraintSet.clone(getContext(), R.layout.fragment_sign_up_03);
				constraintSet.applyTo(constraintLayout);
				break;

			case 4:
				constraintSet.clone(getContext(), R.layout.fragment_sign_up_04);
				constraintSet.applyTo(constraintLayout);
				break;
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
	{
		birthdayField.setText(year);
	}

	/**
	 *
	 */
	public interface OnSignUpAttemptListener {}

	/**
	 *
	 */
	private class SignUpAttemptListener implements OnCompleteListener<AuthResult>
	{
		/**
		 * @param task
		 */
		@Override
		public void onComplete(@NonNull Task<AuthResult> task)
		{
			if (task.isSuccessful())
			{
				user = auth.getCurrentUser();

				String displayName = "";

				//UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(displayName).setPhotoUri(photoUri).build();
				UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(displayName).build();

				user.updateProfile(profileChangeRequest).addOnCompleteListener(SignUpFragment.this);
			}
			else
			{
				// TODO handle exception.
			}
		}
	}
}
