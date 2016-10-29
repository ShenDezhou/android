package com.example.sdkey;

import java.util.Vector;

import cn.com.key.CertInfo;
import cn.com.key.exception.SDKeyException;
import Sumavision.Library.SmartSDLib;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * 选择菜单页面
 *
 */
public class TabMenu extends Activity {
	private int id=0;
	
	//private Button ConnectDeviceBtn;
	private Button RSABtn;
	private Button DESBtn;
	private Button FileMgrBtn;
	private Button AppMgrBtn;
	private Button UserMgrBtn;
	private Button IntroduceBtn;
	Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.tabmenu);
		context = this;
	   
		//ConnectDeviceBtn = (Button) this.findViewById(R.id.opendevice);
		RSABtn=(Button) this.findViewById(R.id.RSABtn);
		DESBtn=(Button) this.findViewById(R.id.DESBtn);
		FileMgrBtn=(Button) this.findViewById(R.id.fileMgrBtn);
		AppMgrBtn=(Button) this.findViewById(R.id.appMgrBtn);
		UserMgrBtn=(Button) this.findViewById(R.id.userMgrBtn);
		IntroduceBtn = (Button) this.findViewById(R.id.introduceBtn);
        
		//ConnectDeviceBtn.setOnClickListener(new connectDeviceBtnListener());
		RSABtn.setOnClickListener(new rsaBtnListener());
		DESBtn.setOnClickListener(new desBtnListener());
		FileMgrBtn.setOnClickListener((android.view.View.OnClickListener) new fileMgrBtnListener());
		AppMgrBtn.setOnClickListener(new appMgrBtnListener());
		UserMgrBtn.setOnClickListener(new userMgrBtnListener());
		IntroduceBtn.setOnClickListener(new introduceBtnListener());
		
		cn.com.key.JAPI.setPackageName("com.example.sdkey");
	}
	
	
	
/*	class connectDeviceBtnListener implements android.view.View.OnClickListener{
		public void onClick(View v) {
			byte[] outdata = new byte[512];
			int outdataLen[] = new int[1];

			int ret = SmartSDLib.getInstance().SDConnect(context, outdataLen, outdata);
			if (ret != 0)
			{
				showText("设备已被连接");
				return;
			}
			ret = SmartSDLib.getInstance().Reset(outdataLen, outdata);
			if (ret != 0)
			{
				showText("设备已被连接");
				return;
			}
			
			showText("设备已连接");
			
	        return;
		}
    }*/
	
	class rsaBtnListener implements OnClickListener{
		public void onClick(View v) {
			boolean f = cn.com.key.JAPI.setPackageName("com.example.sdkey"); 
			showText("设置包名"+f);
			
			boolean r = cn.com.key.JAPI.getInstance().getOpenResult();
			showText("设备已连接"+r);
			r=cn.com.key.JAPI.getInstance().checkValid();
			showText("设备有效性"+r);
			r=cn.com.key.JAPI.getInstance().reconnectKey();
			showText("设备重新连接"+r);
			try {
				String s =cn.com.key.JAPI.getInstance().getBJCAKeyParam(1);
				showText("FREE SIZE:"+s);
				 s =cn.com.key.JAPI.getInstance().getBJCAKeyParam(2);
				showText("SERIAL NUMBER:"+s);
				 s =cn.com.key.JAPI.getInstance().getBJCAKeyParam(3);
				showText("RETRY TIMES:"+s);
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				showText(e.getMessage());
			}
			
//			Intent intent=new Intent(TabMenu.this,RSAToolActivity.class);
//			Bundle b=new Bundle();
//			b.putString("action", "rsaToolBtn");
//			intent.putExtra("RSATool", b);
//			startActivityForResult(intent,id);
		}
	}
	
	class desBtnListener implements OnClickListener{
		public void onClick(View v)  {
			 try {
				int r = cn.com.key.JAPI.getInstance().login( cn.com.key.JAPI.PIN_TYPE_ADMIN,"88888888");
				showText("login admin:"+r);
				boolean f = cn.com.key.JAPI.getInstance().unLockUserPass("88888888");
				showText("UNLOCK user:"+f);
				r = cn.com.key.JAPI.getInstance().login( cn.com.key.JAPI.PIN_TYPE_USER,"88888888");
				showText("login user:"+r);
				f = cn.com.key.JAPI.getInstance().changeUserPin( "88888888","88888888");
				showText("CHANGEPIN user:"+f);
				f = cn.com.key.JAPI.getInstance().changeAdminPin( "88888888","88888888");
				showText("CHANGEPIN admin:"+f);
				r = cn.com.key.JAPI.getInstance().logout();
				showText("LOGOUT:"+r);
				
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				showText(e.getMessage());
			}
			
//			Intent intent=new Intent(TabMenu.this,DESToolActivity.class);
//			Bundle b=new Bundle();
//			b.putString("action", "desToolBtn");
//			intent.putExtra("DESTool", b);
//			startActivityForResult(intent,id);
		}
	}
	
	class fileMgrBtnListener implements android.view.View.OnClickListener{
			private void HashUpdate(int alg,byte[] data)
			{
				int outLen;
		        
		        switch (alg) {
	//	        case cn.com.key.JAPI.ALG_MD2:
	//		    case cn.com.key.JAPI.ALG_MD5:
		        case cn.com.key.JAPI.ALG_SM3:
		        case cn.com.key.JAPI.ALG_SHA1:
		             break;
		        default:
		        	showText("暂不支持该算法...");
		            return;
		        }
				try{
			        long ctx = cn.com.key.JAPI.getInstance().HashInit(alg);
			        outLen = 0;
			        int r = cn.com.key.JAPI.getInstance().HashUpdate((int)ctx, data);
			        byte[] hash = cn.com.key.JAPI.getInstance().HashFinal((int)ctx);
			        StringBuilder sb = new StringBuilder(hash.length);
					for(int i =0; i< hash.length; i++)
						sb.append(hash[i]+",");
					showText(sb.toString());
					
			    }catch (SDKeyException e) {
			    	showText(e.getMessage());
			        return;
			    }
			}
			private void EncryptDecryptUpdate(int alg,int mode,byte[] key,byte[] iv,byte[] data)
			{
				byte[] cipher;
		        byte[] plain;
		        byte[] tmpOut = new byte[data.length+16];
		        int outLen;
		        
		        switch (alg) {
		        case cn.com.key.JAPI.ALG_DES:
		        	if(key.length!=8 || iv.length!=8)
		        	{	showText("key or iv cuo...");
		            	return;
		            }
		            break;
		        case cn.com.key.JAPI.ALG_3DES:
		        	if(key.length!=16 || iv.length!=8)
		        	{	showText("key or iv cuo...");
		            	return;
		            }
		            break;
		        case cn.com.key.JAPI.ALG_AES:
		        case cn.com.key.JAPI.ALG_SM1:
		        	if(key.length!=16 || iv.length!=16)
		        	{	showText("key or iv cuo...");
		            	return;
		            }
		            break;
		        case cn.com.key.JAPI.ALG_SSF33:
		        	if(key.length!=16 || iv.length!=8)
		        	{	
		        		showText("key or iv cuo...");
		            	return;
		            }
		            break;
		        default:
		        	showText("暂不支持该算法...");
		            return;
		        }
		        try {
		            long ctx = cn.com.key.JAPI.getInstance().SymEncryptInit(alg, mode, cn.com.key.JAPI.PADDING_TYPE_PKCS5, key, iv);
		            outLen = 0;
		            cipher = cn.com.key.JAPI.getInstance().SymEncryptUpdate((int)ctx, data, data.length);
		            if (cipher != null)
		            {
		                System.arraycopy(cipher, 0, tmpOut, 0, cipher.length);
		                outLen += cipher.length;
		            }            
		            cipher = cn.com.key.JAPI.getInstance().SymEncryptFinal((int)ctx);
		            if (cipher != null)
		            {
		                System.arraycopy(cipher,0, tmpOut, outLen, cipher.length);
		                outLen += cipher.length;
		            }
		            cipher = new byte[outLen];
		            System.arraycopy(tmpOut, 0, cipher, 0, outLen);
		        } catch (SDKeyException e) {
		        	showText(e.getMessage());
		            return;
		        }
		        StringBuilder sb = new StringBuilder(cipher.length);
				for(int i =0; i< cipher.length; i++)
					sb.append(cipher[i]+",");
				showText(sb.toString());
				
		        try{
		            long ctx = cn.com.key.JAPI.getInstance().SymDecryptInit(alg, mode, cn.com.key.JAPI.PADDING_TYPE_PKCS5, key, iv);
		            outLen = 0;
		            plain = cn.com.key.JAPI.getInstance().SymDecryptUpdate((int)ctx, cipher, cipher.length);
		            if (plain != null)
		            {
		                System.arraycopy(plain, 0, tmpOut, 0, plain.length);
		                outLen += plain.length;
		            }
		            plain = cn.com.key.JAPI.getInstance().SymDecryptFinal((int)ctx);
		            if (plain != null)
		            {
		                System.arraycopy(plain, 0, tmpOut, outLen, plain.length);
		                outLen += plain.length;
		            }
		            plain = new byte[outLen];
		            System.arraycopy(tmpOut, 0, plain, 0, outLen);
		        }catch (SDKeyException e) {
		        	showText(e.getMessage());
		            return;
		        }
		         sb = new StringBuilder(plain.length);
				for(int i =0; i< plain.length; i++)
					sb.append(plain[i]+",");
				showText(sb.toString());
				showText("compare result:"+java.util.Arrays.equals(plain,data));
				
			}
			public void onClick(View v) {
				byte[] r= cn.com.key.JAPI.getInstance().getRandom(8);
				StringBuilder sb = new StringBuilder(r.length);
				for(int i =0; i< r.length; i++)
					sb.append(r[i]+",");
				showText(sb.toString());
				byte[] iv=new byte[8];
				byte[] key=new byte[16];
				byte[] data=new byte[16];
				byte[] data32=new byte[32];
				byte[] iv16=new byte[16];
				byte[] key8=new byte[8];
				byte[] data8=new byte[8];
				byte spiv[] = {(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6};
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_DES,cn.com.key.JAPI.ALG_MOD_ECB,key8,iv,data8);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_DES,cn.com.key.JAPI.ALG_MOD_CBC,key8,iv,data8);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_3DES,cn.com.key.JAPI.ALG_MOD_ECB,key,iv,data);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_3DES,cn.com.key.JAPI.ALG_MOD_CBC,key,iv,data);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_AES,cn.com.key.JAPI.ALG_MOD_ECB,key,iv16,data);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_AES,cn.com.key.JAPI.ALG_MOD_CBC,key,iv16,data);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_SSF33,cn.com.key.JAPI.ALG_MOD_ECB,key,spiv,data8);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_SSF33,cn.com.key.JAPI.ALG_MOD_CBC,key,spiv,data8);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_SM1,cn.com.key.JAPI.ALG_MOD_ECB,key,iv16,data);
				EncryptDecryptUpdate(cn.com.key.JAPI.ALG_SM1,cn.com.key.JAPI.ALG_MOD_CBC,key,iv16,data);
				
				HashUpdate(cn.com.key.JAPI.ALG_SHA1,data);
				HashUpdate(cn.com.key.JAPI.ALG_SM3,data);
				
	//			Intent intent=new Intent(TabMenu.this,FileMgrActivity.class);
	//			Bundle b=new Bundle();
	//			b.putString("action", "fileMgrBtn");
	//			intent.putExtra("FILEMGR", b);
	//			startActivityForResult(intent,id);
			}
	    }

	class appMgrBtnListener implements OnClickListener{
		
		private void EncryptDecrypt(int alg,int mode,byte[] key,byte[] iv,byte[] data)
		{
			
			showText("algo:"+alg+" mode:"+mode);
			try {
				byte[] c =cn.com.key.JAPI.getInstance().SymEncrypt(alg,mode,key,iv,data);
				StringBuilder sb = new StringBuilder(c.length);
				for(int i =0; i< c.length; i++)
					sb.append(c[i]+",");
				showText(sb.toString());
				byte[] d =cn.com.key.JAPI.getInstance().SymDecrypt(alg,mode,key,iv,c);
				showText("compare result:"+java.util.Arrays.equals(d,data));
				
				
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				showText(e.getMessage());
			}
		}

		public void onClick(View v) {
			byte[] r= cn.com.key.JAPI.getInstance().getRandom(8);
			StringBuilder sb = new StringBuilder(r.length);
			for(int i =0; i< r.length; i++)
				sb.append(r[i]+",");
			showText(sb.toString());
			byte[] iv=new byte[8];
			byte[] key=new byte[16];
			byte[] data=new byte[16];
			byte[] data32=new byte[32];
			byte[] iv16=new byte[16];
			byte[] key8=new byte[8];
			byte[] data8=new byte[8];
			byte spiv[] = {(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6,(byte)0xA6};
			EncryptDecrypt(cn.com.key.JAPI.ALG_DES,cn.com.key.JAPI.ALG_MOD_ECB,key8,iv,data8);
			EncryptDecrypt(cn.com.key.JAPI.ALG_DES,cn.com.key.JAPI.ALG_MOD_CBC,key8,iv,data8);
			EncryptDecrypt(cn.com.key.JAPI.ALG_3DES,cn.com.key.JAPI.ALG_MOD_ECB,key,iv,data);
			EncryptDecrypt(cn.com.key.JAPI.ALG_3DES,cn.com.key.JAPI.ALG_MOD_CBC,key,iv,data);
			EncryptDecrypt(cn.com.key.JAPI.ALG_AES,cn.com.key.JAPI.ALG_MOD_ECB,key,iv16,data);
			EncryptDecrypt(cn.com.key.JAPI.ALG_AES,cn.com.key.JAPI.ALG_MOD_CBC,key,iv16,data);
			EncryptDecrypt(cn.com.key.JAPI.ALG_SSF33,cn.com.key.JAPI.ALG_MOD_ECB,key,spiv,data8);
			EncryptDecrypt(cn.com.key.JAPI.ALG_SSF33,cn.com.key.JAPI.ALG_MOD_CBC,key,spiv,data8);
			EncryptDecrypt(cn.com.key.JAPI.ALG_SM1,cn.com.key.JAPI.ALG_MOD_ECB,key,iv16,data);
			EncryptDecrypt(cn.com.key.JAPI.ALG_SM1,cn.com.key.JAPI.ALG_MOD_CBC,key,iv16,data);
			
			
//			Intent intent=new Intent(TabMenu.this,AppMgrActivity.class);
//			Bundle b=new Bundle();
//			b.putString("action", "rsaToolBtn");
//			intent.putExtra("RSATool", b);
//			startActivityForResult(intent,id);
		}
	}
	
	class userMgrBtnListener implements android.view.View.OnClickListener{
		private void showContainers(){
			
			try {
				Vector<cn.com.key.CertInfo> vec = cn.com.key.JAPI.getInstance().getKeysList();
				for(int i=0;i<vec.size();i++)
				{
					cn.com.key.CertInfo ci = vec.get(i);
					showText(ci.getAlias());
				}
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		private void RSA(){
			try {
				byte[] data = new byte[8];
				boolean f = cn.com.key.JAPI.getInstance().genRSAKey("0", cn.com.key.JAPI.AT_KEYEXCHANGE, 1024);
				showText("RSA container:0, Exchage key,create:"+f);
				f = cn.com.key.JAPI.getInstance().genRSAKey("0", cn.com.key.JAPI.AT_SIGNATURE, 1024);
				showText("RSA container:0, Signature key,create:"+f);
				
				byte[] pubkey = cn.com.key.JAPI.getInstance().exportPubRSA("0", cn.com.key.JAPI.AT_SIGNATURE);
				StringBuilder sb = new StringBuilder(pubkey.length);
				for(int i =0; i< pubkey.length; i++)
					sb.append(pubkey[i]+",");
				showText(sb.toString());
				
				byte[] cipher = cn.com.key.JAPI.getInstance().pubKeyEnc("0", cn.com.key.JAPI.AT_KEYEXCHANGE, data);
				 sb = new StringBuilder(cipher.length);
				for(int i =0; i< cipher.length; i++)
					sb.append(cipher[i]+",");
				showText(sb.toString());
				byte[] plain = cn.com.key.JAPI.getInstance().priKeyDec("0", cn.com.key.JAPI.AT_KEYEXCHANGE, cipher);
				showText("compare PubEnc and PriDec result:"+ java.util.Arrays.equals(data, plain));
				
				byte[] signed = cn.com.key.JAPI.getInstance().SignedData_Sign("0", cn.com.key.JAPI.AT_SIGNATURE, cn.com.key.JAPI.ALG_SHA1, data);
				sb = new StringBuilder(signed.length);
				for(int i =0; i< signed.length; i++)
					sb.append(signed[i]+",");
				showText("Signature:"+sb.toString());
				f = cn.com.key.JAPI.getInstance().signedData_Verify("0", cn.com.key.JAPI.AT_SIGNATURE, cn.com.key.JAPI.ALG_SHA1,signed, data);
				showText("Verify result:"+ f);
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				showText(e.getMessage());
			}
		}
		public void ECC(){
			try{
				byte[] data = new byte[8];
				//ECC
				int r = cn.com.key.JAPI.getInstance().GenEccKeyPair("1", cn.com.key.JAPI.AT_KEYEXCHANGE);
				showText("ECC container:1, Exchage key,create:"+r);
				r = cn.com.key.JAPI.getInstance().GenEccKeyPair("1", cn.com.key.JAPI.AT_SIGNATURE);
				showText("ECC container:1, Signature key,create:"+r);
				
				byte[] pubkey = cn.com.key.JAPI.getInstance().GetEccPublicKey("1", cn.com.key.JAPI.AT_SIGNATURE);
				StringBuilder sb = new StringBuilder(pubkey.length);
				for(int i =0; i< pubkey.length; i++)
					sb.append(pubkey[i]+",");
				showText(sb.toString());
				
				byte[] cipher = cn.com.key.JAPI.getInstance().EccEncrypt("1", cn.com.key.JAPI.AT_KEYEXCHANGE, data);
				 sb = new StringBuilder(cipher.length);
				for(int i =0; i< cipher.length; i++)
					sb.append(cipher[i]+",");
				showText(sb.toString());
				byte[] plain = cn.com.key.JAPI.getInstance().EccDecrypt("1", cn.com.key.JAPI.AT_KEYEXCHANGE, cipher);
				showText("compare PubEnc and PriDec result:"+ java.util.Arrays.equals(data, plain));
	
	
				byte[] hash = cn.com.key.JAPI.getInstance().Hash(cn.com.key.JAPI.ALG_SM3, data);
				 sb = new StringBuilder(hash.length);
				for(int i =0; i< hash.length; i++)
					sb.append(hash[i]+",");
				showText("Hash:"+sb.toString());
				
				byte[] signed = cn.com.key.JAPI.getInstance().EccSign("1", cn.com.key.JAPI.AT_SIGNATURE, hash);
				sb = new StringBuilder(signed.length);
				for(int i =0; i< signed.length; i++)
					sb.append(signed[i]+",");
				showText("Signature:"+sb.toString());
				r = cn.com.key.JAPI.getInstance().EccVerify("1", cn.com.key.JAPI.AT_SIGNATURE,hash, signed);
				showText("Verify result:"+ r);
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				showText(e.getMessage());
			}
		}
		private void cleanUpContainers(){
			//delete containers
			try {
				boolean f = cn.com.key.JAPI.getInstance().delRSAKey("0", cn.com.key.JAPI.AT_KEYEXCHANGE);	
				showText("del ras key"+f);
				f = cn.com.key.JAPI.getInstance().delRSAKey("0", cn.com.key.JAPI.AT_SIGNATURE);	
				showText("del ras key"+f);
				Vector<cn.com.key.CertInfo> vec = cn.com.key.JAPI.getInstance().getKeysList();
				for(int i=0;i<vec.size();i++)
				{
					cn.com.key.CertInfo ci = vec.get(i);
					cn.com.key.JAPI.getInstance().delContainer(ci.getAlias());
					showText(ci.getAlias()+" deleted");
				}
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void onClick(View v) {
			try {
				int r = cn.com.key.JAPI.getInstance().login( cn.com.key.JAPI.PIN_TYPE_USER,"88888888");
				showText("login user:"+r);
				RSA();
				ECC();
				//cleanUpContainers();
				
			} catch (SDKeyException e) {
				// TODO Auto-generated catch block
				showText(e.getMessage());
			}
//			Intent intent=new Intent(TabMenu.this,UserMgrActivity.class);
//			Bundle b=new Bundle();
//			b.putString("action", "userMgrBtn");
//			intent.putExtra("UserMGR", b);
//			startActivityForResult(intent,id);
		}
	}

	class introduceBtnListener implements android.view.View.OnClickListener{
			public void onClick(View v) {
				byte[] data = new byte[1024];
				try {
					boolean f = cn.com.key.JAPI.getInstance().importCertificate("1", cn.com.key.JAPI.AT_KEYEXCHANGE, data);
					showText("import cert:"+f);
					byte[] cert=cn.com.key.JAPI.getInstance().exportCertificate("1", cn.com.key.JAPI.AT_KEYEXCHANGE);
					StringBuilder sb = new StringBuilder(cert.length);
					for(int i =0; i< cert.length; i++)
						sb.append(cert[i]+",");
					showText(sb.toString());
					f = cn.com.key.JAPI.getInstance().importCertificate("1", cn.com.key.JAPI.AT_SIGNATURE, data);
					showText("import cert:"+f);
					cert=cn.com.key.JAPI.getInstance().exportCertificate("1", cn.com.key.JAPI.AT_SIGNATURE);
					sb = new StringBuilder(cert.length);
					for(int i =0; i< cert.length; i++)
						sb.append(cert[i]+",");
					showText(sb.toString());
					
					Vector<CertInfo> vec = cn.com.key.JAPI.getInstance().getCertsList();
					for(int i=0;i<vec.size();i++)
					{
						cn.com.key.CertInfo ci = vec.get(i);
						showText(ci.getAlias());
					}
				} catch (SDKeyException e) {
					// TODO Auto-generated catch block
					showText(e.getMessage());
				}
	//			Intent intent=new Intent(TabMenu.this,IntroduceActivity.class);
	//			Bundle b=new Bundle();
	//			b.putString("action", "introduceBtn");
	//			intent.putExtra("INTRODUCE", b);
	//			startActivityForResult(intent,id);
			}
	    }

	private void showText(String str){
		Toast toast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
	}
	
}
