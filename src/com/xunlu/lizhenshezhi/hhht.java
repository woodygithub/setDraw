package com.xunlu.lizhenshezhi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class hhht extends Activity{
//�滭����
 @Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.hhht);
}
 //ɨ����̵���ת
    public void next(View v){
    	Intent intent = new Intent(hhht.this, smgc.class);
		startActivity(intent);
 }
}
