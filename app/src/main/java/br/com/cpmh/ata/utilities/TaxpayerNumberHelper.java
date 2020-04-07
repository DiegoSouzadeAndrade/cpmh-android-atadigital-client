package br.com.cpmh.ata.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 *
 */
public class TaxpayerNumberHelper implements TextWatcher
{
	public static final String TAXPAYER_NUMBER_FORMAT = "###.###.###-##";

	private String previousTaxpayerNumber = "";

	private EditText editText;

	public TaxpayerNumberHelper(EditText editText)
	{
		this.editText = editText;
	}

	boolean isUpdating;

	/**
	 * @param taxpayerNumber
	 *
	 * @return
	 */
	public static boolean validate(String taxpayerNumber)
	{
		String[] taxpayerNumberDigits = taxpayerNumber.split("");

		int sum = 0;
		int weight;
		int residue;

		weight = 11;


		for (int index = 1; index <= 11; index++)
		{
			int number = Integer.parseInt(taxpayerNumberDigits[index]);

			sum += number * weight;
			weight--;
		}

		residue = 11 - (sum % 11);

		if (residue == 11 || residue == 10)
		{
			residue = 0;
		}

		boolean secondResult = Integer.parseInt(taxpayerNumberDigits[11]) != residue;


		sum = 0;
		weight = 10;

		for (int index = 1; index <= 10; index++)
		{
			int number = Integer.parseInt(taxpayerNumberDigits[index]);

			sum += number * weight;
			weight--;
		}

		residue = 11 - (sum % 11);

		if (residue == 11 || residue == 10)
		{
			residue = 0;
		}

		boolean firstResult = Integer.parseInt(taxpayerNumberDigits[10]) != residue;


		return firstResult && secondResult;
	}

	/**
	 * @param sequence
	 * @param start
	 * @param count
	 * @param after
	 */
	@Override
	public void beforeTextChanged(CharSequence sequence, int start, int count, int after)
	{

	}

	/**
	 * @param sequence
	 * @param start
	 * @param before
	 * @param count
	 */
	@Override
	public void onTextChanged(CharSequence sequence, int start, int before, int count)
	{
		// Just numbers
		final String taxpayerNumber = unmask(sequence.toString());

		StringBuilder formattedTaxpayerNumber = new StringBuilder();

		if (isUpdating)
		{
			previousTaxpayerNumber = taxpayerNumber;
			isUpdating = false;

			return;
		}

		int index = 0;

		for (final char character : TAXPAYER_NUMBER_FORMAT.toCharArray())
		{
			if (character != '#' && taxpayerNumber.length() > previousTaxpayerNumber.length())
			{
				formattedTaxpayerNumber.append(character);

				continue;
			}
			if (character != '#' && taxpayerNumber.length() == previousTaxpayerNumber.length())
			{
				if (index == previousTaxpayerNumber.length())
				{
					break;
				}
			}
			if (character != '#' && taxpayerNumber.length() <= previousTaxpayerNumber.length())
			{

				formattedTaxpayerNumber.append(character);
				continue;
			}
			try
			{
				formattedTaxpayerNumber.append(taxpayerNumber.charAt(index));
			}
			catch (final Exception exception)
			{
				break;
			}
			index++;
		}

		isUpdating = true;

		editText.setText(formattedTaxpayerNumber.toString());
		editText.setSelection(formattedTaxpayerNumber.length());

	}

	/**
	 * @param sequence
	 *
	 * @return
	 */
	@Override
	public void afterTextChanged(Editable sequence)
	{

	}

	/**
	 * @param sequence
	 *
	 * @return
	 */
	public static String unmask(final String sequence)
	{
		return sequence.replaceAll("[.-]", "");
	}
}
