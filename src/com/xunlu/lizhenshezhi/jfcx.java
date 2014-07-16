package com.xunlu.lizhenshezhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class jfcx extends Activity {
	private Button cha;
//积分查询	
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.jfcx);
	cha = (Button)findViewById(R.id.chaxun);
	cha.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(jfcx.this,XianshiActivity.class);
			startActivity(intent);
		}
	});
	
}
  //积分查询的按钮点击事件
 public void btn_select(View v){
	 Log.e("t", "eeeee");
 }
}
