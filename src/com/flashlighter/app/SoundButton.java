/**
 * 
 */
package com.flashlighter.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

/**
 * @author Scott Auman
 *
 */
public class SoundButton extends ImageButton implements OnClickListener {

	private FlashStatesName currentState;
	private Integer sound_off_image;
	private Integer sound_on_image;

	/**
	 * @param context
	 */
	public SoundButton(Context context, RelativeLayout layout) {
		super(context);

		// inflate the imagebutton object with XML view
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.sound_button, layout, false);

		// drawables for states changes
		setSound_off_image(R.drawable.sound_off);
		setSound_on_image(R.drawable.sound_on);

		setClickable(true);
		setOnClickListener(this); // attach click listener implemented for this
									// class

		this.setBackgroundColor(getContext().getResources().getColor(
				android.R.color.transparent));

		// check starting state of button
		if (new SoundPreferences(getContext()).isSoundOn()) {
			// user preference has sound on
			currentState = FlashStatesName.ON;
			this.setImageDrawable(getContext().getResources().getDrawable(
					getSound_on_image()));
		} else {
			// user preferences has sound off
			currentState = FlashStatesName.OFF;
			this.setImageDrawable(getContext().getResources().getDrawable(
					getSound_off_image()));
		}
	}

	@Override
	public void setClickable(boolean clickable) {
		super.setClickable(clickable);
	}

	@Override
	public void onClick(View v) {
		if (currentState == FlashStatesName.ON) {
			// state is currently "ON" turn it off
			this.setImageDrawable(getContext().getResources().getDrawable(
					getSound_off_image()));
			new SoundPreferences(getContext()).setSoundPreference(false);
			this.currentState = FlashStatesName.OFF;
		} else {
			// sound is off
			this.setImageDrawable(getContext().getResources().getDrawable(
					getSound_on_image()));
			new SoundPreferences(getContext()).setSoundPreference(true);
			this.currentState = FlashStatesName.ON;
		}
	}

	/**
	 * @return the sound_off_image
	 */
	public Integer getSound_off_image() {
		return sound_off_image;
	}

	/**
	 * @param sound_off_image
	 *            the sound_off_image to set
	 */
	public void setSound_off_image(Integer sound_off_image) {
		this.sound_off_image = sound_off_image;
	}

	/**
	 * @return the sound_on_image
	 */
	public Integer getSound_on_image() {
		return sound_on_image;
	}

	/**
	 * @param sound_on_image
	 *            the sound_on_image to set
	 */
	public void setSound_on_image(Integer sound_on_image) {
		this.sound_on_image = sound_on_image;
	}

}
