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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
			Toast.makeText(this, "请填写内容!", Toast.LENGTH_SHORT).show();
		}
		Uri imgUri = reqIntent.getData();
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inSampleSize = 6;
		Bitmap bitmap=BitmapFactory.decodeFile(imgUri.getPath(),opt);
		ArrayList<String> uris = reqIntent.getStringArrayListExtra("Uris");
		long zPercent = 0;
		try {
			zPercent = ImageUtil.gradeBitmap(bitmap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, ContentBody> pairMap = new HashMap<String, ContentBody>();
		pairMap.put("full_src", new FileBody(new File(imgUri.getPath()),"image/jpeg"));
		try {
			pairMap.put("name", new StringBody(codeString));//编号存在name字段
			pairMap.put("code", new StringBody(nameString));//名称存在code字段
			pairMap.put("base_score", new StringBody(String.valueOf(zPercent)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		new Thread(new HttpThread(pairMap)).start();
		if(pd==null){
			pd=new ProgressDialog(this);
			pd.show();
		}
	}
	ProgressDialog pd;
	
	private class HttpThread implements Runnable{
		List<NameValuePair> pairList;
		Map<String, ContentBody> pairMap;
		
		public HttpThread(Map<String, ContentBody> pairMap) {
			super();
			this.pairMap = pairMap;
		}

		@Override
		public void run() {
			Looper looper = Looper.getMainLooper();
			Handler handler = new showHandler(looper);
			
			try {
				boolean isSuccess = Threads.setRegion(AllPath.Get(), this.pairMap);
				Message msg = Message.obtain();
				if(isSuccess){
					msg.what=1;
				}
				handler.sendMessage(msg);
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
			pd.dismiss();
			if(msg.what==1){
				new AlertDialog.Builder(FinalPicture.this).setTitle("提示")
						.setMessage("已完成")
						.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								startActivity(new Intent(FinalPicture.this, ShezhiActivity.class)
										.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
							}
						}).show();
			}else{
				Toast.makeText(FinalPicture.this, "上传失败", Toast.LENGTH_LONG).show();
			}
		}
	}

}
