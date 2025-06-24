package com.example.motivationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class SaveQuoteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String quote = intent.getStringExtra("quote");

        SharedPreferences sharedPreferences = context.getSharedPreferences("MotivationAppPrefs", Context.MODE_PRIVATE);
        Set<String> favorites = sharedPreferences.getStringSet("favorites", new HashSet<>());
        Set<String> updated = new HashSet<>(favorites);
        updated.add(quote);

        sharedPreferences.edit().putStringSet("favorites", updated).apply();

        Toast.makeText(context, "Quote saved from notification!", Toast.LENGTH_SHORT).show();
    }
}
