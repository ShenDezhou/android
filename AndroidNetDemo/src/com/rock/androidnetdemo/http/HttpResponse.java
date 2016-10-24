package com.rock.androidnetdemo.http;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import android.util.Log;

public abstract class HttpResponse {

	private static final String TAG = HttpResponse.class.getSimpleName();
	
	private ErrorListener errorListener;
	
	private Response.Listener<String> responseListener;
	
	public ErrorListener getErrorListener() {
		errorListener = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
				Log.e(TAG, "error--" + error.getMessage());
				onError(error);
			}
		};
		return errorListener;
	}

	public Response.Listener<String> getResponseListener() {
		responseListener = new Listener<String>() {

			@Override
			public void onResponse(String result) {
				
				Log.e(TAG, "result--" + result);
				onSucceed(result);
			}
		};
		
		return responseListener;
	}

	public abstract void onSucceed(String result);
	
	public abstract void onError(VolleyError error);
	
}
