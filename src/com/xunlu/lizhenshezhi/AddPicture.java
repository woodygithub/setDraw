package com.xunlu.lizhenshezhi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.conn.path.AllPath;
import com.google.zxing.client.android.CaptureActivity;
import com.thread.m.Threads;
import com.util.ImageUtil;

public class AddPicture extends Activity{

	private final static String TAG = "AddPicture";
	private SurfaceView preview;
	private Camera camera;
	public boolean ispreview;
	SharedPreferences spf;
	SharedPreferences.Editor spfEditer;
	private String picture;
	long zPercent;
	public static int CAMERA_Z_CODE = 1;
	public static int CAMERA_F_CODE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addpicture);
		preview = (SurfaceView) findViewById(R.id.surepreview);
		SurfaceHolder holder = preview.getHolder();
		holder.addCallback(new AddPicture.SurfaceHolderCallback());
		//preview.setZOrderOnTop(false);
		holder.setFormat(PixelFormat.TRANSPARENT);
		//holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if(camera != null) {  
            /* 若摄像头正在工作，先停止它 */  
            if(ispreview) {  
                camera.stopPreview();
                ispreview = false;
            }
            //如果注册了此回调，在release之前调用，否则release之后还回调，crash
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
	}

	public void begin(View v) {
		camera.takePicture(null, null, pictureCallback); // picture
	}
	
	private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {  
        //@Override  
        public void onPictureTaken(byte[] data, Camera camera) {  
        	String path = initPath();
        	try {
				data2file(data, path);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	Looper looper = Looper.getMainLooper();
        	Handler hanlder = new Handler(looper){
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					Intent intent = (Intent) msg.obj;
					
					//开启照相扫码功能
					startActivityForResult(intent, AddPicture.CAMERA_Z_CODE);
				}
        		
        	};
        	Message msg = Message.obtain();
        	Intent intent = new Intent(AddPicture.this, ConfirmPicture.class);
			intent.setData(Uri.parse(path));
        	msg.obj = intent;
        	hanlder.sendMessage(msg);
            camera.startPreview();  
        }  
    };
    
 
    
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode==Activity.RESULT_OK)
		{
			
		}
	}
	
	
	
	private void init(SurfaceHolder holder){
		
		camera = Camera.open();
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Camera.Parameters params = camera.getParameters();
		List<Camera.Size> sizes = params.getSupportedPreviewSizes();
		Camera.Size selected = sizes.get(0);
		params.setPreviewSize(selected.width, selected.height);
		camera.setParameters(params);
		camera.startPreview();
		ispreview = true;
	}
	
	public class SurfaceHolderCallback implements SurfaceHolder.Callback{

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			init(holder);
			
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			
			if(camera != null) {  
                /* 若摄像头正在工作，先停止它 */  
                if(ispreview) {  
                    camera.stopPreview();
                    ispreview = false;
                }
                //如果注册了此回调，在release之前调用，否则release之后还回调，crash
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            }
		}
		
	}
	
	public String initPath(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		// 在SD卡上创建文件夹
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/myCamera/pics");
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = file.getPath()+ "/" + time + ".jpg";
		return path;
	}
    /**
     * 保存入磁盘
     * @param data
     * @param path
     * @throws Exception
     */
    public void data2file(byte[] data, String path) throws Exception {
    	    FileOutputStream out = null;
			out = new FileOutputStream(path);
			out.write(data);
			out.close();
	}

}
