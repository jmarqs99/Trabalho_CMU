package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class CheckLocation extends Service {

    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;
    private Estadio[] estadios;
    private Long lastQuestion;
    private final int TIME_BETWEEN_QUESTIONS = 600; //seconds

    private class Estadio extends Location{

        public Estadio(double latitude, double longitude) {
            super("");
            setLatitude(latitude);
            setLongitude(longitude);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        estadios = new Estadio[18];
        estadios[0] = new Estadio(48.21939343463849,11.624625277556701);
        estadios[1] = new Estadio(51.174758119514365,6.38552695539048);
        estadios[2] = new Estadio(51.36448884869094,12.34966334113355);
        estadios[3] = new Estadio(51.03835093346256,7.002226883115411);
        estadios[4] = new Estadio(52.43281972942859,10.803831753600917);
        estadios[5] = new Estadio(51.1741858422861,6.384583168882281);
        estadios[6] = new Estadio(50.07871658660222,8.638854745478667);
        estadios[7] = new Estadio(52.45769470608,13.56872984011033);
        estadios[8] = new Estadio(47.989577604953,7.892877000000001);
        estadios[9] = new Estadio(48.79302104487394,9.231822507934561);
        estadios[10] = new Estadio(49.2382556353651,8.888065928312267);
        estadios[11] = new Estadio(48.323748509303094,10.885860600524877);
        estadios[12] = new Estadio(53.066613290934676,8.837605097813292);
        estadios[13] = new Estadio(52.515271658614836,13.239327038623042);
        estadios[14] = new Estadio(52.03207516179569,8.516414292065436);
        estadios[15] = new Estadio(50.93381249945858,6.875065312508859);
        estadios[16] = new Estadio(50.00259488050244,8.245496749879434);
        estadios[17] = new Estadio(51.55535134591836,7.0671828772460845);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(30000);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Log.d("Geo test", location.getLongitude() + " " + location.getLatitude());
                    for(int i=0;i < estadios.length;i++){
                        if (location.distanceTo(estadios[i]) < 500 && (lastQuestion == null || System.currentTimeMillis()/1000 - lastQuestion >  TIME_BETWEEN_QUESTIONS)){
                            lastQuestion = System.currentTimeMillis()/1000;
                            Log.d("GeoTest","Close to stadium");
                        }
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}