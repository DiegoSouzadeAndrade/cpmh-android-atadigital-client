package br.com.cpmh.ata.barcode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.cpmh.ata.R;

/**
 *
 */
public class BarcodeReaderFragment extends Fragment implements View.OnClickListener
{
	/**
	 *
	 */
	public BarcodeReaderFragment() {}

	/**
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 *
	 * @return
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_barcode_reader, container, false);
	}

	/**
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * @param view
	 */
	@Override
	public void onClick(View view)
	{

	}
}
