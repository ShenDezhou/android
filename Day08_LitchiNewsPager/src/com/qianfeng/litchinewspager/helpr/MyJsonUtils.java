package com.qianfeng.litchinewspager.helpr;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qianfeng.litchinewspager.bean.News;
/**
 * 根据网络数据解析并且进行存储
 * @param jsonString
 * @return
 */
public class MyJsonUtils {

	public static List<News> jsonStringToList(String jsonString){
		List<News>list = new ArrayList<News>();
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			if (jsonObject.getString("status").equals("ok")) {
				JSONObject jsonObject_paramz = jsonObject.getJSONObject("paramz");
				JSONArray jsonArray_feeds = jsonObject_paramz.getJSONArray("feeds");
				for (int i = 0; i < jsonArray_feeds.length(); i++) {
					JSONObject jsonObject_feed = jsonArray_feeds.getJSONObject(i);
					JSONObject jsonObject_data = jsonObject_feed.getJSONObject("data");
					News  news = new News();
					news.setCover(jsonObject_data.getString("cover"));
					news.setSubject(jsonObject_data.getString("subject"));
					news.setSummary(jsonObject_data.getString("summary"));
					list.add(news);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
		
	}
}
