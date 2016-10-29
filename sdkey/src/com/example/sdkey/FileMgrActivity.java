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
	//打开文件 输入文件名
	private EditText openFileNameText;
	private EditText encKeyText;
	private EditText encFileNameText;
	private EditText decKeyText;
	private EditText decFileNameText;
	private EditText fileContentText;
	
	//打开文件 按钮
	private Button selectFileBtn;
	private Button openFileBtn;
	private Button editFileBtn;
	private Button saveFileBtn;
	private Button encyptFileBtn;
	private Button decryptFileBtn;
	
	String inputFileName = "";
	//此处可以按照SD卡目录修改！
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
			//将选择的文件名和path传到"请输入文件名"位置
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
				showText("文件名不能为空");
				return;
			}
			
			if(!inputFileName.startsWith(ROOTPATH)){
				
				if(inputFileName.startsWith("/")){
					showText("不支持此路径下的文件!");
					return;
				}
				
				inputFileName = ROOTPATH + inputFileName;
			}
			
			openedFile = new java.io.File(inputFileName);
			
			if(!openedFile.exists()){
				showText("文件名不存在");
				return;
			}else{
				showText("文件已打开！");
				return;
			}
		}
	}
	
	class editFileBtnListener implements OnClickListener{
		
		public void onClick(View v){
//			inputFileName = openFileNameText.getText().toString();
//			if(inputFileName.equals("")){
//				showText("文件名不能为空");
//				return;
//			}
//
//			if(!inputFileName.startsWith(ROOTPATH)){
//				
//				if(inputFileName.startsWith("/")){
//					showText("不支持此路径下的文件!");
//					return;
//				}
//				
//				inputFileName = ROOTPATH + inputFileName;
//			}
			
			if(inputFileName.equals("")){
				showText("文件还未打开!");
				return;
			}
			
			openedFile = new java.io.File(inputFileName);
			
			if(!openedFile.exists()){
				showText("文件名不存在");
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
				showText("文件名不能为空");
				return;
			}
			
			if(!inputFileName.startsWith(ROOTPATH)){
				
				if(inputFileName.startsWith("/")){
					showText("不支持此路径下的文件!");
					return;
				}
				
				inputFileName = ROOTPATH + inputFileName;
			}
			
			String writestr = fileContentText.getText().toString();
			
	        if(writeString(inputFileName, writestr, 1) != 0){
        		showText("文件写入错误！");
        		return;
        	}
	        
	        showText("文件保存成功");
		}
	}
	
	class encyptFileBtnListener implements OnClickListener{
		public void onClick(View v){
			//获得key数据
			encKeyStr = encKeyText.getText().toString();
			
			if(encKeyStr.equals("")){
				showText("加密密钥不能为空！");
				return;
			}
			
			if(encKeyStr.length() > 32){
				showText("加密密钥长度不能大于16");
				return;
			}
			
			//check key format
			for(int i = 0; i < encKeyStr.length(); i++){
				char c = encKeyStr.charAt(i);
				//if (c > 'F' || c < '0'){
				if(!(((c >= '0') && (c <= '9') ) || 
						((c >= 'a') && (c <= 'f') ) ||
						((c >= 'A') && (c <= 'F')))){
					showText("加密密钥格式不对，只能输入0-F的十六进制数");
					return;
				}
			}
			
			//将key数据转成bytes数组，并填充成16字节
			
			//获得原文
//			inputFileName = openFileNameText.getText().toString();
//			if(inputFileName.equals("")){
//				showText("文件名不能为空");
//				return;
//			}
//			
//			inputFileName = ROOTPATH + inputFileName;
			
			//encSourceText = fileContentText.getText().toString();
			if(inputFileName.equals("")){
				showText("文件还未打开!");
				return;
			}
			encSourceText = readString(inputFileName);
			
			if (selectCipherApp() != 0){
				showText("应用选择失败！");
        		return;
			}
			
			if (doEncyptFile(encKeyStr, encSourceText) != 0){
				showText("文件加密错误！");
        		return;
			}
			
			//将原文转成bytes数组
			//将key和原文拼接成数组，发送APDU给SD卡
			
			//获得返回值，转成String，存入目标文件
			encSavedFile = encFileNameText.getText().toString();
			
			if(encSavedFile.equals("")){
				showText("输出文件名不能为空！");
        		return;
			}
			
			if(!encSavedFile.startsWith(ROOTPATH)){
				
				if(encSavedFile.startsWith("/")){
					showText("不支持此路径下的文件!");
					return;
				}
				
				encSavedFile = ROOTPATH + encSavedFile;
			}
			
			 if(writeString(encSavedFile, outdata, 1) != 0){
	        		showText("文件写入错误！");
	        		return;
	        	}
		        
		        showText("文件加密成功");
		}
	}
	
	class decryptFileBtnListener implements OnClickListener{
		public void onClick(View v){
			//获得key数据
			decKeyStr = decKeyText.getText().toString();
			
			if(decKeyStr.equals("")){
				showText("解密密钥不能为空！");
				return;
			}
			
			if(decKeyStr.length() > 32){
				showText("解密密钥长度不能大于16");
				return;
			}
			
			//check key format
			for(int i = 0; i < decKeyStr.length(); i++){
				char c = decKeyStr.charAt(i);
//				if (c > 'F' || c < '0'){
				if(!(((c >= '0') && (c <= '9') ) || 
						((c >= 'a') && (c <= 'f') ) ||
						((c >= 'A') && (c <= 'F')))){
					showText("解密密钥格式不对，只能输入0-F的十六进制数");
					return;
				}
			}
			
			//将key数据转成bytes数组，并填充成16字节
			
			//获得原文
//			inputFileName = openFileNameText.getText().toString();
//			if(inputFileName.equals("")){
//				showText("文件名不能为空");
//				return;
//			}
//			
//			inputFileName = ROOTPATH + inputFileName;
			
			//decSourceText = fileContentText.getText().toString();
			if(inputFileName.equals("")){
				showText("文件还未打开!");
				return;
			}
			decSourceText = readString(inputFileName);
			
			if (selectCipherApp() != 0){
				showText("应用选择失败！");
        		return;
			}
			
			if (doDecryptFile(decKeyStr, decSourceText) != 0){
				showText("文件解密错误！");
        		return;
			}
			
			//将文件内容转成unicode码再转成string，因为文件内容可能有汉字
			byte[] outBytes = ConversionTools.stringToByteArr(outdata);
			outdata = new String(outBytes);
			
			//将原文转成bytes数组
			//将key和原文拼接成数组，发送APDU给SD卡
			
			//获得返回值，转成String，存入目标文件
			decSavedFile = decFileNameText.getText().toString();
			
			if(decSavedFile.equals("")){
				showText("输出文件名不能为空！");
        		return;
			}
			
			if(!decSavedFile.startsWith(ROOTPATH)){
				
				if(decSavedFile.startsWith("/")){
					showText("不支持此路径下的文件!");
					return;
				}
				
				decSavedFile = ROOTPATH + decSavedFile;
			}
			
			if(writeString(decSavedFile, outdata, 1) != 0){
	        	showText("文件写入错误！");
	        	return;
	        }
		        
		    showText("文件解密密成功");
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
	 * 选择加密应用的AID
	 * 
	 * @return 0: 成功 1: 通讯失败 2: 指令执行失败 3: 其他错误
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
	 * 使用加密应用对文件内容进行加密
	 * 
	 * @return 0: 成功 1: 通讯失败 2: 指令执行失败 3: 其他错误
	 */
	public static int doEncyptFile(String keyContent, String srcContent) {
		
		String encAPDU = "10130702";
		
		
		//补齐key为16长度 
		short keyLen = (short)keyContent.length();
		String keyPadValue = "";
		if(keyLen < 32){
			short keyPadLen = (short)(32 - keyLen);
			
			for(short i = 0; i < keyPadLen; i++){
				keyPadValue += "0";
			}
			
		}
		
		//将文件内容转成unicode码再转成string，因为文件内容可能有汉字
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
	 * 使用解密应用对文件内容进行解密
	 * 
	 * @return 0: 成功 1: 通讯失败 2: 指令执行失败 3: 其他错误
	 */
	public int doDecryptFile(String keyContent, String srcContent) {
		
		String encAPDU = "10130701";
		
		//补齐key为16长度 
		short keyLen = (short)keyContent.length();
		String keyPadValue = "";
		if(keyLen < 32){
			short keyPadLen = (short)(32 - keyLen);
			
			for(short i = 0; i < keyPadLen; i++){
				keyPadValue += "0";
			}
			
		}
		
		if(srcContent.length() % 8 != 0){
			showText("解密文件长度不对");
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
