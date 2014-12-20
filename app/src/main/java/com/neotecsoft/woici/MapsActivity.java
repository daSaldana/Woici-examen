package com.neotecsoft.woici;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationListener;

import android.os.Build;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MapsActivity extends ActionBarActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    int pin_yo= R.drawable.estoy;
    Marker my_marker;
    LatLng my_latlng;
    private LocationListener lc;
    private LocationRequest lr;
    private boolean mUpdatesRequested = false;
    String lugares[][]= new String[50][3];
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ActionBar)getActionBar()).hide();
        setContentView(R.layout.buscar_negocio);
        try {
            PackageInfo info =     getPackageManager().getPackageInfo("com.neotecsoft.woici",     PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign=Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("KEY HASH:", sign);
                //Toast.makeText(getApplicationContext(),sign,     Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        set_lugare();
        setUpMapIfNeeded();
        lr = LocationRequest.create();
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        lc = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                my_marker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                my_latlng=new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15);
                mMap.animateCamera(cameraUpdate);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        ImageButton imageButton = (ImageButton)findViewById(R.id.imageButton_buscar);
        final EditText texto_buscar= (EditText)findViewById(R.id.editText_buscador);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                my_marker = mMap.addMarker(new MarkerOptions().position(my_latlng).title("Mi posición").icon(BitmapDescriptorFactory.fromResource(pin_yo)));
                for (int t=0; t<=16;t++){
                    if(lugares[t][0].toLowerCase().contains(texto_buscar.getText().toString().toLowerCase())){
                        mMap.addMarker(new MarkerOptions().
                                position( new LatLng(Double.valueOf(lugares[t][1].split(",")[0]),Double.valueOf(lugares[t][1].split(",")[1])))
                                        .title(lugares[t][0]).icon(BitmapDescriptorFactory.fromResource(R.drawable.lugar)));
                    }
                }
            }
        });

        // Note that location updates are off until the user turns them on
        mUpdatesRequested = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);
        my_marker = mMap.addMarker(new MarkerOptions().position(new LatLng(-33.4414405,-70.652387)).title("Mi posición").icon(BitmapDescriptorFactory.fromResource(pin_yo)));
        //mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
          //  @Override
          //  public void onMyLocationChange(Location location) {
            //}
        //});

    }



    @Override
    public void onLocationChanged(Location l2) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(l2.getLatitude(), l2.getLongitude()), 15);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        /*lc.requestLocationUpdates(lr, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                my_marker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                my_latlng=new LatLng(location.getLatitude(),location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(location.getLatitude(), location.getLongitude()), 15);
                mMap.animateCamera(cameraUpdate);
            }
        });
*/
    }

    @Override
    public void onDisconnected() {

    }
    public  void set_lugare(){
        lugares[0][0]="Eva Isidora Spa Urbano";
        lugares[0][1]="-33.394509,-70.515798";
        lugares[1][0]="Eva Isidora Spa Urbano";
        lugares[1][1]="-33.411667,-70.550000";
        lugares[2][0]="Eva Isidora Spa Urbano";
        lugares[2][1]="-33.413959,-70.603595";
        lugares[3][0]="Eva Isidora Spa Urbano";
        lugares[3][1]="-33.443847,-70.653701";
        lugares[4][0]="Eva Isidora Spa Urbano";
        lugares[4][1]="-33.405296,-70.572896";
        lugares[5][0]="Eva Isidora Spa Urbano";
        lugares[5][1]="-33.438103,-70.625090";
        lugares[6][0]="Eva Isidora Spa Urbano";
        lugares[6][1]="-33.438764,-70.653836";
        lugares[7][0]="Mia & Chic Spa";
        lugares[7][1]="-33.413955,-70.603595";
        lugares[8][0]="Spa Zen";
        lugares[8][1]="-33.552773,-70.774585";
        lugares[9][0]="Spa One&Only";
        lugares[9][1]="-33.382480,-70.572204";
        lugares[10][0]="Equilibrio Masaje Spa";
        lugares[10][1]="-33.413648,-70.554874";
        lugares[11][0]="Equilibrio Masaje Spa";
        lugares[11][1]="-33.418665,-70.602624";
        lugares[12][0]="Todopiel";
        lugares[12][1]="-33.429798,-70.619633";
        lugares[13][0]="Todopiel";
        lugares[13][1]="-33.433538,-70.774887";
        lugares[14][0]="Todopiel";
        lugares[14][1]="-33.388825,-70.545271";
        lugares[15][0]="Todopiel";
        lugares[15][1]="-33.428775,-70.540588";
        lugares[16][0]="Carmen Steffens";
        lugares[16][1]="-33.415425,-70.606711";
    }
}
