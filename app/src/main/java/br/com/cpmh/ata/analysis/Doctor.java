package br.com.cpmh.ata.analysis;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends Person implements Serializable
{
	private List<Patient> patients;

	/**
	 *
	 */
	public Doctor() {
	}

	/**
	 *
	 * @param in
	 */
	public Doctor(Parcel in) {
		super(in);
		this.patients = new ArrayList<>();
	}

	/**
	 *
	 * @return
	 */
	public List<Patient> getPatients() {
		return patients;
	}

	/**
	 *
	 * @param patients
	 */
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	/**
	 *
	 * @param out
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel out, int flags) {
		super.writeToParcel(out, flags);

		//out.writeList(patients);
	}

	/**
	 *
	 */
	public static final Creator<Doctor> CREATOR = new Creator<Doctor>()
	{
		/**
		 *
		 * @param in
		 * @return
		 */
		@Override
		public Doctor createFromParcel(Parcel in)
		{
			return new Doctor(in);
		}

		/**
		 *
		 * @param size
		 * @return
		 */
		@Override
		public Doctor[] newArray(int size)
		{
			return new Doctor[size];
		}
	};
}
