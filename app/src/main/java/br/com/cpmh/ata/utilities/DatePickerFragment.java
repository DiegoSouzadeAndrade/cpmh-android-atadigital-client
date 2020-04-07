package br.com.cpmh.ata.utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Objects;

/**
 *
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
	private OnDateSetListener onDateSetListener;

	/**
	 * @param savedInstanceState
	 *
	 * @return
	 */
	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
	{
		final Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(Objects.requireNonNull(getActivity()), this, year, month, day);
	}

	public void setOnDateSetListener(OnDateSetListener onDateSetListener)
	{
		this.onDateSetListener = onDateSetListener;
	}

	/**
	 * @param view
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
	{
		onDateSetListener.onDateSet(view, year, month, dayOfMonth);
	}

	public interface OnDateSetListener
	{
		void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
	}
}