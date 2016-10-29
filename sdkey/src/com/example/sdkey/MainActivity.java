package com.example.sdkey;

import java.util.Map;

import Sumavision.Library.ApduBaseCommand;
import Sumavision.Library.ConversionTools;
import Sumavision.Library.SmartSDLib;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText user;// 用户名
	private EditText password;// 密码
	
	Context context;
	
	static final String StaticUserName = "tester";
	
	//public static String passwordValue = "tester";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.login);
		
		user = (EditText) findViewById(R.id.edi1);
		password = (EditText) findViewById(R.id.edi2);
		
		
//		View v = findViewById(R.id.loginbtn);
//		v.getBackground().setAlpha(80);
	}
	
	// 登录按钮监听
		public void onload(View v)
		{
			String userName = "";

			String userText = user.getText().toString();
			String passwordText = password.getText().toString();
			
//			判断用户名是否为空
			if (userText.equals(""))
			{
				DialogDemo.builder(MainActivity.this, "错误信息",
						"用户名不能为空！");
			}
//			判断密码是否为空
			else if (password.getText().toString().equals(""))
			{
				DialogDemo.builder(MainActivity.this, "错误信息",
						"密码不能为空！");
			} 
//			判断用户名和密码是否正确
			else 
			{
				
				if (!userText.equals(StaticUserName) ) {
					DialogDemo.builder(MainActivity.this, "错误信息",
							"用户名或密码错误，请重新输入");
				}
				//连接设备
				
				byte[] outdata = new byte[512];
				int outdataLen[] = new int[1];

				int ret = SmartSDLib.getInstance().SDConnect(context, outdataLen, outdata);
//				if (ret != 0)
//				{
//					showText("设备已被连接");
//					return;
//				}
				ret = SmartSDLib.getInstance().Reset(outdataLen, outdata);
//				if (ret != 0)
//				{
//					showText("设备已被连接");
//					return;
//				}
//				showText("设备已连接");
				
				//选择应用
				if(FileMgrActivity.selectCipherApp() != 0){
					DialogDemo.builder(MainActivity.this, "错误信息",
							"设备未连接");
					return;
				}
				
				//check password format
				short pswLen = (short) passwordText.length();
				
				if(pswLen > 15){
					showText("密码长度只能为1-15");
					return;
				}
				
				for(int i = 0; i < pswLen; i++){
					char c = passwordText.charAt(i);
					if(!(((c >= '0') && (c <= '9') ) || 
							((c >= 'a') && (c <= 'z') ) ||
							((c >= 'A') && (c <= 'Z')))){
						showText("密码格式不对，只能为数字和大小写字母");
						return;
					}
				}
				
				//发送verifyPIN指令
				if(verifyPIN(passwordText) != 0){
					DialogDemo.builder(MainActivity.this, "错误信息",
							"用户名或密码错误，请重新输入");
					return;
				}
				
				Intent intent = new Intent();
				intent.putExtra("username",StaticUserName);
				intent.putExtra("passwordDB", StaticUserName);
				
				intent.setClass(MainActivity.this, TabMenu.class);
				startActivity(intent);
				
				
			}
			
		}
		
		
		public static short verifyPIN(String passwordText){
			String verifyPinAPDU = "10210000";
			
			//将文件内容转成unicode码再转成string，因为文件内容可能有汉字
			byte[] inSrc = passwordText.getBytes();
			passwordText = ConversionTools.ByteArrayToString(inSrc, inSrc.length);
			
			verifyPinAPDU += '0' + Integer.toHexString(passwordText.length() / 2 + 1) + '0' + Integer.toHexString(passwordText.length() / 2) + passwordText;
			
			Map<String, String> resultMap = ApduBaseCommand
					.exchangeApdu(verifyPinAPDU);

			String retString = resultMap.get("ret");
			String sw = resultMap.get("sw");
//			String outdata = resultMap.get("outdata");

			if (retString.equals("0") == false) {
				return 1;
			}
			if (sw.equals("9000") == false) {
				return 2;
			}
			
			return 0;
			
		}
		
//		public void finish(){
//			byte[] outdata = new byte[512];
//			int outdataLen[] = new int[1];
//			SmartSDLib.getInstance().Endtransmission(outdataLen, outdata);
//		}
		
		
		
		private void showText(String str){
			Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
	        toast.setGravity(Gravity.BOTTOM, 0, 0);
	        toast.show();
		}
}
