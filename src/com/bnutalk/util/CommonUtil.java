package com.bnutalk.util;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.StaticLayout;
import android.util.Base64;
import android.util.Log;

public class CommonUtil {
	
	List<RecentMsgEntity> list=new ArrayList<RecentMsgEntity>();
	public CommonUtil() {
	}
	public static String getCurrentTime()
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String time = sDateFormat.format(new java.util.Date());
		return time;
	}
	/**
	 * compare time
	 * @param s1
	 * @param s2
	 * @return int 0:equal 1:s1>s2 2:s1<s2
	 */
	public static int compareTime(String s1, String s2) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (java.text.ParseException e) {
			System.err.println("格式不正确");
		}
		
		int result = c1.compareTo(c2);
		if (result == 0)
			return 0;
		else if (result < 0)
			return -1;
		else
			return 1;
	}
	public static void sortListByTime(List<RecentMsgEntity> list)
	{
		Collections.sort(list);
	}
	
	public static Bitmap imgStrToBitmap(String strPhoto) {
		byte[] photoimg = Base64.decode(strPhoto, 0);
		for (int i = 0; i < photoimg.length; ++i) {
			if (photoimg[i] < 0) {
				// 调整异常数据
				photoimg[i] += 256;
			}
		}
		return BitmapFactory.decodeByteArray(photoimg, 0, photoimg.length);
	}
	/**
	 * transfer json to List<RecentMsgEntity> list
	 * @param strJson
	 * @param list
	 */
	public static void parseJsonMsg(String strJson,List<RecentMsgEntity> list) {
		try {
			JSONArray jsonArray = new JSONArray(strJson);
			for(int i=0;i<jsonArray.length();i++)
			{
				JSONObject user = jsonArray.getJSONObject(i);
				String strUid = user.getString("strUid");
				String strNickname = user.getString("strNickname");
				String strPhoto = user.getString("strPhoto");
				// 图片string转换成Bitmap
				Bitmap bmPhoto=CommonUtil.imgStrToBitmap(strPhoto);
				
				String content="good morning!";
				String time="2015-5-15";
				int isRead=RecentMsgEntity.READ;
				RecentMsgEntity rEntity=new RecentMsgEntity(bmPhoto,strUid, strNickname, content, time, isRead);
				list.add(rEntity);
			}
			Log.v("parseJsonMsg", "parseJsonMsg success");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	/**
	 * transfer json to List<UserEntity> list
	 * @param strJson
	 * @param list
	 */
	public static void parseJsonUser(String strJson,List<UserEntity> list) {
		try {
			JSONArray jsonArray = new JSONArray(strJson);
			for(int i=0;i<jsonArray.length();i++)
			{
				UserEntity uEntity=new UserEntity();
				JSONObject user = jsonArray.getJSONObject(i);
				uEntity.setUid(user.getString("uid"));
				uEntity.setNick(user.getString("nick"));
				uEntity.setAge(user.getInt("age"));
				uEntity.setSex(user.getInt("sex"));
				uEntity.setFaculty(user.getString("faculty"));
				uEntity.setLikeLanguage(user.getString("likeLanguage"));
				uEntity.setMotherTone(user.getString("motherTone"));
				
				uEntity.setPlace("BNU");//
//				uEntity.setVoice(voice);
				
				String strPhoto=user.getString("avatar");
				Bitmap avatar=CommonUtil.imgStrToBitmap(strPhoto);
				uEntity.setAvatar(avatar);
				list.add(uEntity);
			}
			Log.v("parseJsonUser", "parseJsonUser success");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * transfer json to List<ContactEntity> list
	 * @param strJson
	 * @param list
	 */
	public static void parseJsonContact(String strJson,List<ContactEntity> list) {
		try {
			JSONArray jsonArray = new JSONArray(strJson);
			for(int i=0;i<jsonArray.length();i++)
			{
				ContactEntity cEntity=new ContactEntity();
				JSONObject contact = jsonArray.getJSONObject(i);
				cEntity.setUid(contact.getString("uid"));
				cEntity.setNick(contact.getString("nick"));
				cEntity.setNationality(contact.getString("nationality"));
				
				String strPhoto=contact.getString("avatar");
				Bitmap avatar=CommonUtil.imgStrToBitmap(strPhoto);
				cEntity.setAvatar(avatar);
				
				list.add(cEntity);
			}
			Log.v("parseJsonContact", "parseJsonContact success");
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	

}
