package com.userinterface.entry;

import com.zxing.activity.CaptureActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.Camera;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.local.file.FileManager;
import com.net.http.HttpUtil;
import com.net.http.StreamUtil;
import com.net.model.ItemInterface;
import com.net.model.ModelGuest;
import com.net.model.ModelMeeting;
import com.net.model.Po;
import com.net.model.Po1;
import com.tsingdata.zxingdemo.R;
import com.recycle.WebViewActivity;
import com.net.http.HttpUtils;
import com.userinterface.adapter.MyBaseAdapter;
import com.userinterface.entry.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final String EVENT_URL = "http://app.tsingdata.com/mgr/rest/checkin/events";
	private static final String SERIALIZEFILE = "checkinguest";
	private static final String MEETINGIDFILE = "meetingid";

	// private static final String EVENT_URL =
	// "http://192.168.199.216:8888/mgr/rest/checkin/events";
	private static final String TAG = MainActivity.class.getSimpleName();

	private ListView meetingListView = null;
	private MyBaseAdapter myBaseAdapter = null;
	private List<ItemInterface> meetingListAgreement = null;
	private String meetingEventID = null;

	private List<ItemInterface> guestListAgreement = null;

	private List<ItemInterface> scannedListAgreement = null;

	private EditText scanedNameTextView = null;

	private Random random = null;

	// 1.启动Activity：系统会先调用onCreate方法，然后调用onStart方法，最后调用onResume，Activity进入运行状态。
	//
	// 2.当前Activity被其他Activity覆盖其上或被锁屏：系统会调用onPause方法，暂停当前Activity的执行。
	//
	// 3.当前Activity由被覆盖状态回到前台或解锁屏：系统会调用onResume方法，再次进入运行状态。
	//
	// 4.当前Activity转到新的Activity界面或按Home键回到主屏，自身退居后台：系统会先调用onPause方法，然后调用onStop方法，进入停滞状态。
	//
	// 5.用户后退回到此Activity：系统会先调用onRestart方法，然后调用onStart方法，最后调用onResume方法，再次进入运行状态。
	//
	// 6.当前Activity处于被覆盖状态或者后台不可见状态，即第2步和第4步，系统内存不足，杀死当前Activity，而后用户退回当前Activity：再次调用onCreate方法、onStart方法、onResume方法，进入运行状态。
	//
	// 7.用户退出当前Activity：系统先调用onPause方法，然后调用onStop方法，最后调用onDestory方法，结束当前Activity。

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_settings:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		setUpView();
		loadJson();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		cleanUpView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			boolean find = false;
			for (ItemInterface item : guestListAgreement) {
				if (item.getItemType() == 2) {
					Po1 po = (Po1) item;
					if (scanResult.equals(po.getTitle2())) {
						po.setTitle3(DateFormat.format("yyyy-MM-dd kk:mm:ss",
								System.currentTimeMillis()).toString());
						Toast.makeText(MainActivity.this, "您已成功签到",
								Toast.LENGTH_SHORT).show();
						find = true;
						scannedListAgreement.add(po);
						
						//to JSONArray
						JSONArray jsonArray = new JSONArray();
						try{
						// 遍历我们的JSONArray
						for (int i = 0; i < scannedListAgreement
								.size(); i++) {
							Po1 _po = (Po1) scannedListAgreement
									.get(i);
							if (_po.getTitle3() != null) {
								JSONObject obj = new JSONObject();
								obj.put("id", _po.getTitle1());
								obj.put("datetime",
										_po.getTitle3());
								jsonArray.put(obj);
							}
						}
						}catch(JSONException jsex)
						{
							jsex.printStackTrace();
							Toast.makeText(MainActivity.this,jsex.getMessage(),
									Toast.LENGTH_SHORT).show();
								
						}
						FileManager.saveObjects(this, jsonArray, SERIALIZEFILE);
						Log.e(TAG,"saved json:" + jsonArray.toString());
						break;
					}

				}
			}
			if (!find)
				Toast.makeText(MainActivity.this, "您可能未报名", Toast.LENGTH_SHORT)
						.show();
			scanedNameTextView.setText(scanResult);

		}
	}

	protected void setOnItemSelectedListener() {

	}

	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.getMeetingList:

			new AsyncTask<String, Void, String>() {

				@Override
				protected String doInBackground(String... params) {
					HttpEntity entity = HttpUtil.getHttpClient(params[0]);
					try {
						if (entity == null) {
							return null;
						}
						String result = EntityUtils.toString(entity);
						return result;
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Log.e(TAG, "doInbackground--null");
					return null;
				}

				@Override
				protected void onPostExecute(String result) {
					Log.e(TAG, "result--" + result);
					if (result != null) {
						try {

							JSONArray jsonArray = new JSONArray(result);

							List<Po1> data = new ArrayList<Po1>();
							// 遍历我们的JSONArray
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject obj = jsonArray.getJSONObject(i);
								Po1 model = new Po1();
								model.setTitle1("" + obj.getInt("id"));
								model.setTitle2(obj.getString("title"));
								data.add(model);
							}

							// if (data.size() != meetingListAgreement.size()
							// || meetingListAgreement.size() == 0
							// || meetingListAgreement.size() > 0
							// && data.size() > 0
							// && ((Po1) meetingListAgreement.get(0))
							// .getTitle1() != ((Po1) data.get(0))
							// .getTitle1()) {
							meetingListAgreement.clear();
							meetingListAgreement.addAll(data);
							// myBaseAdapter.notifyDataSetChanged();
							// }
							myBaseAdapter.setList(meetingListAgreement);
							Toast.makeText(MainActivity.this, "会议列表下载成功",
									Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							Toast.makeText(MainActivity.this, e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(MainActivity.this, "下载失败",
								Toast.LENGTH_SHORT).show();

					}
				};
			}.execute(EVENT_URL);

			break;

		case R.id.scanGuestCode:
			if (meetingEventID == null) {
				Toast.makeText(MainActivity.this, "请先选择会议，下载嘉宾名单。",
						Toast.LENGTH_SHORT).show();
				break;
			}

			Intent openCameraIntent = new Intent(MainActivity.this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0);
			break;

		case R.id.uploadGuestList:
			if (meetingEventID == null || scannedListAgreement.size() == 0) {
				Toast.makeText(MainActivity.this, "本机无签到数据，无需上传。",
						Toast.LENGTH_SHORT).show();
				break;
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this).setTitle("签到嘉宾名单上传?")
					.setMessage("你确定上传吗？上传前请确认网络开启。")
					.setNegativeButton("不", null)
					.setPositiveButton("是的", new OnClickListener() {
						@Override
						public void onClick(DialogInterface diag, int value) {
							new AsyncTask<Void, Void, String>() {

								@Override
								protected String doInBackground(
										Void... paramVarArgs) {
									try {
										String key = "qrlist";
										JSONArray jsonArray = new JSONArray();

										// 遍历我们的JSONArray
										for (int i = 0; i < scannedListAgreement
												.size(); i++) {
											Po1 po = (Po1) scannedListAgreement
													.get(i);
											if (po.getTitle3() != null) {
												JSONObject obj = new JSONObject();
												obj.put("id", po.getTitle1());
												obj.put("datetime",
														po.getTitle3());
												jsonArray.put(obj);
											}
										}
										// Map<String, String> map = new
										// HashMap<String,
										// String>();
										// map.put("cateid", "0");
										// map.put("p", "1");
										// InputStream is =
										// HttpUtil.postHttpUrlConnection("http://apiv2.vmovier.com/api/post/getPostInCate",map);
										// String result =
										// StreamUtil.parseString(is);
										//
										//
										String value = jsonArray.toString();
										java.util.HashMap<String, String> param = new java.util.HashMap<String, String>();
										param.put(key, value);
										HttpEntity entity = HttpUtil
												.postHttpClient(EVENT_URL + "/"
														+ meetingEventID
														+ "/qrlist/checkined",
														param);
										if (entity == null)
											return null;
										String result = EntityUtils
												.toString(entity);
										return result;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return null;
								}

								@Override
								protected void onPostExecute(String result) {
									Log.e(TAG, "result--" + result);
									if (result != null) {
										try {
											JSONObject obj = new JSONObject(
													result);
											if (obj.getString("message")
													.equals("上传成功")) {
												Toast.makeText(
														MainActivity.this,
														obj.getString("message"),
														Toast.LENGTH_SHORT)
														.show();
												scannedListAgreement.clear();
												
												//clean saved json
												cleanJson();
											}

										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											Toast.makeText(MainActivity.this,
													e.getMessage(),
													Toast.LENGTH_SHORT).show();
										}
									} else {
										Toast.makeText(MainActivity.this,
												"上传失败", Toast.LENGTH_SHORT)
												.show();

									}
								};

							}.execute();
						}
					});
			builder.create().show();
			break;
		}

	}

	private void initView() {

		meetingListView = (ListView) findViewById(R.id.meetinglistView);
		
		myBaseAdapter = new MyBaseAdapter(this);
		meetingListView.setAdapter(myBaseAdapter);
		meetingListAgreement = new ArrayList<ItemInterface>();
		myBaseAdapter.setList(meetingListAgreement);

		guestListAgreement = new ArrayList<ItemInterface>();
		scannedListAgreement = new ArrayList<ItemInterface>();
		scanedNameTextView = (EditText) findViewById(R.id.scanResult);

		meetingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MainActivity.this)
						.setTitle("嘉宾名单下载?")
						.setMessage(
								"你确定下载"
										+ ((Po1) meetingListAgreement
												.get(position)).getTitle2()
										+ "吗？").setNegativeButton("不", null)
						.setPositiveButton("是的", new OnClickListener() {
							@Override
							public void onClick(DialogInterface diag, int value) {

								
								new AsyncTask<Void, Void, String>() {

									@Override
									protected String doInBackground(
											Void... paramVarArgs) {
										try {
											
											String value = ((Po1) meetingListAgreement
													.get(position)).getTitle1();
											meetingEventID = value;
											
											saveMeetingID();
											HttpEntity entity = HttpUtil
													.getHttpClient(EVENT_URL
															+ "/" + value
															+ "/qrlist");
											if(entity==null)
												return null;
											String result = EntityUtils
													.toString(entity);
											return result;
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										return null;
									}

									@Override
									protected void onPostExecute(String result) {
										Log.e(TAG, "result--" + result);
										if (result != null) {
											try {
												JSONArray jsonArray = new JSONArray(
														result);

												List<Po1> data = new ArrayList<Po1>();
												// 遍历我们的JSONArray
												for (int i = 0; i < jsonArray
														.length(); i++) {
													JSONObject obj = jsonArray
															.getJSONObject(i);
													Po1 model = new Po1();
													model.setTitle1(obj
															.getString("id"));
													model.setTitle2(obj
															.getString("name"));
													data.add(model);
												}

												guestListAgreement.clear();
												guestListAgreement.addAll(data);
												Toast.makeText(
														MainActivity.this,
														"下载完成",
														Toast.LENGTH_SHORT)
														.show();
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
												Toast.makeText(
														MainActivity.this,
														e.getMessage(),
														Toast.LENGTH_SHORT)
														.show();
											}
										
										}else
										{
											Toast.makeText(
													MainActivity.this,
													"下载失败",
													Toast.LENGTH_SHORT)
													.show();
										}
									};

								}.execute();

							}
						}).setOnItemSelectedListener(null);
				builder.create().show();

			}
		});
		// 如果长按点击事件onItemClick 和setOnItemLongClickListener都会响应 返回true 只让长按处理
		meetingListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						Toast.makeText(MainActivity.this, "" + position,
								Toast.LENGTH_SHORT).show();
						return true;
					}
				});
	}

	private void setUpView() {
		//getData();
	}

	private void getData() {
		random = new Random();
		for (int i = 0; i < 200; i++) {
			int n = 1;
			switch (n) {
			case 0:
				Po po = new Po();
				po.setTitle("这是第" + i + "条数据");
				meetingListAgreement.add(po);
				break;
			case 1:
				Po1 po1 = new Po1();
				po1.setTitle1("这是第" + i + "条数据");
				po1.setTitle2("这是第" + i + "条");
				meetingListAgreement.add(po1);
				break;
			default:
				break;
			}
		}
		// onClick(findViewById(R.id.getMeetingList));
	}
	
	private void loadJson(){
		//meeting id
		JSONObject jsonObj = FileManager.readObject(this, MEETINGIDFILE);
		if(jsonObj==null)
			return;
		try {
			meetingEventID=jsonObj.getString("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e(TAG,"recovered json:" + jsonObj.toString());
		//to JSONArray
		JSONArray jsonArray = FileManager.readObjects(this, SERIALIZEFILE);
		if(jsonArray==null)
			return;

		try{
		// 遍历我们的JSONArray
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				Po1 model = new Po1();
				model.setTitle1("" + obj.getInt("id"));
				model.setTitle3(obj.getString("datetime"));
				scannedListAgreement.add(model);
			}
		}catch(JSONException jsex)
		{
			jsex.printStackTrace();
			Toast.makeText(MainActivity.this,jsex.getMessage(),
					Toast.LENGTH_SHORT).show();
				
		}
		Log.e(TAG,"recovered json:" + jsonArray.toString());
		
		
		
	}

	private void saveMeetingID()
	{
		JSONObject obj = new JSONObject();
		try {
			obj.put("id",meetingEventID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileManager.saveObject(this, obj, MEETINGIDFILE);
		
	}
	private void cleanUpView() {
		Log.e(TAG, "clean up");
		onClick(findViewById(R.id.uploadGuestList));
		
	}

	private void cleanJson(){
		FileManager.saveObjects(this, new JSONArray(),SERIALIZEFILE);
		FileManager.saveObject(this, new JSONObject(),MEETINGIDFILE);
	}

}
