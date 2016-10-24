package com.qianfeng.day02_spinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Spinner spinner;
	private String[] strs = {"语文","数学","英语","物理","化学"};
	private ArrayAdapter<String> adapter;
	private TextView textView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner=(Spinner) findViewById(R.id.spinner);
        textView=(TextView) findViewById(R.id.textView);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, strs);
        spinner.setAdapter(adapter);
        
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
//				Object object = parent.getItemAtPosition(position);
//				textView.setText("您选择的科目是："+object.toString());
				
				String str = adapter.getItem(position);
				textView.setText("您选择的科目是："+str);
				
//				Object object = spinner.getSelectedItem();
//				textView.setText("您选择的科目是："+object.toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
