package com.xunlu.lizhenshezhi;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
public class takephoto extends Activity {
	private final static int SCANNIN_GREQUEST_CODE = 1;
	private static String TAG="takephoto";
	ImageView iv;
	Intent intent;
	private ImageView fly;
	private ImageView ivc;
	private CameraView view;
	private static ImageView img;
	public static ImageView getImg() {
		return img;
	}
	private Intent it = null;
	private LayoutInflater inflater;
	private LinearLayout ly ;
	private static final int CAMERA = 0;
	public  List<Bitmap> lmapt = new ArrayList<Bitmap>();
	 //zf start
	private Camera mCamera;
	private FrameLayout preview;
	private class AsyncTaskCamera extends AsyncTask<Integer, Integer, Camera>{
			@Override
			protected Camera doInBackground(Integer... params) {
				return getCameraInstance();
			}
			@Override
			protected void onPostExecute(Camera result) {
				mCamera=result;
				view = new CameraView(takephoto.this,result);
				Button b = new Button(getApplicationContext());
				b.setText("dddd");
				Log.v(TAG, "view "+view+"preview " +preview);
				preview.addView(view);	
				View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.camera_layout, null);
//				preview.addView(R.layout.camera_layout);
				preview.addView(v);
				super.onPostExecute(result);
			}
		}
		/** A safe way to get an instance of the Camera object. */
		public static Camera getCameraInstance() {
			Camera c = null;
			try {
				c = Camera.open(Camera.getNumberOfCameras()-1); // attempt to get a Camera instance
			} catch (Exception e) {
				e.printStackTrace();
				// Camera is not available (in use or does not exist)
			}
			return c; // returns null if camera is unavailable
		}
	 //zf end
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ���ú���ģʽ�Լ�ȫ��ģʽ
//		inflater = LayoutInflater.from(getApplicationContext());
//		View v = inflater.inflate(R.layout.camera_layout, null);
		this.setContentView(R.layout.camera_layout);
		img = (ImageView)findViewById(R.id.GetJiang);
		preview=(FrameLayout)this.findViewById(R.id.camera_preview);//zf
		Log.v(TAG, "---"+preview);
		new AsyncTaskCamera().execute(0);//zf
		 Log.e("�����", "����󡪡�����1");
		RelativeLayout relative = (RelativeLayout) this.findViewById(R.id.Rly);
		RelativeLayout.LayoutParams Layout = new RelativeLayout.LayoutParams(3,
				3);// ����surfaceviewʹ�����������޷��ۿ�Ԥ��
		Layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1);
		Layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1);
	}
	@Override
	   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   super.onActivityResult(requestCode, resultCode, data);
	    Log.e("�����", "����󡪡�����2");
	  //������ʱ��ת
	}
//	public void GetErWeima() {
//		// TODO Auto-generated method stub
//		Log.e("saomiao", "dddddddddd---=== GetErWeima() ");
//		Intent intent = new Intent(takephoto.this, MipcaActivityCapture.class);
//		Log.e("saomiao", "saosaosaosoaosaomiaomiaomiaomai---======i");
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
//	}
}
//tiaozhang
// ��ʾͼƬ

