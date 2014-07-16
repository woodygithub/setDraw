package com.xunlu.lizhenshezhi;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.AudioManager;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.date.ListMap;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback,
		Camera.PictureCallback {
	private SurfaceHolder holder;
	private Camera camera;
	private Camera.Parameters parameters;
	private Activity act;
	private Handler handler = new Handler();
	private Handler handler1 = new Handler();
	private Context context;
	private SurfaceView surfaceView;
	private AudioManager audio;
	private int current;
	private CameraView cameraView;
	// int i = 0;
	private ImageView ivc;
	private LinearLayout ly;
	
	// zf start
	private static String TAG = "CameraView";

	// zf end
	public CameraView(Context context, Camera cam) {
		super(context);
		camera = cam;
		surfaceView = this;
		surfaceView.setVisibility(View.INVISIBLE);
		audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		final int current = audio.getRingerMode();
		audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		this.context = context;
		holder = getHolder();// ����Surface Holder
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// ָ��Push Buffer

	}

	public CameraView(Context context, Activity act, ImageView ivc,
			LinearLayout ly, Camera camera) {// �ڴ˶���һ�����췽���������չ����CameraActivity��finish��
		this(context, camera);
		this.act = act;
		this.ivc = ivc;
		this.ly = ly;
	}
	@Override
	public void surfaceCreated(final SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.v("timer", "surfaceCreated");
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.v("timer", "surfaceChanged");
		Log.v(TAG, "surfaceChanged");
		if (holder.getSurface() == null) {
			// preview surface does not exist
			return;
		}
		try {
			camera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}
		try {
			Log.d(TAG, holder + "" + camera);
			camera.setPreviewDisplay(holder);
			camera.startPreview();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (camera != null) {
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

	public void onPictureTaken(byte[] data, Camera camera) {// ������ɺ󱣴���Ƭ

		try {

			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat formats = new SimpleDateFormat("yyyyMMddHHmmss");
			String time = formats.format(date);
			// ��SD���ϴ����ļ���
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/myCamera/pics");
			Log.e("FFFF", file.getPath() + "GGGGGG");
			if (!file.exists()) {
				file.mkdirs();
			}
			String path = Environment.getExternalStorageDirectory()
					+ "/myCamera/pics/" + time + ".jpg";
			// if (data != null) {

			ListMap.setI(-1);
			Log.e("sssss", path + "");
			data2file(data, path);// �浽ID��
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();// �ͷ�
			camera = null;
			ListMap.setJ(-1);
			holder.removeCallback(CameraView.this);
			audio.setRingerMode(current);

			// Thread.sleep(100);
			act.finish();
		} catch (Exception e) {

		} finally {
		}
	}

	/**
	 * ��Ϊ��ֹ���գ��ͷ����������һ�δ�
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();// �ͷ�
			camera = null;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 *  ������������ת��Ϊ�ļ��ĺ���
	 * @param w ����������
	 * @param fileName �ļ�·��
	 * @throws Exception
	 */
	private void data2file(byte[] w, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(fileName);
			out.write(w);
			Bitmap bitmap = BitmapFactory.decodeByteArray(w, 0, w.length);
			ListMap.setImgsize(bitmap.getByteCount());
			ListMap.getLb().put(ListMap.getIndex(), bitmap);
			out.close();
		} catch (Exception e) {
			if (out != null)
				out.close();
			throw e;
		}
	}

	// private void uploadFile()// ���չ����ϴ��ļ���������
	// {
	//
	// takephoto t = new takephoto();
	// t.GetErWeima();
	//
	// }
	class getphoto implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			camera.takePicture(null, null, new MyPictureCallback());
		}

	}

	private final class MyPictureCallback implements PictureCallback {
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				File jpgFile = new File(
						Environment.getExternalStorageDirectory(),
						System.currentTimeMillis() + ".jpg");
				Log.e("FFFFFF", jpgFile.getPath() + "****************");
				FileOutputStream outStream = new FileOutputStream(jpgFile);
				outStream.write(data);
				outStream.close();
				camera.startPreview();
				// act.finish();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}