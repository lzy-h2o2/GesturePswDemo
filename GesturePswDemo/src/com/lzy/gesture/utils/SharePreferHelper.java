package com.lzy.gesture.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferHelper {
	private String psw;
	private Context context;
	private SharedPreferences sharedPreferences;
	
	public SharePreferHelper(Context context) {
		this.context = context;
		this.sharedPreferences = context.getSharedPreferences("gesturePsw", Context.MODE_PRIVATE);
	}

	public String getPsw() {
		psw = sharedPreferences.getString("gesturePsw", "");
		return psw;
	}
}
