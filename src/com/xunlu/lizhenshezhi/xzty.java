package com.xunlu.lizhenshezhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
//����ͼ��
public class xzty extends Activity{
  @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.xzty);
}
  //ɨ��ԭͼ
 public void next(View v){
	 Intent intent=new Intent(this,smyt.class);
	  startActivity(intent);
 } 
}
