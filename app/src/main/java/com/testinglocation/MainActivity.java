package com.testinglocation;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnShowLocation;

    // GPSTracker class
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            btnShowLocation = (Button) findViewById(R.id.get_location);

            // show location button click event
            btnShowLocation.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // create class object
                    gps = new GPSTracker(MainActivity.this);

                    // check if GPS enabled
                    if(gps.canGetLocation()){

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        // \n is for new line
                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
                        try {
                            List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);

                            String add = "";
                            Toast.makeText(getApplicationContext(),"Locality "+addresses.get(0).getLocality(),Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"State "+addresses.get(0).getAdminArea(),Toast.LENGTH_SHORT).show();
                            if (addresses.size() > 0)
                            {
                                for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();i++) {
                                    add += addresses.get(0).getAddressLine(i) + "\n";
                                }

                            }

                            Toast.makeText(getApplicationContext(),add,Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e1) {
                            e1.printStackTrace();
                        }


                    }else{
                        gps.showSettingsAlert();
                    }

                }
        });
    }

}

