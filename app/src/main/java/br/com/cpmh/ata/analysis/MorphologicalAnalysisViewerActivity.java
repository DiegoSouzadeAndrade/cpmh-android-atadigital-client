package br.com.cpmh.ata.analysis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.auth.FirebaseAuthHelper;
import br.com.cpmh.ata.auth.UserProfile;

/**
 *
 */
public class MorphologicalAnalysisViewerActivity extends AppCompatActivity implements View.OnClickListener, FirebaseAuthHelper.OnUserIdTokenFetchListener {
	private final String TAG = "MorphologicalAnalysisVA";

	protected UnityPlayerFragment unityPlayerFragment;

	private Patient patient;
	private MorphologicalAnalysis morphologicalAnalysis;

	protected FirebaseAuth auth;

	private FirebaseAuthHelper authHelper;

	/**
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		auth = FirebaseAuth.getInstance();
		setContentView(R.layout.activity_morphological_analysis_viewer);

		FloatingActionButton test = findViewById(R.id.isolate_selected_mode_button);
		test.setOnClickListener(this);

		authHelper = new FirebaseAuthHelper();
		authHelper.setOnUserIdTokenFetchListener(this);

		authHelper.validateUser(auth.getCurrentUser());
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{
		switch ((view.getId()))
		{
			case R.id.augmented_reality_button:

				break;

			case R.id.paint_selection_mode_button:

				break;

			case R.id.isolate_selected_mode_button:
				FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
				VertebraMorphologicalAnalysesFragment vertebraMorphologicalAnalysesFragment = VertebraMorphologicalAnalysesFragment.newInstance(0);

				Bundle bundle = new Bundle();
				bundle.putParcelable("auehua", (Parcelable) patient);
				bundle.putParcelable("morph", (Parcelable) morphologicalAnalysis);
				Log.i("teste",morphologicalAnalysis.getUid());

				vertebraMorphologicalAnalysesFragment.setArguments(bundle);

				vertebraMorphologicalAnalysesFragment.show(fragmentTransaction, "VertebraDialog");

				break;
		}
	}

	protected void displayAnalyses()
	{

	}

	/**
	 * @param value
	 */
	protected void setArgumentRealityMode(boolean value)
	{
		unityPlayerFragment.SendMessageToUnityPlayer("", "", "");
	}

	/**
	 *
	 */
	protected void isolateSelectedSubjects()
	{
		unityPlayerFragment.SendMessageToUnityPlayer("", "", "");
	}

	@Override
	public void onValidUser() {
		Intent intent = getIntent();

		patient = (Patient) intent.getParcelableExtra(getString(R.string.intent_parcelable_patient_key));
		morphologicalAnalysis =(MorphologicalAnalysis)intent.getParcelableExtra(getString(R.string.bundle_parcelable_morphological_analyses_key));

		if (patient == null)
		{
			Log.w(TAG, "patient is null");

		}if (morphologicalAnalysis == null)
		{
			Log.w(TAG, "morphologicalAnalysis is null");
		}
	}

	@Override
	public void onInvalidUser() {

	}
}
