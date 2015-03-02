package com.flashlighter.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class SoundPlayer {

	private Context context;
	private MediaPlayer player;
	private boolean playing;

	public SoundPlayer(Context context) {
		setContext(context);
		playing = false;
	}

	public void playOnSound() {
		
		if (player != null && playing) {
			player.release();
		}

		player = MediaPlayer.create(getContext(), R.raw.button_on_click);
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				playing = false;
			}
		});

		player.start();
		playing = true;
	}

	public void playOffSound() {
		
		if (player != null && playing) {
			player.release();
		}

		player = MediaPlayer.create(getContext(), R.raw.button_off_click);
		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				playing = false;
			}
		});

		player.start();
		playing = true;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * @return the player
	 */
	public MediaPlayer getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(MediaPlayer player) {
		this.player = player;
	}

}
