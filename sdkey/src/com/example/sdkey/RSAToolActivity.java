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
 * ѡ��˵�ҳ��
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
			//����APDUָ��,ѡ��Ӧ��,�������������ָ��
			//ѡ��Ӧ��
			if(FileMgrActivity.selectCipherApp() != 0){
				showText("Ӧ��ѡ��ʧ��!");
				return;
			}
			
			Map<String, String> resultMap = ApduBaseCommand
					.exchangeApdu("1031000000");
			//��÷�����
			String retString = resultMap.get("ret");
			String sw = resultMap.get("sw");
			String outdata = resultMap.get("outdata");
			
			if (retString.equals("0") == false) {
				showText("RSA��Կ������ʧ��!");
				return;
			}
			if (sw.equals("9000") == false) {
				showText("RSA��Կ������ʧ��!");
				return;
			}
			
			//��ʾ��EditText��
			rsaPubKey.setText("010001");
//			rsaPriKey.setText(outdata.substring(0, 191));
//			rsaModule.setText(outdata.substring(192, 383));
			rsaPriKey.setText(outdata.substring(0, 255));
			
			resultMap = ApduBaseCommand
					.exchangeApdu("103B000000");
			//��÷�����
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
		
		
		//�����Կ��ģΪ��,�״�
		if( rsaPubKey.getText().equals("") || rsaModule.getText().equals("") ){
			showText("δ����RSA��Կ��!");
			return;
		}
		
		//����������ݸ�ʽ����,���Ȳ���
		//�õ�����
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
			showText("�������ݲ���Ϊ��");
			return;
		}
		
		if((dataLen % 2) == 1){
			showText("���ݳ��Ȳ��ԣ�ֻ�ܰ��ֽ�����,����Ϊ����");
			return;
		}
		
		//�����Կ��ʽ,0-9,a-f,A-F
		for(short i = 0; i < dataLen; i++){
			char c = rsaData.charAt(i);
			if(!(((c >= '0') && (c <= '9') ) || 
					((c >= 'a') && (c <= 'f') ) ||
					((c >= 'A') && (c <= 'F')))){
				showText("Դ���ݸ�ʽ���ԣ�ֻ������0-F��ʮ��������");
				return ;
			}
		}
		
		//ѡ��Ӧ��
		if(FileMgrActivity.selectCipherApp() != 0){
			showText("Ӧ��ѡ��ʧ��!");
			return ;
		}
		
		APDU = APDU + INS + "0000" + LC +  rsaData;
		
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu(APDU);
		//��÷�����
		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");
		String outdata = resultMap.get("outdata");
		
		if (retString.equals("0") == false) {
			showText("RSA����ʧ��!");
			return ;
		}
		if (sw.equals("9000") == false) {
			if(INS.equals(INS_VERIFY)){
				showText("RSA��ǩ�������!");
				return;
			}else{
				showText("RSA����ʧ��!");
				return;
			}
			
		}
		if(INS.equals(INS_VERIFY)){
			showText("RSA��ǩ�����ȷ!");
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
			showText("�뽫��ǩ��������������,ǩ��������ڽ����,�ٽ�����ǩ!");
			rsaCipher(INS_VERIFY);
			
		}
	}
	
	private void showText(String str){
		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
	}
	
}
