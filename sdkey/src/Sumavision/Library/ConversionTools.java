package Sumavision.Library;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Locale;

/**
 * ConversionTools 数据格式转换
 * 
 * 
 */
public final class ConversionTools
{
	/**
	 * byte 2 String
	 * 
	 * @param indata
	 * @param indataLen
	 * @return
	 */
	public static String ByteArrayToString(byte[] indata, int indataLen)
	{
		try
		{
			int g_RespLen = indataLen;
			byte[] g_Response = indata;

			int m = 0;
			String infoString = "";

			while (m < g_RespLen)
			{
				if ((g_Response[m] & 0xF0) == 0x00)
				{
					infoString += '0' + Integer.toHexString((short) (0x00FF & g_Response[m]));
				}
				else
				{
					infoString += Integer.toHexString((short) (0x00FF & g_Response[m]));
				}
				m++;
			}

			return infoString.toUpperCase(Locale.US);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * @param iStr
	 * @return
	 */
	public static byte[] stringToByteArr(String iStr)
	{
		iStr = iStr.replaceAll(" ", "");
		iStr = iStr.replaceAll(System.getProperty("line.separator"), "");
		iStr = iStr.replaceAll("\n", "");
		iStr = iStr.replaceAll("\t", "");
		if (iStr.length() == 0)
		{
			return null;
		}
		if (iStr.length() % 2 != 0)
		{
			iStr += "F";
		}
		else
		{

		}
		byte[] outArr = new byte[iStr.length() / 2];
		byte b = 0;
		String hex = "";

		for (int i = 0; i < iStr.length(); i += 2)
		{
			hex = iStr.substring(i, i + 2);
			try
			{
				if (hex.equalsIgnoreCase(null) || hex.equalsIgnoreCase(""))
				{
					break;
				}
				b = (byte) ((int) Integer.parseInt(hex, 16) & 0xFF);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				break;
			}

			outArr[i / 2] = b;
		}
		return outArr;
	}

	public static String byte2HexString(byte inbyte)
	{
		byte tmp[] = new byte[1];
		tmp[0] = inbyte;
		return ConversionTools.ByteArrayToString(tmp, 1);
	}
	
	
	
	/**
	 * GBK 2 String
	 * 
	 * @param indata
	 * @return
	 */
	public String GBK2String(String indata)
	{
		if (indata == null)
		{
			return null;
		}

		String finalDataString = null;

		byte[] indataByte = ConversionTools.stringToByteArr(indata);
		try
		{
			finalDataString = new String(indataByte, "GBK");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}

		return finalDataString;
	}

	/**
	 * String 2 GBK
	 * 
	 * @param indata
	 * @return
	 */
	public static String string2GBK(String indata)
	{
		if (indata == null)
		{
			return null;
		}

		byte[] GBKDataBytes;

		try
		{
			GBKDataBytes = indata.getBytes("GBK");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
			return null;
		}

		String GBKDataString = ConversionTools.ByteArrayToString(GBKDataBytes, GBKDataBytes.length);
		return GBKDataString;
	}

	/**
	 * 工具函数：GBK coding to character String , eg. 0x31b6d432 to "1对2"
	 * 
	 * @param cArray
	 *            HEX value
	 * @return 含有汉字的字符串
	 */
	public static String GBK2Sring(String gbkEncoding) {
		String sResult = "";
		String sTmp = "";
		char c1, c2;
		int index = 0;

		// String to Chinese , eg. "31b6d432" to "1对2"
		index = 0;
		while (index < gbkEncoding.length()) {
			if (gbkEncoding.charAt(index) >= '0' && gbkEncoding.charAt(index) <= '9') {
				// ASCII , eg. "31" to '1', "41" to 'A'
				c1 = (char) ((gbkEncoding.charAt(index) - '0') * 16);
				c2 = gbkEncoding.charAt(index + 1);
				if (c2 >= '0' && c2 <= '9') {
					c2 -= '0';
				} else if (c2 >= 'A' && c2 <= 'F') {
					c2 -= 'A' - 10;
				} else if (c2 >= 'a' && c2 <= 'f') {
					c2 -= 'a' - 10;
				}
				c1 += c2;
				sResult += c1;
				index += 2;
			} else {
				// GBK , eg. "b6d4" to "对"
				sTmp = gbkEncoding.substring(index, index + 4);
				sResult += toGbkChar(sTmp);
				index += 4;
			}
		}
		return sResult;
	}
	
	
	/**
	 * String2 UTF-8
	 * @param indata
	 * @return
	 */
	public static String string2UTF8(String indata)
	{
		if (indata == null)
		{
			return null;
		}

		if(indata.equals(""))
		{
			return "";
		}
		
		byte[] GBKDataBytes;

		try
		{
			GBKDataBytes = indata.getBytes("UTF-8");
		}
		catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
			return null;
		}

		String GBKDataString = ConversionTools.ByteArrayToString(GBKDataBytes, GBKDataBytes.length);
		return GBKDataString;
	}	

	/**
	 * UTF-8 2 String
	 * 
	 * @param indata
	 * @return
	 */
	public static String UTF82String(String indata)
	{
		if (indata == null)
		{
			return null;
		}

		String finalDataString = null;

		byte[] indataByte = ConversionTools.stringToByteArr(indata);
		try
		{
			finalDataString = new String(indataByte, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}

		return finalDataString;
	}

	private final static char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	/**
	 * 工具函数：GBK coding to character String , eg. 0x31b6d432 to "1对2"
	 * 
	 * @param cArray
	 *            HEX value
	 * @return 含有汉字的字符串
	 */
	public static String GBKtoSring(String gbkEncoding) {
		String sResult = "";
		String sTmp = "";
		char c1, c2;
		int index = 0;

		// String to Chinese , eg. "31b6d432" to "1对2"
		index = 0;
		while (index < gbkEncoding.length()) {
			if (gbkEncoding.charAt(index) >= '0' && gbkEncoding.charAt(index) <= '9') {
				// ASCII , eg. "31" to '1', "41" to 'A'
				c1 = (char) ((gbkEncoding.charAt(index) - '0') * 16);
				c2 = gbkEncoding.charAt(index + 1);
				if (c2 >= '0' && c2 <= '9') {
					c2 -= '0';
				} else if (c2 >= 'A' && c2 <= 'F') {
					c2 -= 'A' - 10;
				} else if (c2 >= 'a' && c2 <= 'f') {
					c2 -= 'a' - 10;
				}
				c1 += c2;
				sResult += c1;
				index += 2;
			} else {
				// GBK , eg. "b6d4" to "对"
				sTmp = gbkEncoding.substring(index, index + 4);
				sResult += toGbkChar(sTmp);
				index += 4;
			}
		}
		return sResult;
	}

	/**
	 * 工具函数：单个汉字，GBK code to Chinese character ,eg. "b6d4" to "对"
	 * 
	 * @param gbkEncoding
	 * @return corresponding Chinese
	 */
	public static char toGbkChar(String gbkEncoding) {
		if (gbkEncoding == null || !gbkEncoding.matches("[0-9a-fA-F]{4}")) {
			throw new IllegalArgumentException("GBK encoding data error!");
		}
		int num = Integer.parseInt(gbkEncoding, 16);
		if (num < 0 || num > 0xffff) {
			throw new IllegalArgumentException("GBK encoding data error!");
		}
		byte[] bys = new byte[2];
		bys[0] = (byte) (num >> 8 & 0xff);
		bys[1] = (byte) (num & 0xff);

		if (bys == null || bys.length != 2) {
			throw new IllegalArgumentException("GBK encoding byte length must be 2");
		}
		String str = null;
		try {
			str = new String(bys, "gbk");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("GBK encoding convert error!");
		}
		if (str == null) {
			throw new IllegalArgumentException("GBK encoding convert error!");
		}
		return str.charAt(0);
	}

	/**
	 * 工具函数，将两个字符转换为一个字节，如‘A’‘B’-> 0xAB
	 * 
	 * @param oldData
	 *            待转换数据
	 * @param inLen
	 *            待转换数据长度，即字符个数
	 * @param newData
	 *            转换后的数据
	 * @param outLen
	 *            转换后数据长度，即字节个数
	 * @return true 转换成功； false 转换失败，有非HEX字符
	 */

	public static boolean StringToBCD(char[] oldData, int inLen, char[] newData, int outLen[]) {

		char[] buff = new char[inLen];
		char j, k;
		int i;

		Arrays.fill(buff, (char) 0xFF);
		Arrays.fill(newData, (char) 0xFF);
		outLen[0] = (inLen + inLen % 2) / 2;

		// 对字符的BCD码转换
		for (i = 0; i < inLen; i++) {
			if (oldData[i] >= '0' && oldData[i] <= '9') {
				buff[i] = (char) (oldData[i] - 0x30);
			} else if (oldData[i] >= 'a' && oldData[i] <= 'f') {
				buff[i] = (char) (oldData[i] - 0x57);
			} else if (oldData[i] >= 'A' && oldData[i] <= 'F') {
				buff[i] = (char) (oldData[i] - 0x37);
			} else {
				return false;
			}
		}

		// 两个字符合成一个字节
		for (i = 0; i < (outLen[0] - 1); i++) {
			j = buff[2 * i];
			k = buff[2 * i + 1];
			newData[i] = (char) ((char) j << 4 & (char) 0xF0);
			newData[i] |= (char) (k & (char) 0x0F);
		}
		j = buff[2 * i];
		newData[i] = (char) ((char) j << 4 & (char) 0xF0);

		// 对字符长度奇偶的处理
		if ((inLen % 2) != 0) {
			newData[i] |= (char) 0x0F;
		} else {
			k = buff[2 * i + 1];
			newData[i] |= (char) (k & (char) 0x0F);
		}
		return true;
	}

	public static int StringToBCD(String oldData, int inLen, byte[] newData, int offset) {
		char[] outData = new char[(inLen + inLen % 2) / 2];
		int[] len = new int[1];
		boolean result;

		result = StringToBCD(oldData.toCharArray(), oldData.length(), outData, len);

		if (result) {
			for (int i = 0; i < len[0]; i++) {
				newData[offset + i] = (byte) outData[i];
			}
			return len[0];
		} else {
			return 0;
		}

	}

	public static byte[] toBytes(int a) {
		return new byte[] { (byte) (0x000000ff & (a >>> 24)), (byte) (0x000000ff & (a >>> 16)),
				(byte) (0x000000ff & (a >>> 8)), (byte) (0x000000ff & (a)) };
	}

	public static int toInt(byte[] b, int s, int n) {
		int ret = 0;

		final int e = s + n;
		for (int i = s; i < e; ++i) {
			ret <<= 8;
			ret |= b[i] & 0xFF;
		}
		return ret;
	}

	public static int toInt(byte... b) {
		int ret = 0;
		for (final byte a : b) {
			ret <<= 8;

			ret |= a & 0xFF;
		}
		return ret;
	}

	public static String toHexString(byte[] d, int s, int n) {
		final char[] ret = new char[n * 2];
		final int e = s + n;

		int x = 0;
		for (int i = s; i < e; ++i) {
			final byte v = d[i];
			ret[x++] = HEX[0x0F & (v >> 4)];
			ret[x++] = HEX[0x0F & v];
		}
		return new String(ret);
	}

	public static String toHexStringR(byte[] d, int s, int n) {
		final char[] ret = new char[n * 2];

		int x = 0;
		for (int i = s + n - 1; i >= s; --i) {
			final byte v = d[i];
			ret[x++] = HEX[0x0F & (v >> 4)];
			ret[x++] = HEX[0x0F & v];
		}
		return new String(ret);
	}

	public static int parseInt(String txt, int radix, int def) {
		int ret;
		try {
			ret = Integer.valueOf(txt, radix);
		} catch (Exception e) {
			ret = def;
		}

		return ret;
	}

	public static String ByteArrayToString(byte[] indata, int offset, int len_indata) {

		int g_RespLen = len_indata;
		byte[] g_Response = indata;

		int m = offset;
		String g_InfoString = "";

		while (m < (offset + g_RespLen)) {
			if ((g_Response[m] & 0xF0) == 0x00) {
				g_InfoString += '0' + Integer.toHexString((short) (0x00FF & g_Response[m]));
			} else {
				g_InfoString += Integer.toHexString((short) (0x00FF & g_Response[m]));
			}
			m++;
		}

		return g_InfoString;
	}

	public static short getShort(byte[] src, short srcoffset) {
		short result = 0;

		result = (short) (((src[srcoffset + 0] << 8) | src[srcoffset + 1] & 0xff));

		return result;
	}
	
	/**
	 * 数据转换 eg 000000000001 -> 0.01
	 * 
	 * @param newBalanceString
	 * @return
	 */
	public static String format2Amount(String newBalanceString)
	{
		newBalanceString = newBalanceString.substring(0, 10) + "." + newBalanceString.substring(10, 12);

		int i = 0;
		String newBalanceStringFinal = "";

		for (i = 0; i < 10; i++)
		{
			if (!newBalanceString.substring(i, i + 1).equals("0"))
			{
				newBalanceStringFinal = newBalanceString.substring(i, 13);
				break;
			}
		}

		if (i == 10)
		{
			newBalanceStringFinal = "0." + newBalanceString.substring(11, 13);
		}
		String balance = newBalanceStringFinal;
		return balance;
	}

	/**
	 * 数据转换 eg 0.01 -> 000000000001
	 * 
	 * @param transAmount
	 * @return
	 */
	public static String amount2Format(String transAmount)
	{
		int i = 0;

		while (i < transAmount.length())
		{
			if (transAmount.charAt(i) == '.')
			{
				if ((transAmount.length() - i - 1) == 2)
				{
					break;
				}
				else if ((transAmount.length() - i - 1) == 1)
				{
					transAmount += '0';
					break;
				}
				else if ((transAmount.length() - i - 1) == 0)
				{
					transAmount += "00";
					break;
				}
			}
			i++;
		}
		if (i == transAmount.length())
		{
			// amount = transAmount + '.' + "00";
			// no decimal point
			transAmount += "00";
			while (transAmount.length() < 12)
			{
				transAmount = '0' + transAmount;
			}
		}
		else
		{
			// amount = transAmount;
			// get rid of decimal point
			transAmount = transAmount.substring(0, i) + transAmount.substring(i + 1, transAmount.length());
			while (transAmount.length() < 12)
			{
				transAmount = '0' + transAmount;
			}
		}

		return transAmount;
	}

	public static String gbToUtf8(String str) throws UnsupportedEncodingException {   
	        StringBuffer sb = new StringBuffer();   
	        for (int i = 0; i < str.length(); i++) {   
	            String s = str.substring(i, i + 1);   
	            if (s.charAt(0) > 0x80) {   
	                byte[] bytes = s.getBytes("Unicode");   
	                String binaryStr = "";   
	                for (int j = 2; j < bytes.length; j += 2) {   
	                    // the first byte   
	                    String hexStr = getHexString(bytes[j + 1]);   
	                    String binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
	                    binaryStr += binStr;   
	                    // the second byte   
	                    hexStr = getHexString(bytes[j]);   
	                    binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
	                    binaryStr += binStr;   
	                }   
	                // convert unicode to utf-8   
	                String s1 = "1110" + binaryStr.substring(0, 4);   
	                String s2 = "10" + binaryStr.substring(4, 10);   
	                String s3 = "10" + binaryStr.substring(10, 16);   
	                byte[] bs = new byte[3];   
	                bs[0] = Integer.valueOf(s1, 2).byteValue();   
	                bs[1] = Integer.valueOf(s2, 2).byteValue();   
	                bs[2] = Integer.valueOf(s3, 2).byteValue();   
	                String ss = new String(bs, "UTF-8");   
	                sb.append(ss);   
	            } else {   
	                sb.append(s);   
	            }   
	        }   
	        return sb.toString();   
	    }   
	  
	public static String getHexString(byte b) {   
	        String hexStr = Integer.toHexString(b);   
	        int m = hexStr.length();   
	        if (m < 2) {   
	            hexStr = "0" + hexStr;   
	        } else {   
	            hexStr = hexStr.substring(m - 2);   
	        }   
	        return hexStr;   
	    }   
	  
	    public static String getBinaryString(int i) {   
	        String binaryStr = Integer.toBinaryString(i);   
	        int length = binaryStr.length();   
	        for (int l = 0; l < 8 - length; l++) {   
	            binaryStr = "0" + binaryStr;   
	        }   
	        return binaryStr;   
	    }   
	
	
}
	
