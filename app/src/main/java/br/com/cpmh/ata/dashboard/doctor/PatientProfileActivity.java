package br.com.cpmh.ata.dashboard.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.MorphologicalAnalysisListFragment;
import br.com.cpmh.ata.analysis.Patient;
import br.com.cpmh.ata.auth.FirebaseAuthHelper;
import br.com.cpmh.ata.utilities.ViewPagerAdapter;

/**
 *
 */
public class PatientProfileActivity extends AppCompatActivity implements FirebaseAuthHelper.OnUserIdTokenFetchListener
{
	private static final String TAG = "PatientProfileActivity";

	private FirebaseAuth auth;
	private FirebaseAuthHelper authHelper;

	private Patient patient;

	private ViewPagerAdapter viewPagerAdapter;
	private ViewPager viewPager;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_profile);

		auth = FirebaseAuth.getInstance();
		authHelper = new FirebaseAuthHelper();
		authHelper.setOnUserIdTokenFetchListener(this);

		viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

		viewPager = findViewById(R.id.view_pager);
		viewPager.setOffscreenPageLimit(2);
		viewPager.setAdapter(viewPagerAdapter);

		authHelper.validateUser(auth.getCurrentUser());
	}

	/**
	 *
	 */
	@Override
	public void onValidUser()
	{
		Intent intent = getIntent();

		patient = (Patient) intent.getParcelableExtra(getString(R.string.intent_parcelable_patient_key));

		if (patient == null)
		{
			Log.w(TAG, "patient is null");
		}
		else
		{
			displayPatientProfileFragment(patient);
			displayMorphologicalAnalysisListFragment(patient);
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
	 *
	 * @param patient
	 */
	protected void displayPatientProfileFragment(Patient patient)
	{
		Bundle bundle = new Bundle();
		bundle.putParcelable(getString(R.string.bundle_parcelable_patient_key), (Parcelable) patient);

		PatientProfileFragment patientProfileFragment = new PatientProfileFragment();
		patientProfileFragment.setArguments(bundle);

		viewPagerAdapter.addFragment(patientProfileFragment);
		viewPagerAdapter.notifyDataSetChanged();
	}

	/**
	 *
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
}
