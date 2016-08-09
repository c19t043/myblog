package com.blog.ssh.test.interceptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {

	  /*  
	  * MD5���� 
	  */  
	    public static String getMD5Str(String str) {        
	        MessageDigest messageDigest = null;        
	        try {        
	            messageDigest = MessageDigest.getInstance("MD5");        
	            messageDigest.reset();        
	            //messageDigest.update(str.getBytes("UTF-8"));  
	            messageDigest.update(str.getBytes("GBK"));
	        } catch (NoSuchAlgorithmException e) {        
	            System.out.println("NoSuchAlgorithmException caught!");        
	            System.exit(-1);        
	        } catch (UnsupportedEncodingException e) {        
	            e.printStackTrace();        
	        }        
	        
	        byte[] byteArray = messageDigest.digest();        
	        
	        StringBuffer md5StrBuff = new StringBuffer();        
	           
	        for (int i = 0; i < byteArray.length; i++) {                    
	            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)        
	                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));        
	            else        
	                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));        
	        }        
	      //16λ���ܣ��ӵ�9λ��25λ 
	        //return md5StrBuff.substring(8, 24).toString().toUpperCase(); 
	        return md5StrBuff.toString();
	    } 

	public static String md5Encrypt(String s) throws Exception{
		if("".equals(s)||s==null){
			return "";
		}
		MessageDigest md=MessageDigest.getInstance("MD5");
		byte[] bys = md.digest(s.getBytes());
		return Base64Encrypt(bys);
	}
	public static String Base64Encrypt(byte[] bys){
		BASE64Encoder base=new BASE64Encoder();
		String s=base.encode(bys);
		return s;
	}
	public static byte[] Base64Decrypt(String s) throws IOException {
		BASE64Decoder base = new BASE64Decoder();
		byte[] bys = base.decodeBuffer(s);
		return bys;
	}
	public static void main(String[] args) throws Exception {
		System.out.println(EncryptUtil.getMD5Str("111"));
		System.out.println(EncryptUtil.getMD5Str("111"));
		
	}
	
	private static boolean saveImageToDisk(byte[] data,String imageName){
		// 写入到文件
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(imageName));
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		} 
	
	private static byte[] decode(String imageData) throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = decoder.decodeBuffer(imageData);
		for(int i=0;i<data.length;++i)
		{
		if(data[i]<0)
		{
		//调整异常数据
		data[i]+=256;
		}
		}
		//
		return data;
		}
	
	public static boolean uploadImage(String imageData,String fileRealPathName) throws IOException
	{
		if(null == imageData || imageData.length() < 100){
			// 数据太短，明显不合理
			return false;
			} else {
			// 去除开头不合理的数据
			imageData = imageData.substring(30);
			imageData = URLDecoder.decode(imageData,"UTF-8");
			//System.out.println(imageData);
			byte[] data = decode(imageData);
			saveImageToDisk(data,fileRealPathName);
		    return true;
			}
	}
}
