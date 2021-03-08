package com.cst2335.li000713;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ForecastQuery req = new ForecastQuery();
        req.execute( );
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {

        private ForecastQuery (){
        }

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView mintemp = findViewById(R.id.mintemp);
        TextView maxtemp = findViewById(R.id.maxtemp);
        TextView curweather = findViewById(R.id.curweather);
        TextView UVrating = findViewById(R.id.UVrating);
        ImageView currwt = findViewById(R.id.currwt);

        String max = null;
        String min = null;
        String curr = null;
        String uvrt = null;
        Bitmap imagev = null;

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected String doInBackground(String... args) {
            publishProgress(20, 50, 75);
            String ex = null;

            try {
                //create a URL object of what server to contact:
                String weatherURL = "http://api.openweathermap.org/data/2.5/weather?" +
                        "q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";

                URL url = new URL(weatherURL);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                //From part 3, slide 20
                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("temperature")) {
                            //If you get here, then you are pointing to a <Weather> start tag
                            curr = xpp.getAttributeValue(null, "value");
                            max = xpp.getAttributeValue(null, "max");
                            min = xpp.getAttributeValue(null, "min");
                        } else if (xpp.getName().equals("weather")) {
                            String iconName = xpp.getAttributeValue(null, "icon");
                            Log.i("Finding Image", "Finding image " + iconName + ".png");

                            if (fileExistance(iconName + ".png")) {
                                FileInputStream fileInput = null;
                                try {
                                    fileInput = openFileInput(iconName + ".png");
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                imagev = BitmapFactory.decodeStream(fileInput);
                                Log.i("Finding Image", "Found image " + iconName + ".png from local");

                            } else {
                                URL urlImage = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                                HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
                                connection.connect();
                                int responseCode = connection.getResponseCode();
                                if (responseCode == 200) {
                                    InputStream inputStream = connection.getInputStream();
                                    imagev = BitmapFactory.decodeStream(inputStream);
                                }
                                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                //OutputStream outputStream = new FileOutputStream(file);

                                imagev.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                Log.i("Finding Image", "Found image " + iconName + ".png from URL, and download it.");
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }
                String uvURL = "http://api.openweathermap.org/data/2.5/uvi?" +
                        "appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

                URL url2 = new URL(uvURL);

                HttpURLConnection uvUrlConnection = (HttpURLConnection) url2.openConnection();

                InputStream inputStream2 = uvUrlConnection.getInputStream();
                //Set up the XML parser:
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream2,
                        "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject jObject = new JSONObject(result);
                double value = jObject.getDouble("value");
                uvrt = String.valueOf(value);

            } catch (MalformedURLException mfe) {
                ex = "Malformed URL exception";
            } catch (IOException ioe) {
                ex = "IO Exception. Wifi connected?";
            } catch (XmlPullParserException pe) {
                ex = "XML Pull exception";
            } catch (JSONException JSONeX) {
                ex = "Json is not properly formed";
            }
            //What is returned here will be passed as a parameter to onPostExecute:
            return ex;
        }

        @Override                       //Type 2
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Update GUI stuff only:
            try {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(values[0]);
                Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            //update GUI Stuff:
            curweather.setText("Current Temperature is : " + curr);
            mintemp.setText("Minimum Temperature is :" + min);
            maxtemp.setText("Maximum Temperature is :" + max);
            UVrating.setText("UV Rating is :" + uvrt);
            currwt.setImageBitmap(imagev);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }
}