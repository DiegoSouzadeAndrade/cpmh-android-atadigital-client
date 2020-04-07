package br.com.cpmh.ata.analysis;

import android.os.Parcel;

import java.util.Date;

import br.com.cpmh.ata.core.Entity;

/**
 *
 */
public class Person extends Entity
{
	private String firstName;
	private String middleName;
	private String lastName;
	private String taxpayerNumber;
	private Date birthday;

	/**
	 *
	 */
	public Person()
	{
		super();
	}

	/**
	 * @param in
	 */
	protected Person(Parcel in)
	{
		super(in);

		firstName = in.readString();
		middleName = in.readString();
		lastName = in.readString();
		taxpayerNumber = in.readString();
		birthday = (Date) in.readSerializable();
	}

	/**
	 * @param out
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		super.writeToParcel(out, flags);

		out.writeString(firstName);
		out.writeString(middleName);
		out.writeString(lastName);
		out.writeString(taxpayerNumber);
		out.writeSerializable(birthday);
	}

	/**
	 * Returns the first name of the person.
	 *
	 * @return The first name.
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Sets the first name of the person.
	 *
	 * @param firstName The first name.
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * Returns the middle name of the person.
	 *
	 * @return The middle name.
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * Sets the middle name of the person.
	 *
	 * @param middleName The middle name.
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * Returns the last name of the person.
	 *
	 * @return The last name.
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Sets the last name of the person.
	 *
	 * @param lastName The last name.
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * Returns the full of the person.
	 *
	 * @return
	 */
	public String getFullName()
	{
		return String.format("%s %s %s", this.firstName, this.middleName, this.lastName);
	}

	/**
	 * Returns the birthday of the person.
	 *
	 * @return The birthday of the person.
	 */
	public Date getBirthday()
	{
		return birthday;
	}

	/**
	 * Sets the birthday of the person.
	 *
	 * @param birthday The birthday of the person.
	 */
	public void setBirthday(Date birthday) throws IllegalArgumentException
	{
		Date today = new Date();

		if (birthday.after(today))
		{
			this.birthday = null;
			// TODO: describes the exception.
			throw new IllegalArgumentException("");
		}
		this.birthday = birthday;
	}

	/**
	 * @return
	 */
	public String getTaxpayerNumber()
	{
		return taxpayerNumber;
	}

	/**
	 * @param taxpayerNumber
	 */
	public void setTaxpayerNumber(String taxpayerNumber)
	{
		this.taxpayerNumber = taxpayerNumber;
	}

	/**
	 *
	 */
	public static final Creator<Person> CREATOR = new Creator<Person>()
	{
		/**
		 *
		 * @param in
		 * @return
		 */
		@Override
		public Person createFromParcel(Parcel in)
		{
			return new Person(in);
		}

		/**
		 *
		 * @param size
		 * @return
		 */
		@Override
		public Person[] newArray(int size)
		{
			return new Person[size];
		}
	};
}
