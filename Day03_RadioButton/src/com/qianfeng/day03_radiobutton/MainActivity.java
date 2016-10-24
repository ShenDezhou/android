package com.qianfeng.day03_radiobutton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	private RadioGroup radioGroup;
	private RadioButton radioButton_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        //��RadioGroup��radioButton��״̬�ı�ʱִ�еļ���
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	/* RadioGroup group ��ʾ��ǰ״̬�ı��RadioButton���ڵ�RadioGroup
			 * int checkedId  ��ǰRadioGroup����״̬�ı�ѡ�е�radioButton����Դid
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			    radioButton_selected = (RadioButton) findViewById(checkedId);
				Toast.makeText(MainActivity.this, radioButton_selected.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    public void click(View view){//��ɶ? ���ʱ��Ҫ��radioButton��ѡ�е����ݻ�ȡ
    	radioButton_selected=(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());//��ȡ��ǰradiogroup��ѡ�е�radioButton����Դid
    	Toast.makeText(MainActivity.this, "��ѡ���ˣ�"+radioButton_selected.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    
}
