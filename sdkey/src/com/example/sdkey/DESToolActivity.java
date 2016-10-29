package com.example.sdkey;

import java.util.Map;

import Sumavision.Library.ApduBaseCommand;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
/**
 * 选择菜单页面
 *
 */
public class DESToolActivity extends Activity { 
	private int id=2;
	
	private Button genRandomBtn;
	private EditText randomData;
	private Button desEncBtn;
	private Button desDecBtn;
	
	private EditText desKeyText;
	private EditText desSrcText;
	private EditText desRstText;
	
	private RadioGroup keyLenGrpBtn;
//	private RadioButton desKeyLen;
//	private RadioButton des2KeyLen;
//	private RadioButton des3KeyLen;
	
	private RadioGroup algGrpBtn;
	private RadioGroup padGrpBtn;
	
	String INS = "11";
	byte P1 = 5;
	
	boolean currentPad_NoPad = true;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.destool);
		
		genRandomBtn = (Button) this.findViewById(R.id.genRandom);
		randomData = (EditText) this.findViewById(R.id.randomdata);
		desEncBtn = (Button) this.findViewById(R.id.desencBtn);
		desDecBtn = (Button) this.findViewById(R.id.desdecBtn);
		
		keyLenGrpBtn = (RadioGroup) this.findViewById(R.id.deskeyalgo);
		algGrpBtn = (RadioGroup) this.findViewById(R.id.desecbcbc);
		padGrpBtn = (RadioGroup) this.findViewById(R.id.padding);
		
		desKeyText = (EditText) this.findViewById(R.id.deskey);
		desSrcText = (EditText) this.findViewById(R.id.desdata);
		desRstText = (EditText) this.findViewById(R.id.desresult);
		
		
		genRandomBtn.setOnClickListener(new genRandomListener());
		desEncBtn.setOnClickListener(new desEncListener());
		desDecBtn.setOnClickListener(new desDecListener());
		
		//得到RadioButton的值
		keyLenGrpBtn.setOnCheckedChangeListener(new keyLenGrpChangeListener());
		
		algGrpBtn.setOnCheckedChangeListener(new algGrpBtnChangeListener());
		
		padGrpBtn.setOnCheckedChangeListener(new padGrpBtnChangeListener() );
				
		
	}
	
	
	class keyLenGrpChangeListener implements RadioGroup.OnCheckedChangeListener{

		public void onCheckedChanged(RadioGroup arg0, int arg1) {

			switch(arg1){
			case R.id.desalgo:
				INS = "11";
				break;
			case R.id.des2keyalgo:
				INS = "13";
				break;
			case R.id.des3keyalgo:
				INS = "15";
				break;
			}
		}
		
	}
	
	class algGrpBtnChangeListener implements RadioGroup.OnCheckedChangeListener{

		public void onCheckedChanged(RadioGroup arg0, int arg1){
			switch(arg1){
			case R.id.desecb:
				P1 = (byte)5;
				break;
			case R.id.descbc:
				P1 = (byte)1;
				break;
			}
		}
		
	}
	
	class padGrpBtnChangeListener implements RadioGroup.OnCheckedChangeListener{

		public void onCheckedChanged(RadioGroup arg0, int arg1){
			switch(arg1){
			case R.id.nopad:
				currentPad_NoPad = true;
				break;
			case R.id.paddingm2:
				P1 += 2;
				currentPad_NoPad = false;
				break;
			}
		}
		
	
		
	}
	
	class genRandomListener implements OnClickListener{
		public void onClick(View arg0) {
			//发送APDU指令,选择应用,发送生成随机数指令
			//选择应用
			if(FileMgrActivity.selectCipherApp() != 0){
				showText("应用选择失败!");
				return;
			}
			
			Map<String, String> resultMap = ApduBaseCommand
					.exchangeApdu("1041000000");
			//获得返回数
			String retString = resultMap.get("ret");
			String sw = resultMap.get("sw");
			String outdata = resultMap.get("outdata");
			
			if (retString.equals("0") == false) {
				showText("随机数生成失败!");
				return;
			}
			if (sw.equals("9000") == false) {
				showText("随机数生成失败!");
				return;
			}
			
			//显示到EditText里
			randomData.setText(outdata);
		}
	}
	
	class desEncListener implements OnClickListener{
		public void onClick(View arg0) {
			//发送APDU指令,选择应用,发送生成随机数指令
			//选择应用
			if(FileMgrActivity.selectCipherApp() != 0){
				showText("应用选择失败!");
				return;
			}
			
			desCipher("02");
			
		}
		
	}
	
	class desDecListener implements OnClickListener{
		public void onClick(View arg0) {
			
			//发送APDU指令,选择应用,发送生成随机数指令
			//选择应用
			if(FileMgrActivity.selectCipherApp() != 0){
				showText("应用选择失败!");
				return;
			}
			//得到密钥
			//得到原数据
			//发送APDU指令,得到返回值
			//显示到结果里
			desCipher("01");
			
		}
	}
	
	private void desCipher(String mode){
		
		
		String desAPDU = "10";
		
		//得到密钥
		String desKey = desKeyText.getText().toString();
		short keyLen = (short)desKey.length();
		
		//检查密钥格式,0-9,a-f,A-F
		for(short i = 0; i < keyLen; i++){
			char c = desKey.charAt(i);
			if(!(((c >= '0') && (c <= '9') ) || 
					((c >= 'a') && (c <= 'f') ) ||
					((c >= 'A') && (c <= 'F')))){
				showText("密钥格式不对，只能输入0-F的十六进制数");
				return;
			}
		}
		
		
		//得到原数据
		String desSrc = desSrcText.getText().toString();
		int dataLen = desSrc.length();
		
		if((dataLen % 2) == 1){
			showText("数据长度不对，只能按字节输入,不能为奇数");
			return;
		}
		
		//检查源数据格式,0-9,a-f,A-F
		for(int i = 0; i < dataLen; i++){
			char c = desSrc.charAt(i);
			if(!(((c >= '0') && (c <= '9') ) || 
					((c >= 'a') && (c <= 'f') ) ||
					((c >= 'A') && (c <= 'F')))){
				showText("数据格式不对，只能输入0-F的十六进制数");
				return;
			}
		}
		
		
		
		//检查密钥值,des 为16, 2key为32, 3key为48
		if((INS.equals("11") && keyLen != 16) ||
				(INS.equals("13") && keyLen != 32) ||
				(INS.equals("15") && keyLen != 48) 
				){
			showText("密钥长度不对!");
			return;
			
		}
		
		//检查源数据格式,nopad需为8字节的倍数
		if((P1 == 5 || P1 == 1) && currentPad_NoPad && (( dataLen % 16) != 0)){
			showText("数据长度不对,应是8字节整数倍!");
			return;
		}
		
		//如果pad方式未发生变化,需要手动加入
		if((P1 == 5 || P1 == 1) && (!currentPad_NoPad)){
			P1 += 2 ;
		}
		
		String lc = Integer.toHexString((keyLen+dataLen)/2);
		
		if(((keyLen+dataLen)/2) < 16){
			lc = '0' + lc;
		}
		
		desAPDU += INS + '0' + Integer.toHexString(P1) + mode + lc + desKey + desSrc;
		
		//发送APDU指令,得到返回值
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu(desAPDU);

		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");
		String outdata = resultMap.get("outdata");

		if (retString.equals("0") == false) {
			showText("数据加解密错误!");
			return;
		}
		if (sw.equals("9000") == false) {
			showText("数据加解密错误!");
			return;
		}
		
		//显示到结果里
		desRstText.setText(outdata);
		desRstText.requestFocus();
		
		showText("数据加解密成功!");
		
		
	}

	private void showText(String str){
		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
	}
	
	
}
