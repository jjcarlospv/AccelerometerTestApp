package com.example.alumno.accelerometertestapp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private boolean isAccelerometerEnable = false;

    private TextView textview_x_axis;
    private TextView textview_y_axis;
    private TextView textview_z_axis;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview_x_axis = (TextView)findViewById(R.id.textview_x_axis);
        textview_y_axis = (TextView)findViewById(R.id.textview_y_axis);
        textview_z_axis = (TextView)findViewById(R.id.textview_z_axis);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);


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
              return true;
          default:
              return super.onOptionsItemSelected(item);

      }

    }

    private void updateTextViews(float x, float y, float z){

        textview_x_axis.setText(getString(R.string.accelerometer_x,x));
        textview_y_axis.setText(getString(R.string.accelerometer_y, y));
        textview_z_axis.setText(getString(R.string.accelerometer_z, z));
    }

    private void startAccelerometer(){

        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);

    }

    private void stopAccelerometer(){
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        updateTextViews(event.values[0],event.values[1], event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
