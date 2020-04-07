package br.com.cpmh.ata.analysis;

import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Patient extends Person implements Serializable
{
	private List<String> doctors;

	/**
	 *
	 */
	public Patient()
	{
		super();
		doctors = new ArrayList<>();
	}

	/**
	 * @param in
	 */
	protected Patient(Parcel in)
	{
		super(in);

		doctors = new ArrayList<>();
//		in.readList(doctors, String.class.getClassLoader());
	}

	/**
	 * @param out
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		super.writeToParcel(out, flags);

		//out.writeList(doctors);
	}

	/**
	 * @return
	 */
	public List<String> getDoctors()
	{
		return doctors;
	}

	/**
	 * @param doctors
	 */
	public void setDoctors(List<String> doctors)
	{
		this.doctors = doctors;
	}

	/**
	 *
	 */
	public static final Creator<Patient> CREATOR = new Creator<Patient>()
	{
		/**
		 *
		 * @param in
		 * @return
		 */
		@Override
		public Patient createFromParcel(Parcel in)
		{
			return new Patient(in);
		}

		/**
		 *
		 * @param size
		 * @return
		 */
		@Override
		public Patient[] newArray(int size)
		{
			return new Patient[size];
		}
	};
}
