package com.qf.sensordemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private SensorManager sensorManager = null;
	private Sensor sensor = null;
	private Sensor sensor1=null;
	private TextView textView = null;

	private float [] sensor_values=new float[3];
	private float [] sensor1_values=new float[3];
	private float [] values=new float[9];
	private float [] v_alues=new float[3];
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		// sensorManager.registerListener(new SensorEventListener() {
		//
		// @Override
		// public void onSensorChanged(SensorEvent event) {
		// // TODO Auto-generated method stub
		// textView.setText("" + event.values[0]);
		// }
		//
		// @Override
		// public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// // TODO Auto-generated method stub
		//
		// }
		// }, sensor, SensorManager.SENSOR_DELAY_GAME);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensor1 =sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				// textView.setText("X:" + event.values[0] + "\n" + "Y:"
				// + event.values[1] + "\n" + "Z:" + event.values[2] + "");
				// if(event.values[0])
				for (int i = 0; i < 3; i++) {
					sensor_values[i]=event.values[i];
				}
				SensorManager.getRotationMatrix(values, null, sensor_values, sensor1_values);
				SensorManager.getOrientation(values, v_alues);
				 textView.setText("Z:" + v_alues[0] + "\n" + "X:"
				 + v_alues[1] + "\n" + "Y:" + v_alues[2] + "");
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub

			}
		}, sensor, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				for (int i = 0; i < 3; i++) {
					sensor1_values[i]=event.values[i];
				}
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		}, sensor1, SensorManager.SENSOR_DELAY_GAME);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
