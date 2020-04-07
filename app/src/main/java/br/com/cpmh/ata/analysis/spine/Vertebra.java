package br.com.cpmh.ata.analysis.spine;

import java.io.Serializable;

import br.com.cpmh.ata.analysis.Patient;

public class Vertebra implements Serializable
{
	private VertebraType type;
	private Patient patient;

	/**
	 * Returns the type that represents the region and position of the vertebra.
	 *
	 * @return The type of the vertebra.
	 */
	public VertebraType getType()
	{
		return type;
	}

	/**
	 * Sets the type of the vertebra that represents the region and position.
	 *
	 * @param type The type of the vertebra.
	 */
	public void setType(VertebraType type)
	{
		this.type = type;
	}

	/**
	 * @return The patient.
	 */
	public Patient getPatient()
	{
		return patient;
	}

	/**
	 * @param patient The patient.
	 */
	public void setPatient(Patient patient)
	{
		this.patient = patient;
	}
}
