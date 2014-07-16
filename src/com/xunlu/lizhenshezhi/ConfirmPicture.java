package com.xunlu.lizhenshezhi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.conn.path.AllPath;
import com.thread.m.Threads;
import com.util.ImageUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmPicture extends Activity {

	Intent reqIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmpicture);
		reqIntent = getIntent();
		Uri imgUri = reqIntent.getData();
		ImageView dataimage = (ImageView)findViewById(R.id.dataimage);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		//opt.outHeight = opt.
		opt.inSampleSize = 4;		
		
		Bitmap data = BitmapFactory.decodeFile(imgUri.getPath(),opt);
		dataimage.setImageBitmap(data);
		findViewById(R.id.surereset).getBackground().setAlpha(0);
		findViewById(R.id.suresubmit).getBackground().setAlpha(0);
	}
	
	public void submit(View v) {
		
		Intent intent = new Intent(reqIntent);
		intent.setClass(getApplicationContext(), FinalPicture.class);
		startActivity(intent);
		finish();

	}
	
	public void reset(View v) {
		this.finish();
	}
	
}
