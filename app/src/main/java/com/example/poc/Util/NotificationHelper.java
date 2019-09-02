package com.example.poc.Util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

import com.example.poc.R;

public class NotificationHelper extends ContextWrapper {
    private static final String channelId = "channel_name";
    private static final CharSequence channelName = "my Channel";
    private NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);
    }


    public NotificationChannel notificationSetup() {
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.CYAN);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        return notificationChannel;
    }

    public NotificationManager getNotificationManager() {
        if (notificationManager == null)
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationSetup());
        return notificationManager;

    }

    public Notification.Builder getChannel(String title, String message) {
        return new Notification.Builder(getApplicationContext(), channelId)
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(channelId)
                .setBadgeIconType(Color.RED)
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setAutoCancel(true);
    }
}
