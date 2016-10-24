package com.net.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;


public class HttpUtil {

	private static final String TAG = HttpUtil.class.getSimpleName();
	
	/**
	 * 
	 * get请求使用HttpClient实现
	 * ① 创建HttpClient对象
	 * ② 创建HttpGet or HttpPost对象
	 * ③ 使用HttpClient.excute() 执行②，返回HttpResponse
	 * ④ 判断HttpResponse的状态是否成功进行处理
	 * ⑤ 获取HttpResponse获取HttpEntity，
	 * ⑥ 在使用过程中，根据自己需求，通过EntityUtil将Entity转变成我们所需要的资源
	 * 
	 * ⑦ HttpClient 文件上传
	 * 
	 */
	
	public static HttpEntity getHttpClient(String url){
		
		// 创建HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
		// 创建HttpGet
		HttpGet httpGet = new HttpGet();
		// 添加访问地址
		httpGet.setURI(URI.create(url));
		// 执行网络请求
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			// SC_OK 是 200 
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				HttpEntity httpEntity = httpResponse.getEntity();
				
				return httpEntity;
				
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(TAG, "HttpResponse-->null");
		return null;
		
	}
	
	public static HttpEntity postHttpClient(String url,Map<String,String> params){
		// 实例化HttpClient
		HttpClient httpClient = new DefaultHttpClient();
		// 实例化一个HttpPost
		HttpPost httpPost = new HttpPost(url);
		
		// 获取所有的参数
		List<NameValuePair> parameters = new ArrayList<NameValuePair>(params.size());
		// 获取集合中得所有key
		Set<String> keys = params.keySet();
		// 遍历key，将每一个对应的key变成一个NameValuePair添加到集合中
		for(String key : keys){
			
			NameValuePair nvp = new BasicNameValuePair(key, params.get(key));
			
			parameters.add(nvp);
		}
 		// 使用Parameters实例化一个HttpEntity
		HttpEntity entity;
		
		try {
			
			entity = new UrlEncodedFormEntity(parameters,"UTF-8");
			// 为HttpPost设置Entity
			httpPost.setEntity(entity);
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			// 实例化HttpResponse，通过HttpClient执行HttpPost获得
			HttpResponse httpResponse = httpClient.execute(httpPost);
			// 判断HttpResponse的状态码
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 通过HttpResponse获取Entity
				HttpEntity httpEntity = httpResponse.getEntity();
				
				return httpEntity;
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(TAG, "HttpResponse-->null");
		return null;
	}
	
	
	
	
	public static InputStream getHttpUrlConnection(String path){
		try {
			URL url = new URL(path);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 允许上传,设置后等于强制POST请求
			//conn.setDoOutput(true);
			// 允许下载
			conn.setDoInput(true);
			
			conn.setRequestMethod("GET");
			
			conn.setConnectTimeout(15 * 1000);
			
			conn.setReadTimeout(15 * 1000);
			
			Log.e(TAG, "responseCode--"+conn.getResponseCode());
			
			if(conn.getResponseCode() == HttpStatus.SC_OK){
				
				Log.e(TAG, "成功获取返回流");
				
				InputStream is = conn.getInputStream();
				
				return is;
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(TAG, "inputstream-- null");
		return null;
	}
	
	public static InputStream postHttpUrlConnection(String path,Map<String, String> params){
		
		try {
			URL url = new URL(path);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoInput(true);
			
			conn.setDoOutput(true);
			
			conn.setRequestMethod("POST");
			
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencode; charset=utf-8");
			
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			// 写入参数		
			Set<String> keys = params.keySet();
			
			String param = "";
			
			if (keys.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for(String key : keys){
					sb.append(key + "=" + params.get(key)+"&");
				}
				param = sb.substring(0, sb.length()-1);
			}
			
			dos.write(param.getBytes());
			
			conn.connect();
			
			Log.e(TAG, "responseCode--" + conn.getResponseCode());
			
			dos.close();
			
			if(conn.getResponseCode() == HttpStatus.SC_OK){
				
				InputStream is = conn.getInputStream();
				Log.e(TAG, "成功获取返回流");
				return is;
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
