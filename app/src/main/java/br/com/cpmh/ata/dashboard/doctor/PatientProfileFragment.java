package br.com.cpmh.ata.dashboard.doctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.cpmh.ata.R;
import br.com.cpmh.ata.analysis.Patient;

/**
 *
 */
public class PatientProfileFragment extends Fragment
{
	private Patient patient;

	protected TextView patientName;
	protected TextView patientTaxpayerNumber;

	/**
	 *
	 */
	public PatientProfileFragment() { }

	/**
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getArguments() != null)
		{
			patient = (Patient) getArguments().getParcelable(getString(R.string.bundle_parcelable_patient_key));
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
		return inflater.inflate(R.layout.fragment_patient_profile, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		patientName = view.findViewById(R.id.patient_name);
		patientName.setText(patient.getFullName());

		patientTaxpayerNumber = view.findViewById(R.id.patient_taxpayer_number);
		patientTaxpayerNumber.setText(patient.getTaxpayerNumber());
	}

	/**
	 * @return
	 */
	public Patient getPatient()
	{
		return patient;
	}

	/**
	 * @param patient
	 */
	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}
}
