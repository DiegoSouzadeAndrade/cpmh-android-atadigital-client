package br.com.cpmh.ata.analysis.spine;

/**
 * Benzel Polygon.
 *
 * Nao será usada até segunda ordem
 */
public enum BenzelPolygon
{
	/**
	 * Normal.
	 */
	NORMAL,

	/**
	 * Kyphofis.
	 */
	KYPHOFIS,

	/**
	 * Gray Zone.
	 */
	GRAY_ZONE;

	/**
	 * @return
	 */
	@Override
	public String toString()
	{
		switch (this)
		{
			case NORMAL:
				return "Normal";

			case KYPHOFIS:
				return "Kyphofis";

			case GRAY_ZONE:
				return "Gray Zone";

			default:
				//TODO Exception.
				throw new IllegalArgumentException("");
		}
	}
}
