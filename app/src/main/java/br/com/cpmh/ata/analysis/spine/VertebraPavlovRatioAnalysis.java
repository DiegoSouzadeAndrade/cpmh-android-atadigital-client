package br.com.cpmh.ata.analysis.spine;

import java.io.Serializable;

/**
 *
 */
public class VertebraPavlovRatioAnalysis implements Serializable
{
	private Vertebra vertebra;

	private String distance;

	private String thickness;

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
	public String getDistance() {
		return distance;
	}

	/**
	 *
	 * @param distance
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 *
	 * @return
	 */
	public String getThickness() {
		return thickness;
	}

	/**
	 *
	 * @param thickness
	 */
	public void setThickness(String thickness) {
		this.thickness = thickness;
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