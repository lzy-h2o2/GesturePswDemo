package com.lzy.gesture;

import com.lzy.gesture.utils.AppManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultShowActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_gesture_result_layout);
		AppManager.getAppManager().addActivity(this);
		
		Intent intent = getIntent();
		TextView textView = (TextView) findViewById(R.id.result);
		textView.setText("手势密码是：" + intent.getStringExtra("code"));
	}
	
	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishAllActivity();
	}
}
