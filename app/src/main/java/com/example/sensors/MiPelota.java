package com.example.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MiPelota extends View implements SensorEventListener{
    Paint pincel = new Paint();
        int alto, ancho,fondo;
        int tamano = 60;
        int borde = 30;

        float ejex=0,ejey=0,ejez=0;
        String Z,Y,X;

        public MiPelota(MainActivity interfaz) {
        super(interfaz);
            SensorManager smAdministrador = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);
            Sensor snsRotation = smAdministrador.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            smAdministrador.registerListener(this,snsRotation,SensorManager.SENSOR_DELAY_FASTEST);
            Display pantalla = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            ancho = pantalla.getWidth();
            borde = pantalla.getHeight();
        }

    @Override
    public void onSensorChanged(SensorEvent cambio) {
        ejex=cambio.values[0];
        X=Float.toString(ejex);
        if(ejex<(tamano+borde)){
            ejex=(tamano+borde);
        }else{
            if(ejex>(ancho-(tamano+borde))){
                ejex=ancho-(tamano+borde);
            }
        }

        ejey=cambio.values[1];
        Y=Float.toString(ejey);
        if(ejey<(tamano+borde)){
            ejey=(tamano+borde);
        }else{
            if(ejey>(alto-tamano-170)){
                ejey=(alto-tamano-170);
            }
        }
        ejez=cambio.values[2];
        Z=String.format("%.2f",ejez);
        invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accurracy) {

    }

    @Override
    protected void onDraw(Canvas lienzo) {
        pincel.setColor(Color.BLACK);
        lienzo.drawCircle(ejex,ejey,ejez+tamano,pincel);
        pincel.setColor(Color.WHITE);
        pincel.setTextSize(25);
        lienzo.drawText("Hola bebé",ejex=35,ejey=3,pincel);
    }

}
