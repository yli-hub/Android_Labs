package com.cst2335.li000713;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private EditText inputEmail;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_relative);
        setContentView(R.layout.activity_main);

        Button loginbtn = findViewById(R.id.loginbtn);
        inputEmail = findViewById(R.id.editT1);
        pref = getSharedPreferences("email",Context.MODE_PRIVATE);

        String emailVal = pref.getString("email","");
        inputEmail.setText(emailVal);

        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        loginbtn.setOnClickListener(click -> {
            goToProfile.putExtra("email",inputEmail.getText().toString());
            startActivity(goToProfile);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pref = getSharedPreferences("email",Context.MODE_PRIVATE);
        SharedPreferences.Editor editEmail = pref.edit();
        String emailText = inputEmail.getText().toString();
        editEmail.putString("email",emailText);
        editEmail.commit();
    }
}
