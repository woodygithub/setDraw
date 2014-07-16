package com.xunlu.lizhenshezhi;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.conn.path.AllPath;
import com.thread.m.Threads;
import com.util.ImageUtil;

public class FinalPicture extends Activity {

	Intent reqIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finalpicture);
		findViewById(R.id.finalsubmit).getBackground().setAlpha(0);
		reqIntent = getIntent();
		Uri imgUri = reqIntent.getData();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 4;
		Bitmap bm = BitmapFactory.decodeFile(imgUri.getPath(), opt);
		ImageView fimg = (ImageView)findViewById(R.id.finaldataimage);
		fimg.setImageBitmap(bm);
	}
	
	public void submit(View v){
		
		EditText code = (EditText) findViewById(R.id.imagecode);
		EditText name = (EditText) findViewById(R.id.imagename);
		String codeString = code.getText().toString();
		String nameString = name.getText().toString();
		if(codeString.equals("")||nameString.equals("")){
			Toast.makeText(this, "ÇëÌîÐ´ÄÚÈÝ!", Toast.LENGTH_SHORT).show();
		}
		Uri imgUri = reqIntent.getData();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 6;
		Bitmap bitmap=BitmapFactory.decodeFile(imgUri.getPath(),opt);
		ArrayList<String> uris = reqIntent.getStringArrayListExtra("Uris");
		long zPercent = 0;
		try {
			zPercent = ImageUtil.getGrayPercent(bitmap, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, ContentBody> pairMap = new HashMap<String, ContentBody>();
		pairMap.put("full_src", new FileBody(new File(imgUri.getPath()),"image/jpeg"));
		try {
			pairMap.put("name", new StringBody(codeString));//±àºÅ´æÔÚname×Ö¶Î
			pairMap.put("code", new StringBody(nameString));//Ãû³Æ´æÔÚcode×Ö¶Î
			pairMap.put("base_score", new StringBody(String.valueOf(zPercent)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		new Thread(new HttpThread(pairMap)).start();
		
	}
	
	private class HttpThread implements Runnable{
		List<NameValuePair> pairList;
		Map<String, ContentBody> pairMap;
		
		public HttpThread(Map<String, ContentBody> pairMap) {
			super();
			this.pairMap = pairMap;
		}

		@Override
		public void run() {
			
			try {
				boolean isSuccess = Threads.setRegion(AllPath.Get(), this.pairMap);
				Looper looper = Looper.getMainLooper();
				Handler handler = new showHandler(looper);
				Message msg = Message.obtain();
				if(isSuccess){
					msg.what=1;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private class showHandler extends Handler{

		public showHandler() {
			super();
		}

		public showHandler(Callback callback) {
			super(callback);
		}

		public showHandler(Looper looper, Callback callback) {
			super(looper, callback);
		}

		public showHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				Toast.makeText(FinalPicture.this, "ÒÑÍê³É", Toast.LENGTH_LONG).show();
				
				FinalPicture.this.finish();
			}
		}
	}

}
