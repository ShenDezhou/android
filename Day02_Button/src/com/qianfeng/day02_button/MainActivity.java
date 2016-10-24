package com.qianfeng.day02_button;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
/**
 * Button是TextView的子类
 * Button继承了TextView的所有属性。
 * 点击事件的三种写法
 * 
 * 需求:点击按钮打印日志消息
 * 1.如果想要操作按钮对象 需要先得到Button对象  那么怎么得到Button对象?xml文件中设置Button的唯一的id
 * 2.通过findviewById()获得按钮对象然后绑定监听器  按钮对象.setOnClickListener()
 * 3.重写OnClickListener中的onClick回调方法
 * 
 * Android中使用LogCat
 */
public class MainActivity extends Activity implements OnClickListener {
	private ImageButton imageButton2;
	private ViewPager viewpager;
	private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button3=(Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        
        // 根据唯一的资源id获得ImageButton控件的对象
        imageButton2=(ImageButton) findViewById(R.id.imageButton2);
        // 第二种方式：采用匿名内部类的方式绑定点击事件监听器，重写回调方法
        imageButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "匿名内部类方法实现点击事件！", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    //第一种方式：控件中添加onClick属性的方法
    /**
     * 按钮的点击事件的步骤
     * 1.xml布局文件中的Button标签添加android:onClick属性
     * 2.在加载当前布局的activity中添加访问修饰符为public 返回值为void 参数为View类型的方法
     *   方法名称必须和android:onClick属性的值完全相同
     * 3.当点击按钮时就会执行当前方法中的代码
     */
    public void clickButton(View view){
    	Toast.makeText(MainActivity.this, "xml中天剑onClick属性的方法实现点击事件！", Toast.LENGTH_SHORT).show();
    }

    //第三种方式：当前Activity实现OnClickListener接口的方法（推荐使用）
	@Override
	public void onClick(View v) {
		Toast.makeText(MainActivity.this, "Activity实现onClickListener接口的方法实现点击事件！", Toast.LENGTH_SHORT).show();
	}
    
    
}
