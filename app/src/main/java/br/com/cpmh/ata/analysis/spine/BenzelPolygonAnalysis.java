package br.com.cpmh.ata.analysis.spine;

import java.io.Serializable;

import br.com.cpmh.ata.analysis.Patient;

/**
 * Essa classe nao será usada até segunda ordem
 */
public class BenzelPolygonAnalysis implements Serializable
{

	private BenzelPolygon benzelPolygon;

	private Patient patient;

	/**
	 *
	 */
	public BenzelPolygonAnalysis()
	{

	}

	/**
	 * @param benzelPolygon The benzel polygon.
	 *
	 * @param patient       The patient.
	 */
	public BenzelPolygonAnalysis(BenzelPolygon benzelPolygon, Patient patient)
	{
		this.benzelPolygon = benzelPolygon;

		this.patient = patient;
	}

	/**
	 * Returns the benzel polygon.
	 *
	 * @return The benzel polygon.
	 */
	public BenzelPolygon getBenzelPolygon()
	{
		return benzelPolygon;
	}

	/**
	 * Sets the benzel polygon.
	 *
	 * @param benzelPolygon The benzel polygon.
	 */
	public void setBenzelPolygon(BenzelPolygon benzelPolygon)
	{
		this.benzelPolygon = benzelPolygon;
	}

	/**
	 * Returns the patient.
	 *
	 * @return The patient.
	 */
	public Patient getPatient()
	{
		return patient;
	}

	/**
	 * Sets the patient.
	 *
	 * @param patient The patient.
	 */
	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}
}
