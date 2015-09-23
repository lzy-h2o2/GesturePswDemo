package com.lzy.gesture.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author luzhenyu
 * @descrip <p>
 *          工具类
 *          </p>
 * */
public class Utils {

	/**
	 * 获取屏幕分辨率
	 * 
	 * @param context
	 * @return [0] - width,[1] - height
	 */
	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		int width = displayMetrics.widthPixels;
		int height = displayMetrics.heightPixels;
		int [] result = {width, height};
		return result;
	}
}
