package com.example.motivationapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashSet;
import java.util.Set;

public class home extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "MotivationAppPrefs";
    public static final String FAVORITES_KEY = "favorites";
    private TextView textQuote;
    private Button btnSaveToFavorites, btnOpenFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        textQuote = findViewById(R.id.textQuote);
        btnSaveToFavorites = findViewById(R.id.btnSaveToFavorites);
        btnOpenFavorites = findViewById(R.id.btnOpenFavorites);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        btnSaveToFavorites.setOnClickListener(v -> {
            String quote = textQuote.getText().toString();
            saveToFavorites(quote);
        });

        btnOpenFavorites.setOnClickListener(v -> {
            startActivity(new Intent(home.this, favorite.class));
        });
    }

    private void saveToFavorites(String quote) {
        Set<String> favorites = sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>());
        Set<String> updatedFavorites = new HashSet<>(favorites);
        updatedFavorites.add(quote);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(FAVORITES_KEY, updatedFavorites);
        editor.apply();

        Toast.makeText(this, "Quote saved to favorites!", Toast.LENGTH_SHORT).show();
    }

}
