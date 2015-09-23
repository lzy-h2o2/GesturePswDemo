package com.lzy.gesture;

import com.lzy.gesture.utils.AppManager;
import com.lzy.gesture.weiget.GestureContentView;
import com.lzy.gesture.weiget.GestureDrawline.GestureCallBack;
import com.lzy.gesture.weiget.LockIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 手势密码设置界面
 *
 */
public class GestureLockActivity extends Activity implements OnClickListener {
	private TextView mTextCancel;
	private LockIndicator mLockIndicator;
	private TextView mTextTip;
	private FrameLayout mGestureContainer;
	private GestureContentView mGestureContentView;
	private TextView mTextReset;
	private boolean mIsFirstInput = true;
	private String mFirstPassword = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.draw_psw_canvas_layout);
		AppManager.getAppManager().addActivity(this);
		
		SharedPreferences sharedPreferences = getSharedPreferences("gesturePsw", Context.MODE_PRIVATE);
		String gesturePsw = sharedPreferences.getString("gesturePsw", "");
		if (!"".equals(gesturePsw) || !TextUtils.isEmpty(gesturePsw)) {
			Intent intent = new Intent(GestureLockActivity.this, GestureVerifyActivity.class);
			intent.putExtra("gesturePsw", gesturePsw);
			intent.putExtra("phoneNumer", "15852188888");
			startActivity(intent);
		}
		setUpViews();
		setUpListeners();
	}
	
	private void setUpViews() {
		mTextCancel = (TextView) findViewById(R.id.text_cancel);
		mTextReset = (TextView) findViewById(R.id.text_show);
		mTextReset.setClickable(false);
		mLockIndicator = (LockIndicator) findViewById(R.id.lock_indicator);
		mTextTip = (TextView) findViewById(R.id.text_tip);
		mGestureContainer = (FrameLayout) findViewById(R.id.gesture_container);
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "", new GestureCallBack() {
			@Override
			public void onGestureCodeInput(String inputCode) {
				if (!isInputPassValidate(inputCode)) {
					mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>最少链接4个点, 请重新输入</font>"));
					mGestureContentView.clearDrawlineState(0L);
					return;
				}
				if (mIsFirstInput) {
					mFirstPassword = inputCode;
					updateCodeList(inputCode);
					mGestureContentView.clearDrawlineState(0L);
					mTextReset.setClickable(true);
					mTextReset.setText("重新设置手势密码");
				} else {
					if (inputCode.equals(mFirstPassword)) {
						Toast.makeText(GestureLockActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
						
						SharedPreferences sp = getSharedPreferences("gesturePsw", Context.MODE_PRIVATE);
						sp.edit().putString("gesturePsw", inputCode).commit();
						
						mGestureContentView.clearDrawlineState(0L);
						Intent intent = new Intent(GestureLockActivity.this, ResultShowActivity.class);
						intent.putExtra("code", inputCode);
						startActivity(intent);
						GestureLockActivity.this.finish();
					} else {
						mTextTip.setText(Html.fromHtml("<font color='#c70c1e'>与上一次绘制不一致，请重新绘制</font>"));
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils.loadAnimation(GestureLockActivity.this, R.anim.shake);
						mTextTip.startAnimation(shakeAnimation);
						// 保持绘制的线，1.5秒后清除
						mGestureContentView.clearDrawlineState(1500L);
					}
				}
				mIsFirstInput = false;
			}

			@Override
			public void checkedSuccess() {
				
			}

			@Override
			public void checkedFail() {
				
			}
		});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
		updateCodeList("");
	}
	
	private void setUpListeners() {
		mTextCancel.setOnClickListener(this);
		mTextReset.setOnClickListener(this);
	}
	
	private void updateCodeList(String inputCode) {
		// 更新选择的图案
		mLockIndicator.setPath(inputCode);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.text_cancel:
			this.finish();
			break;
		case R.id.text_show:
			mIsFirstInput = true;
			updateCodeList("");
			mTextTip.setText("绘制解锁图案");
			break;
		default:
			break;
		}
	}
	
	private boolean isInputPassValidate(String inputPassword) {
		if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
			return false;
		}
		return true;
	}
	
}

