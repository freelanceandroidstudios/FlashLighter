package com.flashlighter.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class Notify {

	public static final Integer REQUEST_CODE = 1001;
	private int mNotificationId;
	NotificationCompat.Builder mBuilder;
	NotificationManager mNotifyMgr;
	boolean launched;

	public Notify(Context context) {

		mBuilder = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(
						context.getResources().getString(
								R.string.notification_title))
				.setContentText(
						context.getResources().getString(
								R.string.notification_content));

		Intent notificationIntent = new Intent(context, FlashActivity.class);
		notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		notificationIntent.setAction(Intent.ACTION_MAIN);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				REQUEST_CODE, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT
						| Notification.FLAG_AUTO_CANCEL);
		mBuilder.setContentIntent(contentIntent);

		mBuilder.setAutoCancel(true);

		// Sets an ID for the notification
		mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		mNotifyMgr = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

	}

	public void cancelNotification() {
		mNotifyMgr.cancel(mNotificationId);
	}

	public void launchNotification() {
		// Builds the notification and issues it.
		mNotifyMgr.notify(mNotificationId, mBuilder.build());
		launched = true;
	}
}
