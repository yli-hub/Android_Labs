package com.cst2335.li000713;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_relative);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main_grid);
        //setContentView(R.layout.activity_main_relative);
        //TextView myText=findViewById(R.id.text);
        //EditText myEdit = findViewById(R.id.edit);
        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener((v) -> {
            Toast.makeText(this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();
        });



    }
}