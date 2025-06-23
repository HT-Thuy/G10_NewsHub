package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            int systemBars = WindowInsetsCompat.Type.systemBars();
            v.setPadding(insets.getInsets(systemBars).left,
                    insets.getInsets(systemBars).top,
                    insets.getInsets(systemBars).right,
                    insets.getInsets(systemBars).bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            Fragment selectedFragment = null;

            int itemId = menuItem.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_categories) {
                selectedFragment = new CategoryFragment();
            } else if (itemId == R.id.nav_news) {
                selectedFragment = new NewsFragment();
            } else if (itemId == R.id.nav_bookmark) {
                selectedFragment = new BookmarkFragment();
            } else if (itemId == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }

    private void replaceFragment(Fragment fragment) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}