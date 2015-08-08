package com.example.alumno.accelerometertestapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.GpsSatellite;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener{

    private boolean isAccelerometerEnable = false;
    private boolean locationRetreived = false;
    private boolean isRetreivingLocation = false;

    private TextView textview_x_axis;
    private TextView textview_y_axis;
    private TextView textview_z_axis;

    private TextView textview_location;
    private SensorManager sensorManager;

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview_x_axis = (TextView)findViewById(R.id.textview_x_axis);
        textview_y_axis = (TextView)findViewById(R.id.textview_y_axis);
        textview_z_axis = (TextView)findViewById(R.id.textview_z_axis);

        textview_location = (TextView)findViewById(R.id.textview_location);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

      switch(item.getItemId()) {
          case R.id.switch_accelerator:

              if(isAccelerometerEnable)
              {
                  item.setTitle(R.string.start_accelerometer);
                  stopAccelerometer(); isAccelerometerEnable = false;
              }
              else{
                  item.setTitle(R.string.stop_accelerometer);
                  startAccelerometer(); isAccelerometerEnable = true;

              }
              break;

          case R.id.switch_location_retriever:
              if(isRetreivingLocation)
              {
                  item.setTitle(R.string.start_location_retreiver);

                  //if(!locationRetreived){textview_location.setText("");}

                  isRetreivingLocation = false;
                  stopLocationRetrieving();
              }
              else{
                  item.setTitle(R.string.stop_location_retreiver);
                  isRetreivingLocation = true;
                  textview_location.setText(getString(R.string.retreiving_location));
                  startLocationRetreiving();
              }

              break;

      }
        return super.onOptionsItemSelected(item);
    }


    private void showLocationRetreiving(final Location location){

        final String locationInfo = getString(R.string.provider,location.getProvider())+"\n"+
                        getString(R.string.latitude,location.getLatitude())+"\n"+
                        getString(R.string.longitud,location.getLongitude())+"\n"+
                        getString(R.string.altitud,location.getAltitude())+"\n"+
                        getString(R.string.accuracy,location.getAccuracy());

        textview_location.setText(locationInfo);
    }

    private void startLocationRetreiving(){

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);

    }

    private void stopLocationRetrieving(){
        //ocationRetreived = true;
        locationManager.removeUpdates(this);

    }

    /****/
    private void updateTextViews(float x, float y, float z){

        textview_x_axis.setText(getString(R.string.accelerometer_x,x));
        textview_y_axis.setText(getString(R.string.accelerometer_y, y));
        textview_z_axis.setText(getString(R.string.accelerometer_z, z));
    }

    private void startAccelerometer(){

        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

    }

    private void stopAccelerometer(){
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateTextViews(event.values[0], event.values[1], event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /****/

    @Override
    public void onLocationChanged(Location location) {
            showLocationRetreiving(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {// Se activa cuando prenden el provedor
        Toast.makeText(getApplicationContext(),provider+ "Activado",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {// Se activa cuando apagan el provedor

        Toast.makeText(getApplicationContext(),provider+ "Desactivado",Toast.LENGTH_SHORT).show();
    }
    /****/
}
