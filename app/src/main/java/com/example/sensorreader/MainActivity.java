package com.example.sensorreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;

    //Instance sensor
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    private Sensor mSensorTemperature;
    private Sensor mSensorPressure;
    private Sensor mSensorHumidity;
//    private Sensor mSensorGyroscope;
//    private Sensor mSensorAccelerometer;


    //Instance text
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;

    private TextView mTextSensorTemperature;
    private TextView mTextSensorPressure;
    private TextView mTextSensorHumidity;
//    private TextView mTextSensorGyroscope;
//    private TextView mTextSensorAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Menampung sensor
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        //String text sensor
        StringBuilder sensorText = new StringBuilder();

        //Loop sensor list
        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);

        mTextSensorTemperature = findViewById(R.id.label_temperature);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorHumidity = findViewById(R.id.label_humidity);
//        mTextSensorAccelerometer = findViewById(R.id.label_accelerometer);
//        mTextSensorGyroscope = findViewById(R.id.label_gryoscope);

        //Sensor
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        mSensorTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
//        mSensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        mSensorGyroscope= mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        String sensor_error = "No sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorTemperature == null){
            mTextSensorTemperature.setText(sensor_error);
        }
        if (mSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorHumidity == null){
            mTextSensorHumidity.setText(sensor_error);
        }
//        if (mSensorAccelerometer == null){
//            mTextSensorAccelerometer.setText(sensor_error);
//        }
//        if (mSensorGyroscope == null){
//            mTextSensorGyroscope.setText(sensor_error);
//        }
    }

    //Daftarin logic sensor
    @Override
    protected void onStart() {
        super.onStart();
        if(mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(mSensorTemperature != null) {
            mSensorManager.registerListener(this, mSensorTemperature,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorPressure != null) {
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorHumidity != null) {
            mSensorManager.registerListener(this, mSensorHumidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
//        if(mSensorAccelerometer != null) {
//            mSensorManager.registerListener(this, mSensorAccelerometer,
//                    SensorManager.SENSOR_DELAY_NORMAL);
//        }
//        if(mSensorGyroscope != null) {
//            mSensorManager.registerListener(this, mSensorGyroscope,
//                    SensorManager.SENSOR_DELAY_NORMAL);
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

    //Perubahan data sensor
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(String.format("Light sensor : %1$.2f lux",currentValue));

                changeBackgroundColorLight(currentValue);

                break;

            case  Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(String.format("Proximity sensor : %1$.2f cm",currentValue));
                break;

            case  Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorTemperature.setText(String.format("Temperature sensor : %1$.2f °C",currentValue));
                break;

            case  Sensor.TYPE_PRESSURE:
                mTextSensorProximity.setText(String.format("Pressure sensor : %1$.2f hPA",currentValue));
                break;

            case  Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(String.format("Humidity sensor : %1$.2f %",currentValue));
                break;

//            case  Sensor.TYPE_ACCELEROMETER:
//                mTextSensorAccelerometer.setText(String.format("Accelerometer sensor : %1$.2f % m",currentValue));
//                break;

//            case  Sensor.TYPE_GYROSCOPE:
//                mTextSensorGyroscope.setText(String.format("Gyroscope sensor : %1$.2f % rad",currentValue));
//                break;

            default:
        }
    }

    private void changeBackgroundColorLight(float currentValue){
        ConstraintLayout layout = findViewById(R.id.layout_constraint);
        if(currentValue >= 20000 && currentValue <= 40000){
            layout.setBackgroundColor(Color.RED);
        }else if (currentValue >= 0 && currentValue < 20000){
            layout.setBackgroundColor(Color.CYAN);
        }
    }

//    private void changeBackgroundColorTemper(float currentValue){
//        ConstraintLayout layout = findViewById(R.id.layout_constraint);
//        if(currentValue >= 20000 && currentValue <= 40000){
//            layout.setBackgroundColor(Color.RED);
//        }else if (currentValue >= 0 && currentValue < 20000){
//            layout.setBackgroundColor(Color.CYAN);
//        }
//    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}