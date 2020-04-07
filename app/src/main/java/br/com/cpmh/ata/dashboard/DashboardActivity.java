package br.com.cpmh.ata.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import javax.annotation.Nullable;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.MorphologicalAnalysisListFragment;
import br.com.cpmh.ata.analysis.Doctor;
import br.com.cpmh.ata.analysis.Patient;
import br.com.cpmh.ata.auth.FirebaseAuthHelper;
import br.com.cpmh.ata.auth.UserProfile;
import br.com.cpmh.ata.dashboard.doctor.PatientsListFragment;
import br.com.cpmh.ata.utilities.ViewPagerAdapter;

/**
 *
 */
public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuthHelper.OnUserIdTokenFetchListener, FirebaseAuthHelper.OnUserProfileFetchListener
{
	private static final String TAG = "DashboardActivity";

	protected FirebaseAuth auth;

	protected FirebaseAuthHelper authHelper;

	protected FirebaseFirestore firestore;

	protected UserProfile userProfile;

	protected ViewPagerAdapter viewPagerAdapter;

	protected ViewPager viewPager;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		auth = FirebaseAuth.getInstance();
		authHelper = new FirebaseAuthHelper();
		authHelper.setOnUserIdTokenFetchListener(this);
		authHelper.setOnUserProfileFetchListener(this);

		firestore = FirebaseFirestore.getInstance();

		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

		viewPager = findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(viewPagerAdapter);

		authHelper.validateUser(auth.getCurrentUser());
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{

	}

	/**
	 *
	 */
	@Override
	public void onValidUser()
	{
		Intent intent = getIntent();

		userProfile = (UserProfile) intent.getParcelableExtra(getString(R.string.intent_parcelable_user_profile_key));

		if (userProfile == null)
		{
			authHelper.retrieveUserProfile(auth.getCurrentUser());
		}
		else
		{
			displayUserProfileFragment(userProfile);

			checkUserPermissions();
		}
	}

	/**
	 *
	 */
	@Override
	public void onInvalidUser()
	{
		authHelper.displayLoginActivity();
	}

	/**
	 * @param userProfile
	 */
	@Override
	public void onRetrievedUserProfile(UserProfile userProfile)
	{
		this.userProfile = userProfile;

		displayUserProfileFragment(userProfile);

		checkUserPermissions();
	}

	/**
	 *
	 */
	protected void checkUserPermissions()
	{
		List<String> roles = userProfile.getRoles();

		if (roles.contains("Doctor"))
		{
			retrieveUserDoctorData();
		}

		else if (roles.contains("Patient"))
		{
			retrieveUserPatientData();
		}
	}

	/**
	 * @param userProfile
	 */
	protected void displayUserProfileFragment(UserProfile userProfile)
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(getString(R.string.bundle_parcelable_user_profile_key), (Parcelable) userProfile);

		UserProfileFragment userProfileFragment = new UserProfileFragment();
		userProfileFragment.setArguments(bundle);

		viewPagerAdapter.addFragment(userProfileFragment);
		viewPagerAdapter.notifyDataSetChanged();
	}

	/**
	 * @param doctor
	 */
	protected void displayPatientListFragment(Doctor doctor)
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(getString(R.string.bundle_parcelable_doctor_key), (Parcelable) doctor);

		PatientsListFragment patientsListFragment = new PatientsListFragment();
		patientsListFragment.setArguments(bundle);

		viewPagerAdapter.addFragment(patientsListFragment);
		viewPagerAdapter.notifyDataSetChanged();
	}

	/**
	 * @param patient
	 */
	protected void displayMorphologicalAnalysisListFragment(Patient patient)
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(getString(R.string.bundle_parcelable_patient_key), (Parcelable) patient);

		MorphologicalAnalysisListFragment morphologicalAnalysisListFragment = new MorphologicalAnalysisListFragment();
		morphologicalAnalysisListFragment.setArguments(bundle);

		viewPagerAdapter.addFragment(morphologicalAnalysisListFragment);
		viewPagerAdapter.notifyDataSetChanged();
	}

	/**
	 *
	 */
	protected void retrieveUserDoctorData()
	{
		firestore.collection(getString(R.string.firestore_doctors_collection_path_key)).document(userProfile.getUid()).addSnapshotListener(new UserDoctorDataFetchListener());
	}

	/**
	 *
	 */
	protected void retrieveUserPatientData()
	{
		firestore.collection(getString(R.string.firestore_patients_collection_path_key)).document(userProfile.getUid()).addSnapshotListener(new UserPatientDataFetchListener());
	}

	/**
	 *
	 */
	private class UserDoctorDataFetchListener implements EventListener<DocumentSnapshot>
	{
		/**
		 * @param snapshot
		 * @param exception
		 */
		@Override
		public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException exception)
		{
			Doctor doctor = snapshot.toObject(Doctor.class);

			displayPatientListFragment(doctor);
		}
	}

	/**
	 *
	 */
	private class UserPatientDataFetchListener implements EventListener<DocumentSnapshot>
	{
		/**
		 * @param snapshot
		 * @param exception
		 */
		@Override
		public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException exception)
		{
			Patient patient = snapshot.toObject(Patient.class);

			displayMorphologicalAnalysisListFragment(patient);
		}
	}
}
