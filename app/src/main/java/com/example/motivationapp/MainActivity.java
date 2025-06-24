package com.example.motivationapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Open Home Screen Button
        Button btnOpenhome = findViewById(R.id.button);
        btnOpenhome.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, home.class));
        });

        // Schedule notifications
        scheduleDailyNotifications();
    }

    private void scheduleDailyNotifications() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        scheduleNotificationAtTime(alarmManager, 10, 16);   // 8:00 AM
        scheduleNotificationAtTime(alarmManager, 10, 17);  // 1:00 PM
        scheduleNotificationAtTime(alarmManager, 10, 17);  // 6:00 PM
    }

    private void scheduleNotificationAtTime(AlarmManager alarmManager, int hour, int minute) {
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("quote", "Your motivational quote here");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                hour, // unique ID for each time
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); // Schedule for next day if time has passed
        }

        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }
}
