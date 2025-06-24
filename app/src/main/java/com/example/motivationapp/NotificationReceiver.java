package com.example.motivationapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    public static final String CHANNEL_ID = "MotivationChannel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String quote = intent.getStringExtra("quote");

        // Create "Save" action intent
        Intent saveIntent = new Intent(context, SaveQuoteReceiver.class);
        saveIntent.putExtra("quote", quote);
        PendingIntent savePendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                saveIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Create notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android 8+ channel requirement
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Motivation Quotes", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_motivation) // Add your icon here
                .setContentTitle("Daily Motivation")
                .setContentText(quote)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_save, "Save", savePendingIntent); // Save action

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
