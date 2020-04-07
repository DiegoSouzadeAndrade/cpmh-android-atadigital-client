package br.com.cpmh.ata.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 *
 */
public class Entity implements Serializable, Parcelable
{
	private String uid;

	/**
	 *
	 */
	public Entity() {}

	/**
	 * @param in
	 */
	protected Entity(Parcel in)
	{
		uid = in.readString();
	}

	/**
	 * @return
	 */
	@Override
	public int describeContents()
	{
		return 0;
	}

	/**
	 * @param out
	 * @param flags
	 */
	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		out.writeString(uid);
	}

	/**
	 * @return
	 */
	public String getUid()
	{
		return uid;
	}

	/**
	 * @param uid
	 */
	public void setUid(String uid)
	{
		this.uid = uid;
	}

	/**
	 *
	 */
	public static final Creator<Entity> CREATOR = new Creator<Entity>()
	{
		/**
		 *
		 * @param in
		 * @return
		 */
		@Override
		public Entity createFromParcel(Parcel in)
		{
			return new Entity(in);
		}

		/**
		 *
		 * @param size
		 * @return
		 */
		@Override
		public Entity[] newArray(int size)
		{
			return new Entity[size];
		}
	};
}
