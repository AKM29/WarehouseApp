package com.example.hillcrest_warehouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements SensorEventListener {

    String timer;


    Paint p = new Paint();

    SensorManager sm;
    Sensor s;

    public class DrawableView extends View {

        public DrawableView(Context context) {
            super(context);


        }

        @Override
        protected void onDraw(Canvas c) {


            p.setTextSize(0);
            c.drawText(" " + timer, 20, 100, p);

            invalidate();
        }

    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        DrawableView v = new DrawableView(this);
        v.setBackgroundResource(R.drawable.warehouse);
        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer = Long.toString(millisUntilFinished / 500);
            }

            @Override
            public void onFinish() {
                nextActivity();
            }
        }.start();

        setContentView(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor s = event.sensor;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void nextActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}