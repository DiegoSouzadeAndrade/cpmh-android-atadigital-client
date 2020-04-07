package br.com.cpmh.ata.analysis;

import android.os.Parcel;

import java.io.Serializable;

import br.com.cpmh.ata.core.Entity;

public class MorphologicalAnalysis extends Entity implements Serializable
{
	private Patient patient;
	private Doctor doctor;
	private String subject;


	public MorphologicalAnalysis() {
		super();
	}

	public MorphologicalAnalysis(Parcel in) {

		super(in);

		this.patient = (Patient) in.readSerializable();
		this.doctor = (Doctor) in.readSerializable();
		this.subject = in.readString();
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);

		out.writeSerializable(patient);
		out.writeSerializable(doctor);
		out.writeString(subject);
	}

	/***
	 * Returns the patient
	 *
	 * @return the patient
	 */
	public Patient getPatient()
	{
		return patient;
	}

	/**
	 * Sets the patient
	 *
	 * @param patient The patient
	 */
	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}

	/**
	 * Returns the doctor
	 *
	 * @return the doctor
	 */
	public Doctor getDoctor()
	{
		return doctor;
	}

	/**
	 * Sets the doctor
	 *
	 * @param doctor The doctor
	 */
	public void setDoctor(Doctor doctor)
	{
		this.doctor = doctor;
	}

	public static final Creator<MorphologicalAnalysis> CREATOR = new Creator<MorphologicalAnalysis>()
	{
		/**
		 *
		 * @param in
		 * @return
		 */
		@Override
		public MorphologicalAnalysis createFromParcel(Parcel in)
		{
			return new MorphologicalAnalysis(in);
		}

		/**
		 *
		 * @param size
		 * @return
		 */
		@Override
		public MorphologicalAnalysis[] newArray(int size)
		{
			return new MorphologicalAnalysis[size];
		}
	};
}
