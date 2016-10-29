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
 * 选择菜单页面
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
			//获得旧密钥
			String oldpasswordText = oldpassword.getText().toString();
			// 如果是空,报错!
			if(oldpasswordText.equals("")){
				showText("旧密钥不能为空！");
				return;
			}
			
			//获得mainActivity中的旧密钥
			//Intent intent = getIntent();
			//String password = MainActivity.passwordValue;//intent.getStringExtra("passwordDB");
			//与mainActivity中的旧密钥相比,如果不同,报错!
			if(0 != MainActivity.verifyPIN(oldpasswordText)){
				showText("旧密钥不正确!");
				return;
			}
			
			//获得新密钥
			String newpasswordText =  newpassword.getText().toString();
			// 如果是空,报错!
			if(newpasswordText.equals("")){
				showText("新密钥不能为空!");
				return;
			}
			//获得确认新密钥
			String newpasswordText1 = newpassword1.getText().toString();
			
			// 如果是空,报错!
			if(newpasswordText1.equals("")){
				showText("确认新密钥不能为空!");
				return;
			}
			//比较新密钥和确认新密钥,如果不同,报错!
			if(!newpasswordText.equals(newpasswordText1)){
				showText("两次输入的新密钥不一致!");
				return;
			}
			
			//如果没错,设置mainActivity中的password值
			//intent.putExtra("passwordDB", newpasswordText);
			//MainActivity.passwordValue = newpasswordText;
			
			//check password format
			short pswLen = (short) newpasswordText.length();
			
			if(pswLen > 15){
				showText("密码长度只能为1-15");
				return;
			}
			
			for(int i = 0; i < pswLen; i++){
				char c = newpasswordText.charAt(i);
//				if (c > 'F' || c < '0'){
				if(!(((c >= '0') && (c <= '9') ) || 
						((c >= 'a') && (c <= 'z') ) ||
						((c >= 'A') && (c <= 'Z')))){
					showText("密码格式不对，只能为数字和大小写字母");
					return;
				}
			}
			
			if(changePin(newpasswordText) != 0){
				showText("密钥设置失败!");
				return;
			}
			
			showText("密钥设置成功!");
			return;
			
		}
	}

	private short changePin(String newPin){
		String verifyPinAPDU = "10230000";
		
		//将文件内容转成unicode码再转成string，因为文件内容可能有汉字
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
