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
 * 选择菜单页面
 *
 */

public class OpenFileActivity extends ListActivity {
	public final static int RESULT_CODE=1;  
	
    private static final String ROOT_PATH = "/storage/sdcard0/aa/";
    //存储文件名称
    private ArrayList<String> names = null;
    //存储文件路径
    private ArrayList<String> paths = null;
    private View view;
    private EditText editText;
    private File selectedFile;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openfile);
        //显示文件列表
        showFileDir(ROOT_PATH);
    }
    
    private void showFileDir(String path){
        names = new ArrayList<String>();
        paths = new ArrayList<String>();
        File file = new File(path);
        File[] files = file.listFiles();
         
        //如果当前目录不是根目录
        if (!path.startsWith(ROOT_PATH)){
//            names.add("@1");
//            paths.add(ROOT_PATH);
//             
//            names.add("@2");
//            paths.add(file.getParent());
        	displayToast("不支持对此目录下的文件进行处理!");
        	return;
        }else if(!ROOT_PATH.equals(path)){
          names.add("@1");
          paths.add(ROOT_PATH);
           
//          names.add("@2");
//          paths.add(file.getParent());
        }
        //添加所有文件
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
        // 文件存在并可读
        if (file.exists() && file.canRead()){
            if (file.isDirectory()){
                //显示子目录及文件
                showFileDir(path);
            }
            else{
                //处理文件
                fileHandle(file);
            }
        }
        //没有权限
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
    
    //对文件进行增删改
    private void fileHandle(final File file){
        OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 打开文件
            	//openFile(file);
            	String name = file.getName();
            	//文件扩展名
                String end = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase();
            	//只支持处理.txt的文件
                if(!end.equals("txt*")){
                	displayToast("不支持对此类文件进行加密!");
                	return;
                }
            	
            }
        };
        selectedFile = file;
        //选择文件时，弹出增删该操作选项对话框
        new AlertDialog.Builder(OpenFileActivity.this)
        .setTitle("选择文件!")
        .setPositiveButton("确定", new selectOkListener()).show();
    }
    
    class selectOkListener implements DialogInterface.OnClickListener{

		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			//将文件名和文件path传回去
			Intent intent=getIntent();  
			intent.putExtra("SELECTEDFILENAME", selectedFile.getPath());  
			setResult(RESULT_CODE, intent);  
			finish();  
		}
    	
    }
    
    //打开文件
   /* private void openFile(File file){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
         
        String type = getMIMEType(file);
        intent.setDataAndType(Uri.fromFile(file), type);
        
        //只支持处理.txt的文件
        if(!type.equals("txt/*")){
        	displayToast("不支持对此类文件进行加密!");
        	return;
        }
        
        
        startActivity(intent);
    }
    //获取文件mimetype
    private String getMIMEType(File file){
        String type = "";
        String name = file.getName();
        //文件扩展名
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
            //如果无法直接打开，跳出列表由用户选择
            type = "*";
        }
        type += "/*";
        return type;
    }*/
    
    private void displayToast(String message){
        Toast.makeText(OpenFileActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

