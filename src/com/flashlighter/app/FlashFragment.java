/**
 * 
 */
package com.flashlighter.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * @author Scott Auman
 *
 */
@SuppressLint("InflateParams")
@SuppressWarnings("deprecation")
public class FlashFragment extends Fragment implements OnClickListener {

	private boolean isFlashOn;

	private Camera camera;
	private Parameters parameters;
	private SoundPlayer soundPlayer;
	private RelativeLayout layout;
	private SoundButton soundButton;
	private Notify notify;

	/**
	 * 
	 */
	public FlashFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(View v) {
		if (!isFlashOn) {
			turnOnFlash();
		} else {
			turnOffFlash();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		isFlashOn = false;
		soundPlayer = new SoundPlayer(getActivity());
		notify = new Notify(getActivity());
		setupCamera();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Notify.REQUEST_CODE) {
			if (getCamera() != null) {
				getCamera().release();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View thisView = inflater.inflate(R.layout.fragment_flash, null, true);
		thisView.setOnClickListener(this);

		//set up custom layout paramters for the sound toggle button
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

		setLayout((RelativeLayout) thisView
				.findViewById(R.id.soundFragmentRelativeLayout));

		soundButton = new SoundButton(getActivity(), getLayout());

		getLayout().addView(soundButton, params);

		// add sound button to view
		return thisView;
	}

	private void setupCamera() {
		if (getCamera() == null) {
			try {
				setCamera(Camera.open());
				setParameters(getCamera().getParameters());
			} catch (RuntimeException e) {
				Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
			}
		}
	}

	/*
	 * Turning On flash
	 */
	private void turnOnFlash() {
		if (!isFlashOn) {
			if (getCamera() == null || getParameters() == null) {
				return;
			}
			// play sound
			if (new SoundPreferences(getActivity()).isSoundOn())
				getSoundPlayer().playOnSound();

			setParameters(getCamera().getParameters());
			getParameters().setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(getParameters());
			camera.startPreview();
			isFlashOn = true;

			// changing button/switch image
			toggleOnBackground();
		}

	}

	private void toggleOnBackground() {
		
		//setbackground() added in API 14 
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// API less than 4.0
			getLayout().setBackgroundDrawable(
					getActivity().getResources().getDrawable(
							R.drawable.glow_on));
		} else {
			getLayout().setBackground(
					getActivity().getResources().getDrawable(
							R.drawable.glow_on));
		}
	}

	/*
	 * Turning Off flash
	 */
	private void turnOffFlash() {
		if (isFlashOn) {
			if (getCamera() == null || getParameters() == null) {
				return;
			}
			// play sound
			if (new SoundPreferences(getActivity()).isSoundOn())
				getSoundPlayer().playOffSound();

			setParameters(camera.getParameters());
			getParameters().setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(getParameters());
			camera.stopPreview();
			isFlashOn = false;

			// changing button/switch image
			toggleOffBackground();
		}
	}

	private void toggleOffBackground() {

		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			// API less than 4.0
			getLayout().setBackgroundDrawable(
					getActivity().getResources().getDrawable(
							R.drawable.glow_off));
		} else {
			getLayout().setBackground(
					getActivity().getResources().getDrawable(
							R.drawable.glow_off));
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		if (isFlashOn)
			notify.launchNotification();
	}

	@Override
	public void onResume() {
		super.onResume();

		if (notify.launched) {
			if (getCamera() != null) {
				getCamera().release();
				isFlashOn = false;
				getActivity().finish();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (camera != null) {
			camera.release();
			notify.cancelNotification();
		}

	}

	/**
	 * @return the isFlashOn
	 */
	public boolean isFlashOn() {
		return isFlashOn;
	}

	/**
	 * @param isFlashOn
	 *            the isFlashOn to set
	 */
	public void setFlashOn(boolean isFlashOn) {
		this.isFlashOn = isFlashOn;
	}

	/**
	 * @param camera
	 *            the camera to set
	 */
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Camera getCamera() {
		return this.camera;
	}

	/**
	 * @return the parameters
	 */
	public Parameters getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the soundPlayer
	 */
	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}

	/**
	 * @param soundPlayer
	 *            the soundPlayer to set
	 */
	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}

	/**
	 * @return the soundButton
	 */
	public ImageButton getSoundButton() {
		return soundButton;
	}

	/**
	 * @param soundButton
	 *            the soundButton to set
	 */
	public void setSoundButton(SoundButton soundButton) {
		this.soundButton = soundButton;
	}

	/**
	 * @return the layout
	 */
	public RelativeLayout getLayout() {
		return layout;
	}

	/**
	 * @param layout
	 *            the layout to set
	 */
	public void setLayout(RelativeLayout layout) {
		this.layout = layout;
	}

	/**
	 * @return the notify
	 */
	public Notify getNotify() {
		return notify;
	}

	/**
	 * @param notify
	 *            the notify to set
	 */
	public void setNotify(Notify notify) {
		this.notify = notify;
	}

}
