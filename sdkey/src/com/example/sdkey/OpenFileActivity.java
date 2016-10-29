package com.example.sdkey;


import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
/**
 * ѡ��˵�ҳ��
 *
 */

public class OpenFileActivity extends ListActivity {
	public final static int RESULT_CODE=1;  
	
    private static final String ROOT_PATH = "/storage/sdcard0/aa/";
    //�洢�ļ�����
    private ArrayList<String> names = null;
    //�洢�ļ�·��
    private ArrayList<String> paths = null;
    private View view;
    private EditText editText;
    private File selectedFile;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openfile);
        //��ʾ�ļ��б�
        showFileDir(ROOT_PATH);
    }
    
    private void showFileDir(String path){
        names = new ArrayList<String>();
        paths = new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
         
        //�����ǰĿ¼���Ǹ�Ŀ¼
        if (!path.startsWith(ROOT_PATH)){
//            names.add("@1");
//            paths.add(ROOT_PATH);
//             
//            names.add("@2");
//            paths.add(file.getParent());
        	displayToast("��֧�ֶԴ�Ŀ¼�µ��ļ����д���!");
        	return;
        }else if(!ROOT_PATH.equals(path)){
          names.add("@1");
          paths.add(ROOT_PATH);
           
//          names.add("@2");
//          paths.add(file.getParent());
        }
        //��������ļ�
        for (File f : files){
            names.add(f.getName() + "          ");
            paths.add(f.getPath());
        }
        this.setListAdapter(new MyAdapter(this, names, paths));
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String path = paths.get(position);
        File file = new File(path);
        // �ļ����ڲ��ɶ�
        if (file.exists() && file.canRead()){
            if (file.isDirectory()){
                //��ʾ��Ŀ¼���ļ�
                showFileDir(path);
            }
            else{
                //�����ļ�
                fileHandle(file);
            }
        }
        //û��Ȩ��
        else{
            Resources res = getResources();
            new AlertDialog.Builder(this).setTitle("Message")
            .setMessage(res.getString(R.string.no_permission))
            .setPositiveButton("OK",new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     
                }
            }).show();
        }
        super.onListItemClick(l, v, position, id);
    }
    
    //���ļ�������ɾ��
    private void fileHandle(final File file){
        OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ���ļ�
            	//openFile(file);
            	String name = file.getName();
            	//�ļ���չ��
                String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
            	//ֻ֧�ִ���.txt���ļ�
                if(!end.equals("txt*")){
                	displayToast("��֧�ֶԴ����ļ����м���!");
                	return;
                }
            	
            }
        };
        selectedFile = file;
        //ѡ���ļ�ʱ��������ɾ�ò���ѡ��Ի���
        new AlertDialog.Builder(OpenFileActivity.this)
        .setTitle("ѡ���ļ�!")
        .setPositiveButton("ȷ��", new selectOkListener()).show();
    }
    
    class selectOkListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			//���ļ������ļ�path����ȥ
			Intent intent=getIntent();  
			intent.putExtra("SELECTEDFILENAME", selectedFile.getPath());  
			setResult(RESULT_CODE, intent);  
			finish();  
		}
    	
    }
    
    //���ļ�
   /* private void openFile(File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
         
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        
        //ֻ֧�ִ���.txt���ļ�
        if(!type.equals("txt/*")){
        	displayToast("��֧�ֶԴ����ļ����м���!");
        	return;
        }
        
        
        startActivity(intent);
    }
    //��ȡ�ļ�mimetype
    private String getMIMEType(File file){
        String type = "";
        String name = file.getName();
        //�ļ���չ��
        String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
        if(end.equals("txt")){
        	type = "txt";
        }
        else if(end.equals("m4a") || end.equals("mp3") || end.equals("wav")){
            type = "audio";
        }
        else if(end.equals("mp4") || end.equals("3gp")) {
            type = "video";
        }
        else if (end.equals("jpg") || end.equals("png") || end.equals("jpeg") || end.equals("bmp") || end.equals("gif")){
            type = "image";
        }
        else {
            //����޷�ֱ�Ӵ򿪣������б����û�ѡ��
            type = "*";
        }
        type += "/*";
        return type;
    }*/
    
    private void displayToast(String message){
        Toast.makeText(OpenFileActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

