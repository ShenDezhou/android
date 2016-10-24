package com.qf.broadcastreceiverdemo1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class BroadCast extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		Log.i("BroadCast", bundle.getInt("iop")+"");
		//截断广播
		abortBroadcast();
		
	}

}
