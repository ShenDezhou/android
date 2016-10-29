package com.example.sdkey;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import Sumavision.Library.ApduBaseCommand;
import Sumavision.Library.ConversionTools;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FileMgrActivity extends Activity{
	
	private int id=3;
	private final static int REQUEST_CODE=3;
	//���ļ� �����ļ���
	private EditText openFileNameText;
	private EditText encKeyText;
	private EditText encFileNameText;
	private EditText decKeyText;
	private EditText decFileNameText;
	private EditText fileContentText;
	
	//���ļ� ��ť
	private Button selectFileBtn;
	private Button openFileBtn;
	private Button editFileBtn;
	private Button saveFileBtn;
	private Button encyptFileBtn;
	private Button decryptFileBtn;
	
	String inputFileName = "";
	//�˴����԰���SD��Ŀ¼�޸ģ�
	final static String ROOTPATH = "/storage/sdcard0/aa/";
	File openedFile;
	
	String encKeyStr;
	String decKeyStr;
	String encSavedFile;
	String decSavedFile;
	static String outdata;
	String encSourceText;
	String decSourceText;
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState); 
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.filemgr);
		
		openFileNameText = (EditText) this.findViewById(R.id.fileNameText);
		encKeyText = (EditText) this.findViewById(R.id.inputEncKeyText);
		encFileNameText = (EditText) this.findViewById(R.id.encOutFileText);
		decKeyText = (EditText) this.findViewById(R.id.inputDecKeyText);
		decFileNameText = (EditText) this.findViewById(R.id.decOutFileText);
		fileContentText = (EditText) this.findViewById(R.id.fileContentText);
		
		selectFileBtn = (Button) this.findViewById(R.id.selectFileBtn);
		openFileBtn = (Button) this.findViewById(R.id.openFileBtn);
		editFileBtn = (Button) this.findViewById(R.id.editFileBtn);
		saveFileBtn = (Button) this.findViewById(R.id.saveFileBtn);
		encyptFileBtn = (Button) this.findViewById(R.id.encryptFileBtn);
		decryptFileBtn = (Button) this.findViewById(R.id.decryptFileBtn);
		
		selectFileBtn.setOnClickListener(new selectFileBtnListener());
		openFileBtn.setOnClickListener(new openFileBtnListener());
		editFileBtn.setOnClickListener(new editFileBtnListener());
		saveFileBtn.setOnClickListener(new saveFileBtnListener());
		encyptFileBtn.setOnClickListener(new encyptFileBtnListener());
		decryptFileBtn.setOnClickListener(new decryptFileBtnListener());
		
		
	}
	
	class selectFileBtnListener implements OnClickListener{

		public void onClick(View v) {
			//��ѡ����ļ�����path����"�������ļ���"λ��
			Intent intent=new Intent(FileMgrActivity.this,OpenFileActivity.class);
			Bundle b=new Bundle();
			b.putString("SELECTEDFILENAME", "1.txt");
			intent.putExtra("SELECTEDFILENAME", b);
			startActivityForResult(intent,id);
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE) {
			if (resultCode == OpenFileActivity.RESULT_CODE) {
				Bundle bundle = data.getExtras();
				String str = bundle.getString("SELECTEDFILENAME");
				openFileNameText.setText(str);
				
			}
		}
	}

	
	class openFileBtnListener implements OnClickListener{

		public void onClick(View v) {
	
			inputFileName = openFileNameText.getText().toString();
			if(inputFileName.equals("")){
				showText("�ļ�������Ϊ��");
				return;
			}
			
			if(!inputFileName.startsWith(ROOTPATH)){
				
				if(inputFileName.startsWith("/")){
					showText("��֧�ִ�·���µ��ļ�!");
					return;
				}
				
				inputFileName = ROOTPATH + inputFileName;
			}
			
			openedFile = new java.io.File(inputFileName);
			
			if(!openedFile.exists()){
				showText("�ļ���������");
				return;
			}else{
				showText("�ļ��Ѵ򿪣�");
				return;
			}
		}
	}
	
	class editFileBtnListener implements OnClickListener{
		
		public void onClick(View v){
//			inputFileName = openFileNameText.getText().toString();
//			if(inputFileName.equals("")){
//				showText("�ļ�������Ϊ��");
//				return;
//			}
//
//			if(!inputFileName.startsWith(ROOTPATH)){
//				
//				if(inputFileName.startsWith("/")){
//					showText("��֧�ִ�·���µ��ļ�!");
//					return;
//				}
//				
//				inputFileName = ROOTPATH + inputFileName;
//			}
			
			if(inputFileName.equals("")){
				showText("�ļ���δ��!");
				return;
			}
			
			openedFile = new java.io.File(inputFileName);
			
			if(!openedFile.exists()){
				showText("�ļ���������");
				return;
			}else{
				
				String fileContent = readString(inputFileName);
				fileContentText.setText(fileContent);
				fileContentText.requestFocus();
			
			}
		}
	}
	
	class saveFileBtnListener implements OnClickListener{
		public void onClick(View v){
			
			inputFileName = openFileNameText.getText().toString();
			if(inputFileName.equals("")){
				showText("�ļ�������Ϊ��");
				return;
			}
			
			if(!inputFileName.startsWith(ROOTPATH)){
				
				if(inputFileName.startsWith("/")){
					showText("��֧�ִ�·���µ��ļ�!");
					return;
				}
				
				inputFileName = ROOTPATH + inputFileName;
			}
			
			String writestr = fileContentText.getText().toString();
			
	        if(writeString(inputFileName, writestr, 1) != 0){
        		showText("�ļ�д�����");
        		return;
        	}
	        
	        showText("�ļ�����ɹ�");
		}
	}
	
	class encyptFileBtnListener implements OnClickListener{
		public void onClick(View v){
			//���key����
			encKeyStr = encKeyText.getText().toString();
			
			if(encKeyStr.equals("")){
				showText("������Կ����Ϊ�գ�");
				return;
			}
			
			if(encKeyStr.length() > 32){
				showText("������Կ���Ȳ��ܴ���16");
				return;
			}
			
			//check key format
			for(int i = 0; i < encKeyStr.length(); i++){
				char c = encKeyStr.charAt(i);
				//if (c > 'F' || c < '0'){
				if(!(((c >= '0') && (c <= '9') ) || 
						((c >= 'a') && (c <= 'f') ) ||
						((c >= 'A') && (c <= 'F')))){
					showText("������Կ��ʽ���ԣ�ֻ������0-F��ʮ��������");
					return;
				}
			}
			
			//��key����ת��bytes���飬������16�ֽ�
			
			//���ԭ��
//			inputFileName = openFileNameText.getText().toString();
//			if(inputFileName.equals("")){
//				showText("�ļ�������Ϊ��");
//				return;
//			}
//			
//			inputFileName = ROOTPATH + inputFileName;
			
			//encSourceText = fileContentText.getText().toString();
			if(inputFileName.equals("")){
				showText("�ļ���δ��!");
				return;
			}
			encSourceText = readString(inputFileName);
			
			if (selectCipherApp() != 0){
				showText("Ӧ��ѡ��ʧ�ܣ�");
        		return;
			}
			
			if (doEncyptFile(encKeyStr, encSourceText) != 0){
				showText("�ļ����ܴ���");
        		return;
			}
			
			//��ԭ��ת��bytes����
			//��key��ԭ��ƴ�ӳ����飬����APDU��SD��
			
			//��÷���ֵ��ת��String������Ŀ���ļ�
			encSavedFile = encFileNameText.getText().toString();
			
			if(encSavedFile.equals("")){
				showText("����ļ�������Ϊ�գ�");
        		return;
			}
			
			if(!encSavedFile.startsWith(ROOTPATH)){
				
				if(encSavedFile.startsWith("/")){
					showText("��֧�ִ�·���µ��ļ�!");
					return;
				}
				
				encSavedFile = ROOTPATH + encSavedFile;
			}
			
			 if(writeString(encSavedFile, outdata, 1) != 0){
	        		showText("�ļ�д�����");
	        		return;
	        	}
		        
		        showText("�ļ����ܳɹ�");
		}
	}
	
	class decryptFileBtnListener implements OnClickListener{
		public void onClick(View v){
			//���key����
			decKeyStr = decKeyText.getText().toString();
			
			if(decKeyStr.equals("")){
				showText("������Կ����Ϊ�գ�");
				return;
			}
			
			if(decKeyStr.length() > 32){
				showText("������Կ���Ȳ��ܴ���16");
				return;
			}
			
			//check key format
			for(int i = 0; i < decKeyStr.length(); i++){
				char c = decKeyStr.charAt(i);
//				if (c > 'F' || c < '0'){
				if(!(((c >= '0') && (c <= '9') ) || 
						((c >= 'a') && (c <= 'f') ) ||
						((c >= 'A') && (c <= 'F')))){
					showText("������Կ��ʽ���ԣ�ֻ������0-F��ʮ��������");
					return;
				}
			}
			
			//��key����ת��bytes���飬������16�ֽ�
			
			//���ԭ��
//			inputFileName = openFileNameText.getText().toString();
//			if(inputFileName.equals("")){
//				showText("�ļ�������Ϊ��");
//				return;
//			}
//			
//			inputFileName = ROOTPATH + inputFileName;
			
			//decSourceText = fileContentText.getText().toString();
			if(inputFileName.equals("")){
				showText("�ļ���δ��!");
				return;
			}
			decSourceText = readString(inputFileName);
			
			if (selectCipherApp() != 0){
				showText("Ӧ��ѡ��ʧ�ܣ�");
        		return;
			}
			
			if (doDecryptFile(decKeyStr, decSourceText) != 0){
				showText("�ļ����ܴ���");
        		return;
			}
			
			//���ļ�����ת��unicode����ת��string����Ϊ�ļ����ݿ����к���
			byte[] outBytes = ConversionTools.stringToByteArr(outdata);
			outdata = new String(outBytes);
			
			//��ԭ��ת��bytes����
			//��key��ԭ��ƴ�ӳ����飬����APDU��SD��
			
			//��÷���ֵ��ת��String������Ŀ���ļ�
			decSavedFile = decFileNameText.getText().toString();
			
			if(decSavedFile.equals("")){
				showText("����ļ�������Ϊ�գ�");
        		return;
			}
			
			if(!decSavedFile.startsWith(ROOTPATH)){
				
				if(decSavedFile.startsWith("/")){
					showText("��֧�ִ�·���µ��ļ�!");
					return;
				}
				
				decSavedFile = ROOTPATH + decSavedFile;
			}
			
			if(writeString(decSavedFile, outdata, 1) != 0){
	        	showText("�ļ�д�����");
	        	return;
	        }
		        
		    showText("�ļ������ܳɹ�");
		}
	}
	
	private void showText(String str){
		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}
	
	public static int writeString(String file, String str, int mode){
		//mode:0:create
		//1:overwrite
		java.io.File outputFile=new java.io.File(file);
//		if(outputFile.exists() && mode==0){
//			return -1;
//		}
	    java.io.PrintWriter output;
		try {
			output = new java.io.PrintWriter(outputFile);
			//out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("d:/text.txt"), "utf-8"))
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return -2;
		}
		output.print(str);
		output.close();
		return 0;
	}
	
	public static String readString(String file){
		String inputStr="";
		java.io.File inputFile=new java.io.File(file);
		java.util.Scanner input;
        try {
			input=new java.util.Scanner(inputFile);
			while(input.hasNext()){
				inputStr += input.next();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		input.close();
		
		return inputStr;
	}
	
	/**
	 * ѡ�����Ӧ�õ�AID
	 * 
	 * @return 0: �ɹ� 1: ͨѶʧ�� 2: ָ��ִ��ʧ�� 3: ��������
	 */
	public static int selectCipherApp() {
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu("00A40400080102030405060708");

		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");
		String outdata = resultMap.get("outdata");

		if (retString.equals("0") == false) {
			return 1;
		}
		if (sw.equals("9000") == false) {
			return 2;
		}

		return 0;
	}
	
	/**
	 * ʹ�ü���Ӧ�ö��ļ����ݽ��м���
	 * 
	 * @return 0: �ɹ� 1: ͨѶʧ�� 2: ָ��ִ��ʧ�� 3: ��������
	 */
	public static int doEncyptFile(String keyContent, String srcContent) {
		
		String encAPDU = "10130702";
		
		
		//����keyΪ16���� 
		short keyLen = (short)keyContent.length();
		String keyPadValue = "";
		if(keyLen < 32){
			short keyPadLen = (short)(32 - keyLen);
			
			for(short i = 0; i < keyPadLen; i++){
				keyPadValue += "0";
			}
			
		}
		
		//���ļ�����ת��unicode����ת��string����Ϊ�ļ����ݿ����к���
		byte[] inSrc = srcContent.getBytes();
		srcContent = ConversionTools.ByteArrayToString(inSrc, inSrc.length);
		
		String content = keyContent + keyPadValue + srcContent;
		
		encAPDU += Integer.toHexString(content.length() / 2) + content;
		
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu(encAPDU);

		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");
		outdata = resultMap.get("outdata");

		if (retString.equals("0") == false) {
			return 1;
		}
		if (sw.equals("9000") == false) {
			return 2;
		}
		
		return 0;
	}
	
	/**
	 * ʹ�ý���Ӧ�ö��ļ����ݽ��н���
	 * 
	 * @return 0: �ɹ� 1: ͨѶʧ�� 2: ָ��ִ��ʧ�� 3: ��������
	 */
	public int doDecryptFile(String keyContent, String srcContent) {
		
		String encAPDU = "10130701";
		
		//����keyΪ16���� 
		short keyLen = (short)keyContent.length();
		String keyPadValue = "";
		if(keyLen < 32){
			short keyPadLen = (short)(32 - keyLen);
			
			for(short i = 0; i < keyPadLen; i++){
				keyPadValue += "0";
			}
			
		}
		
		if(srcContent.length() % 8 != 0){
			showText("�����ļ����Ȳ���");
			return -4;
		}
		
		String content = keyContent + keyPadValue + srcContent;
		
		encAPDU += Integer.toHexString(content.length() / 2) + content;
		
		Map<String, String> resultMap = ApduBaseCommand
				.exchangeApdu(encAPDU);

		String retString = resultMap.get("ret");
		String sw = resultMap.get("sw");
		outdata = resultMap.get("outdata");

		if (retString.equals("0") == false) {
			return 1;
		}
		if (sw.equals("9000") == false) {
			return 2;
		}
		
		return 0;
	}
	
}
