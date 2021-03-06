package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView text1;
    private TextView text2;
    private Sensor sensor;
    private SensorManager sm;
    private double MagnitudePrev=0;
    private double sumM=0;
    private double maxMagnitude;
    private double minMagnitude;
    private Integer step=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        text1=(TextView)findViewById(R. id.text1);
        text2=(TextView)findViewById(R. id.text2);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event!=null){
            float x_acc=event.values[0];
            float y_acc=event.values[1];
            float z_acc=event.values[2];

            double Magnitude = Math.sqrt(x_acc*x_acc+y_acc*y_acc+z_acc*z_acc);
            if(MagnitudePrev==0) {
                MagnitudePrev=Magnitude;
                maxMagnitude=Magnitude;
                minMagnitude=Magnitude;
            }

            if(Magnitude>maxMagnitude){
                maxMagnitude=Magnitude;
            }
            if(Magnitude<minMagnitude){
                minMagnitude=Magnitude;
            }

            double MagnitudeDelta = Magnitude - MagnitudePrev;
            MagnitudePrev=Magnitude;

            if(MagnitudeDelta > 2){
                step++;
            }
            double delta=maxMagnitude-minMagnitude;
            text1.setText("Step: "+step);
            //text2.setText("max: "+maxMagnitude+" min: "+minMagnitude+" delta: "+delta);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

}