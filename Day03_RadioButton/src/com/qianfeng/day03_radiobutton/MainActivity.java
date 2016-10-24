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
        //当RadioGroup中radioButton的状态改变时执行的监听
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	/* RadioGroup group 表示当前状态改变的RadioButton所在的RadioGroup
			 * int checkedId  当前RadioGroup组中状态改变选中的radioButton的资源id
			 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			    radioButton_selected = (RadioButton) findViewById(checkedId);
				Toast.makeText(MainActivity.this, radioButton_selected.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    public void click(View view){//做啥? 点击时需要将radioButton中选中的内容获取
    	radioButton_selected=(RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());//获取当前radiogroup中选中的radioButton的资源id
    	Toast.makeText(MainActivity.this, "您选择了："+radioButton_selected.getText().toString(), Toast.LENGTH_SHORT).show();
    }
    
}
