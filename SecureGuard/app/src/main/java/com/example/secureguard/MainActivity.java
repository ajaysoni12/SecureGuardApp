package com.example.secureguard;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.secureguard.Database.RoomDB;
import com.example.secureguard.Model.Key;
import com.example.secureguard.Utility.AuthenticatorTask;
import com.example.secureguard.Utility.FingerprintAuthenticator;
import com.example.secureguard.fragments.AboutUsFragment;
import com.example.secureguard.fragments.HomeFragment;
import com.example.secureguard.fragments.MessageFragment;
import com.example.secureguard.fragments.ToolsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnView;
    RelativeLayout RLMain;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        persistKey();

        try {
            AuthenticatorTask authenticatorTask = new AuthenticatorTask() {
                @Override
                public void afterValidationSuccess() {
                    RLMain.setVisibility(View.VISIBLE);
                }

                @Override
                public void onValidationFailed() {
                    KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
                    if (keyguardManager.isKeyguardSecure()) {
                        (MainActivity.this).finish();
                        System.exit(0);
                    } else {
                        Toast.makeText(MainActivity.this, "Phone doesn't contain password", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            List<Key> keyList = database.keyDao().getAllKey();
            Boolean security = keyList.get(0).getSecurityKey();
            if (security) {
                FingerprintAuthenticator fingerprintAuthenticator = new FingerprintAuthenticator(MainActivity.this, authenticatorTask, RLMain);
                fingerprintAuthenticator.addAuthentication();
            } else {
                RLMain.setVisibility(View.VISIBLE);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    loadFragment(new HomeFragment(), false);
                } else if (itemId == R.id.nav_message) {
                    loadFragment(new MessageFragment(), false);
                } else if (itemId == R.id.nav_changeKey) {
                    loadFragment(new ToolsFragment(), false);
                } else if (itemId == R.id.nav_aboutUs) {
                    loadFragment(new AboutUsFragment(), false);
                }
                return true;
            }
        });
        loadFragment(new HomeFragment(), true);
        bnView.setSelectedItemId(R.id.nav_home);


    }

    private void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (flag)
            fragmentTransaction.add(R.id.frameLayout, fragment);
        else
            fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


    private void persistKey() {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            SharedPreferences.Editor editor = prefs.edit();

            database = RoomDB.getInstance(MainActivity.this);
            if (!(prefs.getBoolean("firstTime", false))) {
                Key key = new Key();
                key.setKey("DAHdfDHG53LDHLAD#(33EAG");
                key.setMessageBackup(true);
                key.setSecurityKey(false);
                database.keyDao().saveItem(key);
                editor.putBoolean("firstTime", true);
                editor.apply();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            (MainActivity.this).finish();
            System.exit(0);
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(this, "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    private void init() {

        bnView = findViewById(R.id.bnView);
        RLMain = findViewById(R.id.RLMain);
    }
}