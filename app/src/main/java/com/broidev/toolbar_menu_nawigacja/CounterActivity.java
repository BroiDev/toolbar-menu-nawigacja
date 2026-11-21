package com.broidev.toolbar_menu_nawigacja;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CounterActivity extends AppCompatActivity {

    private int counter = 0;
    private TextView tvCounter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvCounter = findViewById(R.id.tvCounter);

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            counter++;
            updateUI();
            return true;
        } else if (id == R.id.action_subtract) {
            if (counter > 0) {
                counter--;
                updateUI();
            } else {
                Toast.makeText(this, "Licznik nie schodzi poniżej zera!", Toast.LENGTH_SHORT).show();
            }
            return true;
        } else if (id == R.id.action_reset) {
            showResetDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        tvCounter.setText(String.valueOf(counter));
        if (getSupportActionBar() != null) {
            if (counter == 0) {
                getSupportActionBar().setTitle("Licznik");
            } else {
                getSupportActionBar().setTitle("Licznik: " + counter);
            }
        }
    }

    private void showResetDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Zeruj licznik")
                .setMessage("Czy na pewno chcesz wyzerować licznik?")
                .setPositiveButton("Tak", (dialog, which) -> {
                    counter = 0;
                    updateUI();
                })
                .setNegativeButton("Nie", null)
                .show();
    }
}
