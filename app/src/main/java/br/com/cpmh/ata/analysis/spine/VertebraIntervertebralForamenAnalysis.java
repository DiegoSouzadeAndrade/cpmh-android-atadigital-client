package br.com.cpmh.ata.analysis.spine;

import java.io.Serializable;

public class VertebraIntervertebralForamenAnalysis implements Serializable
{
	private Vertebra vertebra;

	private String horizontal;

	private String vertical;

	private String imageURL;

	private String notes;


	/**
	 * @return
	 */
	public Vertebra getVertebra()
	{
		return vertebra;
	}

	/**
	 * @param vertebra
	 */
	public void setVertebra(Vertebra vertebra)
	{
		this.vertebra = vertebra;
	}

	/**
	 * @return
	 */
	public String getHorizontal()
	{
		return horizontal;
	}

	/**
	 * @param horizontal
	 */
	public void setHorizontal(String horizontal)
	{
		this.horizontal = horizontal;
	}

	/**
	 * @return
	 */
	public String getVertical()
	{
		return vertical;
	}

	/**
	 * @param vertical
	 */
	public void setVertical(String vertical)
	{
		this.vertical = vertical;
	}

	/**
	 *
	 * @return
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 *
	 * @param imageURL
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	/**
	 *
	 * @return
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 *
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
