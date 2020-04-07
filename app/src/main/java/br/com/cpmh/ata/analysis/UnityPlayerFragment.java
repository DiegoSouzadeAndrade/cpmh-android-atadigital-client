package br.com.cpmh.ata.analysis;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.unity3d.player.UnityPlayer;

/**
 *
 */
public class UnityPlayerFragment extends Fragment implements ViewTreeObserver.OnWindowFocusChangeListener
{
	private static final String TAG = "UnityPlayerFragment";

	protected UnityPlayer unityPlayer;

	/**
	 *
	 */
	public UnityPlayerFragment() {}

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
		unityPlayer = new UnityPlayer((getActivity()));
		return unityPlayer.getView();
	}

	/**
	 * @param view
	 * @param savedInstanceState
	 */
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
	{
		view.getViewTreeObserver().addOnWindowFocusChangeListener(this);

		unityPlayer.start();
		unityPlayer.resume();
	}

	/**
	 *
	 */
	@Override
	public void onDestroy()
	{
		super.onDestroy();

		unityPlayer.quit();
	}

	/**
	 *
	 */
	@Override
	public void onResume()
	{
		super.onResume();

		unityPlayer.resume();
	}

	/**
	 *
	 */
	@Override
	public void onStart()
	{
		super.onStart();

		unityPlayer.start();
	}

	/**
	 *
	 */
	@Override
	public void onLowMemory()
	{
		super.onLowMemory();

		unityPlayer.lowMemory();
	}

	/**
	 * @param hasFocus
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		unityPlayer.windowFocusChanged(hasFocus);
	}

	/**
	 * @param newConfig
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);

		unityPlayer.configurationChanged(newConfig);

	}

	/**
	 * @param object
	 * @param method
	 * @param parameter
	 */
	public void SendMessageToUnityPlayer(String object, String method, String parameter)
	{
		UnityPlayer.UnitySendMessage(object, method, parameter);
	}
}
