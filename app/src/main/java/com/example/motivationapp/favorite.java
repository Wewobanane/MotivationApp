package com.example.motivationapp;




import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Set;

public class favorite extends AppCompatActivity {

    LinearLayout favoritesContainer;
    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "MotivationAppPrefs";
    public static final String FAVORITES_KEY = "favorites";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        favoritesContainer = findViewById(R.id.favoritesContainer);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        loadFavorites();
    }

    private void loadFavorites() {
        Set<String> favoritesSet = sharedPreferences.getStringSet(FAVORITES_KEY, null);

        if (favoritesSet != null && !favoritesSet.isEmpty()) {
            // Convert to a list so we can number them
            ArrayList<String> favoritesList = new ArrayList<>(favoritesSet);

            for (int i = 0; i < favoritesList.size(); i++) {
                String quote = favoritesList.get(i);

                TextView textView = new TextView(this);
                textView.setText((i + 1) + ". " + quote); // Add number
                textView.setTextSize(18);
                textView.setPadding(0, 20, 0, 20); // Add space between quotes

                favoritesContainer.addView(textView);
            }
        } else {
            TextView noFavorites = new TextView(this);
            noFavorites.setText("No favorites saved yet.");
            noFavorites.setTextSize(18);
            favoritesContainer.addView(noFavorites);
        }
    }
}