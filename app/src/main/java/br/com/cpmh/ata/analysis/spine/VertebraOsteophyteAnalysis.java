package br.com.cpmh.ata.analysis.spine;

import java.io.Serializable;

/**
 *
 */
public class VertebraOsteophyteAnalysis implements Serializable
{
	private Vertebra vertebra;

	private String imageURL;

	private String notes;

	/**
	 * return the vertebra
	 *
	 * @return the vertebra
	 */
	public Vertebra getVertebra()
	{
		return vertebra;
	}

	/**
	 * sets the vertebra
	 *
	 * @param vertebra the vertebra
	 */
	public void setVertebra(Vertebra vertebra)
	{
		this.vertebra = vertebra;
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