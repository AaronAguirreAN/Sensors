package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip=0;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //camera = Camera.open();
        //Camera.Parameters parameters = camera.getParameters();

        if(sensor==null){
            finish();
        }

        sensorEventListener=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x=sensorEvent.values[0];
                System.out.println("valor giro "+x);
                if(x<-5 && whip==0){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                    //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    //camera.setParameters(parameters);
                    //camera.startPreview();
                }else if(x>5&&whip==1){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                    //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    //camera.setParameters(parameters);
                    //camera.stopPreview();
                }

                if(whip==2){
                    sound();
                    whip=0;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    private void sound(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.tortuga);
        mediaPlayer.start();
    }

    private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
}