package com.broidev.toolbar_menu_nawigacja;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private View sectionForm, sectionSettings;
    
    private CheckBox cbNotifications, cbDark, cbSound, cbAutosave;
    private TextView tvSettingsSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = findViewById(R.id.etName);
        sectionForm = findViewById(R.id.sectionForm);
        sectionSettings = findViewById(R.id.sectionSettings);
        
        cbNotifications = findViewById(R.id.cbNotifications);
        cbDark = findViewById(R.id.cbDark);
        cbSound = findViewById(R.id.cbSound);
        cbAutosave = findViewById(R.id.cbAutosave);
        tvSettingsSummary = findViewById(R.id.tvSettingsSummary);
        
        setUpSettingsListeners();

        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_form) {
                showSection(true);
                return true;
            } else if (id == R.id.nav_settings) {
                showSection(false);
                return true;
            }
            return false;
        });
    }

    private void showSection(boolean form) {
        sectionForm.setVisibility(form ? View.VISIBLE : View.GONE);
        sectionSettings.setVisibility(form ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            share();
            return true;
        } else if (id == R.id.action_clear) {
            clearForm();
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        } else if (id == R.id.action_reset) {
            resetSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    private void setUpSettingsListeners() {
        cbNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> updateSettingsSummary());
        cbDark.setOnCheckedChangeListener((buttonView, isChecked) -> updateSettingsSummary());
        cbSound.setOnCheckedChangeListener((buttonView, isChecked) -> updateSettingsSummary());
        cbAutosave.setOnCheckedChangeListener((buttonView, isChecked) -> updateSettingsSummary());
    }

    private void updateSettingsSummary() {
        StringBuilder summary = new StringBuilder();
        if (cbNotifications.isChecked()) summary.append(getString(R.string.setting_notifications)).append("\n");
        if (cbDark.isChecked()) summary.append(getString(R.string.setting_dark)).append("\n");
        if (cbSound.isChecked()) summary.append(getString(R.string.setting_sound)).append("\n");
        if (cbAutosave.isChecked()) summary.append(getString(R.string.setting_autosave)).append("\n");

        if (summary.length() > 0) {
            tvSettingsSummary.setText(summary.toString().trim());
        } else {
            tvSettingsSummary.setText(R.string.settings_summary_empty);
        }
    }
    
    private void resetSettings() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.reset_dialog_title)
                .setMessage(R.string.reset_dialog_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    cbNotifications.setChecked(false);
                    cbDark.setChecked(false);
                    cbSound.setChecked(false);
                    cbAutosave.setChecked(false);
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void clearForm() {
        etName.setText("");
        Toast.makeText(this, R.string.cleared, Toast.LENGTH_SHORT).show();
    }

    private void share() {
        String text = etName.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, R.string.nothing_to_share, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        try {
            startActivity(Intent.createChooser(intent, getString(R.string.share_via)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, R.string.error_no_app, Toast.LENGTH_SHORT).show();
        }
    }
}