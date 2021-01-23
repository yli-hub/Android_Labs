package com.cst2335.li000713;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_linear);
        setContentView(R.layout.activity_main_grid);
        setContentView(R.layout.activity_main_relative);
        TextView myText=findViewById(R.id.text);
        EditText myEdit = findViewById(R.id.edit);
        Button btn = findViewById(R.id.button1);
        btn.setOnClickListener( OnClickListener obj );


    }
}