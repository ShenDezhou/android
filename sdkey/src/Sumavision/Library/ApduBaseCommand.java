package Sumavision.Library;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

public class ApduBaseCommand
{

	public static int initConnection(Context context)
	{
		int ret = 0;
		byte[] responseData = new byte[512];
		int responseLen[] = new int[1];

		ret = SmartSDLib.getInstance().SDConnect(context, responseLen, responseData);
		if (ret != 0)
		{
			return 1;
		}
		ret = SmartSDLib.getInstance().Reset(responseLen, responseData);
		if (ret != 0)
		{
			return 2;
		}

		return 0;
	}

	public static Map<String, String> exchangeApdu(String apdu)
	{
		Map<String, String> resultMap = new HashMap<String, String>();
		int ret = 0;
		byte[] indata;
		byte[] outdata = new byte[300];
		int[] outdataLen = new int[1];

		try
		{
			indata = ConversionTools.stringToByteArr(apdu);

			Log.d("SumaLog", "IN : " + apdu);
			ret = SmartSDLib.getInstance().SendAPDUCommand(indata.length, indata, outdataLen, outdata, 30000);
			if (ret != 0)
			{
				resultMap.put("ret", "1");
				resultMap.put("sw", "");
				resultMap.put("outdata", "");
				return resultMap;
			}
			String outdataString = ConversionTools.ByteArrayToString(outdata, outdataLen[0]);
			Log.d("SumaLog", "OUT: " + outdataString);
			if (outdataString == null)
			{
				resultMap.put("ret", "1");
				resultMap.put("sw", "");
				resultMap.put("outdata", "");
				return resultMap;
			}
			if (outdataString.length() < 4)
			{
				resultMap.put("ret", "1");
				resultMap.put("sw", "");
				resultMap.put("outdata", "");
				return resultMap;
			}
			if (outdataString.length() == 4)
			{
				resultMap.put("ret", "0");
				resultMap.put("sw", outdataString);
				resultMap.put("outdata", "");
				return resultMap;
			}
			if (outdataString.length() > 4)
			{
				String sw = outdataString.substring(outdataString.length() - 4);
				String odString = outdataString.substring(0, outdataString.length() - 4);
				resultMap.put("ret", "0");
				resultMap.put("sw", sw);
				resultMap.put("outdata", odString);
				return resultMap;
			}

			resultMap.put("ret", "1");
			resultMap.put("sw", "");
			resultMap.put("outdata", "");
			return resultMap;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			resultMap.put("ret", "1");
			resultMap.put("sw", "");
			resultMap.put("outdata", "");
			return resultMap;
		}
	}

}
