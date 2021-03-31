package com.cst2335.li000713;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageButton mImageButton;
    private EditText email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImageButton = findViewById(R.id.imgbtn);
        email = findViewById(R.id.petext2);
        Log.e(ACTIVITY_NAME, "in function" + " onCreate");

        mImageButton.setOnClickListener(click -> dispatchTakePictureIntent());
        Intent fromMain = getIntent();
        email.setText(fromMain.getStringExtra("email"));

        Button CheckWeather = findViewById(R.id.CheckWeather);
        Intent Checkweather = new Intent(ProfileActivity.this, WeatherForecast.class);
        CheckWeather.setOnClickListener(click -> {
            startActivity(Checkweather);
        });

        Button chatbtn = findViewById(R.id.chatbtn);
        Intent goToChatrm = new Intent(ProfileActivity.this, ChatRoomActivity.class);
        chatbtn.setOnClickListener(click -> {
            startActivity(goToChatrm);
        });

         }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }


       @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME,"in function:"+ "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME,"in function: onResume()");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: " + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: " + "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: " + "onDestroy");

    }
}