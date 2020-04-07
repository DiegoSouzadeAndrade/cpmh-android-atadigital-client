package br.com.cpmh.ata.analysis.spine;

/**
 * The vertebra type represents the region and position of a vertebra for morphological analysis reports.
 */
public enum VertebraType
{
	/**
	 * 1st Cervical Vertebra.
	 */
	CERVICAL_1,

	/**
	 * 2nd Cervical Vertebra.
	 */
	CERVICAL_2,

	/**
	 * 3rd Cervical Vertebra.
	 */
	CERVICAL_3,

	/**
	 * 4th Cervical Vertebra.
	 */
	CERVICAL_4,

	/**
	 * 5th Cervical Vertebra.
	 */
	CERVICAL_5,

	/**
	 * 6th Cervical Vertebra.
	 */
	CERVICAL_6,

	/**
	 * 7th Cervical Vertebra.
	 */
	CERVICAL_7,

	/**
	 * 1st Thoracic Vertebra.
	 */
	THORACIC_1,

	/**
	 * 2nd Thoracic Vertebra.
	 */

	THORACIC_2,

	/**
	 * 3rd Thoracic Vertebra.
	 */
	THORACIC_3,

	/**
	 * 4th Thoracic Vertebra.
	 */
	THORACIC_4,

	/**
	 * 5th Thoracic Vertebra.
	 */
	THORACIC_5,

	/**
	 * 6th Thoracic Vertebra.
	 */
	THORACIC_6,

	/**
	 * 7th Thoracic Vertebra.
	 */
	THORACIC_7,

	/**
	 * 8th Thoracic Vertebra.
	 */
	THORACIC_8,

	/**
	 * 9th Thoracic Vertebra.
	 */
	THORACIC_9,

	/**
	 * 10th Thoracic Vertebra.
	 */
	THORACIC_10,

	/**
	 * 11th Thoracic Vertebra.
	 */
	THORACIC_11,

	/**
	 * 12th Thoracic Vertebra.
	 */
	THORACIC_12,

	/**
	 * 1st Lumbar Vertebra.
	 */
	LUMBAR_1,

	/**
	 * 2nd Lumbar Vertebra.
	 */
	LUMBAR_2,

	/**
	 * 3rd Lumbar Vertebra.
	 */
	LUMBAR_3,

	/**
	 * 4th Lumbar Vertebra.
	 */
	LUMBAR_4,

	/**
	 * 5th Lumbar Vertebra.
	 */
	LUMBAR_5,

	/**
	 * Sacrum Vertebra.
	 */
	SACRUM,

	/**
	 * Coccyx Vertebra.
	 */
	COCCYX;

	/**
	 * @return
	 */
	@Override
	public String toString()
	{
		switch (this)
		{
			case CERVICAL_1:
				return "1st Cervical Vertebra";

			case CERVICAL_2:
				return "2nd Cervical Vertebra";

			case CERVICAL_3:
				return "3rd Cervical Vertebra";

			case CERVICAL_4:
				return "4th Cervical Vertebra";

			case CERVICAL_5:
				return "5th Cervical Vertebra";

			case CERVICAL_6:
				return "6th Cervical Vertebra";

			case CERVICAL_7:
				return "7th Cervical Vertebra";

			case THORACIC_1:
				return "1st Thoracic Vertebra";

			case THORACIC_2:
				return "2nd Thoracic Vertebra";

			case THORACIC_3:
				return "3th Thoracic Vertebra";

			case THORACIC_4:
				return "4th Thoracic Vertebra";

			case THORACIC_5:
				return "5th Thoracic Vertebra";

			case THORACIC_6:
				return "6th Thoracic Vertebra";

			case THORACIC_7:
				return "7th Thoracic Vertebra";

			case THORACIC_8:
				return "8th Thoracic Vertebra";

			case THORACIC_9:
				return "9th Thoracic Vertebra";

			case THORACIC_10:
				return "10th Thoracic Vertebra";

			case THORACIC_11:
				return "11th Thoracic Vertebra";

			case THORACIC_12:
				return "12th Thoracic Vertebra";

			case LUMBAR_1:
				return "1st Lumbar Vertebra";

			case LUMBAR_2:
				return "2nd Lumbar Vertebra";

			case LUMBAR_3:
				return "3rd Lumbar Vertebra";

			case LUMBAR_4:
				return "4th Lumbar Vertebra";

			case LUMBAR_5:
				return "5th Lumbar Vertebra";

			case SACRUM:
				return "Sacrum Vertebra";

			case COCCYX:
				return "Coccyx Vertebra";

			default:
				// TODO: describes the exception.
				throw new IllegalArgumentException("");
		}
	}
}