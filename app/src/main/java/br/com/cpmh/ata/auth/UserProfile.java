package br.com.cpmh.ata.auth;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

import br.com.cpmh.ata.analysis.Person;

/**
 *
 */
public class UserProfile extends Person
{
	private List<String> roles;

	/**
	 *
	 */
	public UserProfile()
	{
		roles = new ArrayList<>();
	}

	/**
	 * @param in
	 */
	protected UserProfile(Parcel in)
	{
		super(in);

		roles = new ArrayList<>();

		in.readList(roles, String.class.getClassLoader());
	}

	/**
	 * @param out
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		super.writeToParcel(out, flags);

		out.writeList(roles);
	}

	/**
	 * @return
	 */
	public List<String> getRoles()
	{
		return roles;
	}

	/**
	 * @param roles
	 */
	public void setRoles(List<String> roles)
	{
		this.roles = roles;
	}

	/**
	 *
	 */
	public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>()
	{
		/**
		 *
		 * @param in
		 * @return
		 */
		@Override
		public UserProfile createFromParcel(Parcel in)
		{
			return new UserProfile(in);
		}

		/**
		 *
		 * @param size
		 * @return
		 */
		@Override
		public UserProfile[] newArray(int size)
		{
			return new UserProfile[size];
		}
	};
}
