package com.qf.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MediaPlayerService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		int a=0;
		switch (a) {
		case 0:
			//stop
			break;

		default:
			break;
		}
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
}
