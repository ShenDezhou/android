package com.example.sdkey;

import java.util.Map;

import com.example.sdkey.DESToolActivity.genRandomListener;

import Sumavision.Library.ApduBaseCommand;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 选择菜单页面
 *
 */
public class RSAToolActivity extends Activity { 
	private int id=1;
	
	private Button genRSAKeyBtn;
	private EditText rsaPubKey;
	private EditText rsaPriKey;
	private EditText rsaModule;
	
	private Button encRsaBtn;
	private Button decRsaBtn;
	private Button signRsaBtn;
	private Button verifyRsaBtn;
	
	private EditText rsaSrc;
	private EditText rsaRst;
	
	final static short rsaKeyLength = 128;
	final static String INS_ENC = "33";
	final static String INS_DEC = "35";
	final static String INS_SIGN = "37";
	final static String INS_VERIFY = "39";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.rsatool);
		
		genRSAKeyBtn = (Button) this.findViewById(R.id.genRSAKey);
		genRSAKeyBtn.setOnClickListener(new genRSAKeyListener());
		
		rsaPubKey = (EditText) this.findViewById(R.id.pubKey);
		rsaPriKey = (EditText) this.findViewById(R.id.priKey);
		rsaModule = (EditText) this.findViewById(R.id.module);
		
		encRsaBtn = (Button) this.findViewById(R.id.pubEncrypt);
		decRsaBtn = (Button) this.findViewById(R.id.priDecrpt);
		signRsaBtn = (Button) this.findViewById(R.id.priSign);
		verifyRsaBtn = (Button) this.findViewById(R.id.pubVerify);
		
		encRsaBtn.setOnClickListener(new encRsaListener());
		decRsaBtn.setOnClickListener(new decRsaListener());
		signRsaBtn.setOnClickListener(new signRsaListener());
		verifyRsaBtn.setOnClickListener(new verifyRsaListener());
		
		rsaSrc = (EditText) this.findViewById(R.id.data);
		rsaRst = (EditText) this.findViewById(R.id.result);
		
		
	}
	
	class genRSAKeyListener implements OnClickListener{
		public void onClick(View arg0) {
			//发送APDU指令,选择应用,发送生成随机数指令
			//选择应用
			if(FileMgrActivity.selectCipherApp() != 0){
				showText("应用选择失败!");
				return;
			}
			
			Map<String, String> resultMap = ApduBaseCommand
					.exchangeApdu("1031000000");
			//获得返回数
			String retString = resultMap.get("ret");
			String sw = resultMap.get("sw");
			String outdata = resultMap.get("outdata");
			
			if (retString.equals("0") == false) {
				showText("RSA密钥对生成失败!");
				return;
			}
			if (sw.equals("9000") == false) {
				showText("RSA密钥对生成失败!");
				return;
			}
			
			//显示到EditText里
			rsaPubKey.setText("010001");
//			rsaPriKey.setText(outdata.substring(0, 191));
//			rsaModule.setText(outdata.substring(192, 383));
			rsaPriKey.setText(outdata.substring(0, 255));
			
			resultMap = ApduBaseCommand
					.exchangeApdu("103B000000");
			//获得返回数
			retString = resultMap.get("ret");
			sw = resultMap.get("sw");
			outdata = resultMap.get("outdata");
			
			if (retString.equals("0") == false) {
				return;
			}
			if (sw.equals("9000") == false) {
				return;
			}
			
			rsaModule.setText(outdata.substring(0, 255));
		}
	}
	
	class encRsaListener implements OnClickListener{
		public void onClick(View arg0) {
			rsaCipher(INS_ENC);
			
		}
	}
	
	private void rsaCipher(String INS){
		String APDU = "10";
		
		
		//如果公钥和模为空,抛错
		if( rsaPubKey.getText().equals("") || rsaModule.getText().equals("") ){
			showText("未生成RSA密钥对!");
			return;
		}
		
		//如果输入数据格式不对,长度不对
		//得到数据
		String rsaData = rsaSrc.getText().toString();
		short dataLen = (short)rsaData.length();
		String LC = Integer.toHexString(dataLen/2);
		
		if(dataLen < 32){
			LC = '0' + LC;
		}
		
		if(INS.equals(INS_VERIFY)){
			rsaData = LC + rsaData + rsaRst.getText().toString();
			dataLen = (short)rsaData.length();
			LC = Integer.toHexString(dataLen/2);
			if(dataLen < 32){
				LC = '0' + LC;
			}
		}
		
		if(dataLen == 0){
			showText("输入数据不能为空");
			return;
		}
		
		if((dataLen % 2) == 1){
			showText("数据长度不对，只能按字节输入,不能为奇数");
			return;
		}
		
		//检查密钥格式,0-9,a-f,A-F
		for(short i = 0; i < dataLen; i++){
			char c = rsaData.charAt(i);
			if(!(((c >= '0') && (c <= '9') ) || 
					((c >= 'a') && (c <= 'f') ) ||
					((c >= 'A') && (c <= 'F')))){
				showText("源数据格式不对，只能输入0-F的十六进制数");
				return ;
			}
		}
		
		//选择应用
		if(FileMgrActivity.selectCipherApp() != 0){
			showText("应用选择失败!");
			return ;
		}
		
		APDU = APDU + INS + "0000" + LC +  rsaData;
		
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu(APDU);
		//获得返回数
		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");
		String outdata = resultMap.get("outdata");
		
		if (retString.equals("0") == false) {
			showText("RSA计算失败!");
			return ;
		}
		if (sw.equals("9000") == false) {
			if(INS.equals(INS_VERIFY)){
				showText("RSA验签结果错误!");
				return;
			}else{
				showText("RSA计算失败!");
				return;
			}
			
		}
		if(INS.equals(INS_VERIFY)){
			showText("RSA验签结果正确!");
			return;
		}else{
			rsaRst.setText(outdata);
			rsaRst.requestFocus();
		}
		
	}
	
	class decRsaListener implements OnClickListener{
		public void onClick(View arg0) {
			rsaCipher(INS_DEC);
			
		}
	}
	
	class signRsaListener implements OnClickListener{
		public void onClick(View arg0) {
			rsaCipher(INS_SIGN);
		}
	}
	
	class verifyRsaListener implements OnClickListener{
		public void onClick(View arg0) {
			showText("请将验签数据填在数据区,签名结果填在结果区,再进行验签!");
			rsaCipher(INS_VERIFY);
			
		}
	}
	
	private void showText(String str){
		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
	}
	
}
