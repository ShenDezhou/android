package com.example.sdkey;

import java.util.Map;

import Sumavision.Library.ApduBaseCommand;
import Sumavision.Library.ConversionTools;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * ѡ��˵�ҳ��
 *
 */
public class UserMgrActivity extends Activity { 
	private int id=5;
	
	private EditText oldpassword;
	private EditText newpassword;
	private EditText newpassword1;
	
	private Button modifyCommitBtn;
	private Button modifyCancelBtn;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.usermgr);
		
		oldpassword = (EditText) this.findViewById(R.id.oldpasswordText);
		newpassword = (EditText) this.findViewById(R.id.newPasswordText);
		newpassword1 = (EditText) this.findViewById(R.id.newPasswordText1);
		
		modifyCommitBtn = (Button) this.findViewById(R.id.modifyCmt);
		modifyCancelBtn = (Button) this.findViewById(R.id.modifyCancel);
		
		modifyCommitBtn.setOnClickListener(new modifyCommitListener());
		modifyCancelBtn.setOnClickListener(new modifyCancelListener());
		
	}
	
	class modifyCommitListener implements OnClickListener{
		public void onClick(View arg0) {
			//��þ���Կ
			String oldpasswordText = oldpassword.getText().toString();
			// ����ǿ�,����!
			if(oldpasswordText.equals("")){
				showText("����Կ����Ϊ�գ�");
				return;
			}
			
			//���mainActivity�еľ���Կ
			//Intent intent = getIntent();
			//String password = MainActivity.passwordValue;//intent.getStringExtra("passwordDB");
			//��mainActivity�еľ���Կ���,�����ͬ,����!
			if(0 != MainActivity.verifyPIN(oldpasswordText)){
				showText("����Կ����ȷ!");
				return;
			}
			
			//�������Կ
			String newpasswordText =  newpassword.getText().toString();
			// ����ǿ�,����!
			if(newpasswordText.equals("")){
				showText("����Կ����Ϊ��!");
				return;
			}
			//���ȷ������Կ
			String newpasswordText1 = newpassword1.getText().toString();
			
			// ����ǿ�,����!
			if(newpasswordText1.equals("")){
				showText("ȷ������Կ����Ϊ��!");
				return;
			}
			//�Ƚ�����Կ��ȷ������Կ,�����ͬ,����!
			if(!newpasswordText.equals(newpasswordText1)){
				showText("�������������Կ��һ��!");
				return;
			}
			
			//���û��,����mainActivity�е�passwordֵ
			//intent.putExtra("passwordDB", newpasswordText);
			//MainActivity.passwordValue = newpasswordText;
			
			//check password format
			short pswLen = (short) newpasswordText.length();
			
			if(pswLen > 15){
				showText("���볤��ֻ��Ϊ1-15");
				return;
			}
			
			for(int i = 0; i < pswLen; i++){
				char c = newpasswordText.charAt(i);
//				if (c > 'F' || c < '0'){
				if(!(((c >= '0') && (c <= '9') ) || 
						((c >= 'a') && (c <= 'z') ) ||
						((c >= 'A') && (c <= 'Z')))){
					showText("�����ʽ���ԣ�ֻ��Ϊ���ֺʹ�Сд��ĸ");
					return;
				}
			}
			
			if(changePin(newpasswordText) != 0){
				showText("��Կ����ʧ��!");
				return;
			}
			
			showText("��Կ���óɹ�!");
			return;
			
		}
	}

	private short changePin(String newPin){
		String verifyPinAPDU = "10230000";
		
		//���ļ�����ת��unicode����ת��string����Ϊ�ļ����ݿ����к���
		byte[] inSrc = newPin.getBytes();
		newPin = ConversionTools.ByteArrayToString(inSrc, inSrc.length);
		
		verifyPinAPDU += '0' + Integer.toHexString(newPin.length() / 2 + 1) + '0' + Integer.toHexString(newPin.length() / 2) + newPin;
		
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu(verifyPinAPDU);

		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");

		if (retString.equals("0") == false) {
			return 1;
		}
		if (sw.equals("9000") == false) {
			return 2;
		}
		
		return 0;
		
	}
	
	class modifyCancelListener implements OnClickListener{
		public void onClick(View arg0) {
//			Intent intent = new Intent();
//			intent.setClass(UserMgrActivity.this, TabMenu.class);
//			startActivity(intent);
			finish();
		}
	}
	
	private void showText(String str){
		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}
	
}
