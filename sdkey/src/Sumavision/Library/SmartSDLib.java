package Sumavision.Library;

import java.io.File;

import android.content.Context;
import android.os.Build;


public class SmartSDLib
{

	private static SmartSDLib SDAndICLibInstance = null;

	public static SmartSDLib getInstance()
	{
		if (SDAndICLibInstance == null)
		{
			try
			{
				System.loadLibrary("SumaSmartSDBaseCommuLib");
			}
			catch (Exception e)
			{
				return null;
			}
			SDAndICLibInstance = new SmartSDLib();
		}

		return SDAndICLibInstance;
	}

	
	
	
	
	
	public  int SDConnect(Context context, int responseLen[], byte[] responseData)
	{
		int result;
		String cardPath;
		//android4.4.2
		if(Build.VERSION.SDK_INT >= 19)  //Android Version 4.4.2 or latter
		{
		    File file[] = null;
		    file = context.getExternalFilesDirs("");
		    if(file.length>1)
		    {
		        if(file[1] != null )
		        	cardPath = file[1].getAbsolutePath();
		        else
		        	cardPath = ""; //Not insert SD Card
		    }
		    else
		    {
		    	cardPath = file[0].getAbsolutePath();
		    }
			result = Initialization(cardPath, responseLen, responseData);
		}
		else
		{
			result = Connect(responseLen, responseData);
		}
		
		return result;
	}
	//底层实现的是SCIF_CONNECT命令
	private native int Connect(int responseLen[],byte[] responseData);

	//底层实现的是SCIF_CONNECT命令
	
	private native int Initialization(String filePath,int responseLen[],byte[] responseData);

	// 底层实现的是SCIF_ATR命令
	public native int Reset(int responseLen[], byte[] ATR_Buffer);

	// 底层实现的是SCIF_INFO命令
	public native int INFO(int responseLen[], byte[] INFO_Buffer);

	// 底层实现的是SCIF_APDU命令
	public native int SendAPDUCommand(int inAPDU_len, byte[] InAPDU_Buffer, int responseLen[], byte[] responseData);

	// 底层实现的是SCIF_APDU命令
	public native int SendAPDUCommand(int inAPDU_len, byte[] InAPDU_Buffer, int responseLen[], byte[] responseData, int timeout);

	// 底层实现的是SCIF_PPS 命令
	public native int SendPPSCommand(int Rate, int responseLen[], byte[] responseData);

	// 底层实现的是SCIF_DISCONNECT命令
	public native int Endtransmission(int responseLen[], byte[] responseData);
}
