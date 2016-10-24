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
 * Button��TextView������
 * Button�̳���TextView���������ԡ�
 * ����¼�������д��
 * 
 * ����:�����ť��ӡ��־��Ϣ
 * 1.�����Ҫ������ť���� ��Ҫ�ȵõ�Button����  ��ô��ô�õ�Button����?xml�ļ�������Button��Ψһ��id
 * 2.ͨ��findviewById()��ð�ť����Ȼ��󶨼�����  ��ť����.setOnClickListener()
 * 3.��дOnClickListener�е�onClick�ص�����
 * 
 * Android��ʹ��LogCat
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
        
        // ����Ψһ����Դid���ImageButton�ؼ��Ķ���
        imageButton2=(ImageButton) findViewById(R.id.imageButton2);
        // �ڶ��ַ�ʽ�����������ڲ���ķ�ʽ�󶨵���¼�����������д�ص�����
        imageButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "�����ڲ��෽��ʵ�ֵ���¼���", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    //��һ�ַ�ʽ���ؼ������onClick���Եķ���
    /**
     * ��ť�ĵ���¼��Ĳ���
     * 1.xml�����ļ��е�Button��ǩ���android:onClick����
     * 2.�ڼ��ص�ǰ���ֵ�activity����ӷ������η�Ϊpublic ����ֵΪvoid ����ΪView���͵ķ���
     *   �������Ʊ����android:onClick���Ե�ֵ��ȫ��ͬ
     * 3.�������ťʱ�ͻ�ִ�е�ǰ�����еĴ���
     */
    public void clickButton(View view){
    	Toast.makeText(MainActivity.this, "xml���콣onClick���Եķ���ʵ�ֵ���¼���", Toast.LENGTH_SHORT).show();
    }

    //�����ַ�ʽ����ǰActivityʵ��OnClickListener�ӿڵķ������Ƽ�ʹ�ã�
	@Override
	public void onClick(View v) {
		Toast.makeText(MainActivity.this, "Activityʵ��onClickListener�ӿڵķ���ʵ�ֵ���¼���", Toast.LENGTH_SHORT).show();
	}
    
    
}
