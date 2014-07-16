package com.xunlu.lizhenshezhi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ImageView;

import com.m.adpter.gridAdpter;

public class XianshiActivity extends Activity {
   private GridView g;
   private static List<SoftReference<Bitmap>> bitMapList;
   private ImageView imageView;
   private static SoftReference<Bitmap> srf = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xianshi);
		g = (GridView)findViewById(R.id.xianshiGriView);
		imageView = (ImageView)findViewById(R.id.xianshiss);
		File file = new File(Environment.getExternalStorageDirectory(),
				"/myCamera/pics");
		File[] l = file.listFiles();
		 bitMapList = new ArrayList<SoftReference<Bitmap>>();
		for (int i = 0; i < l.length; i++) {

			if (l[i].isFile()) {
				Log.e("ddddd",
						l[i].getName()
								.substring(l[i].getName().length() - 4,
										l[i].getName().length()).equals(".jpg")
								+ "");
				InputStream in;
				try {
					
					in = new FileInputStream(l[i]);
					Bitmap bitmap = BitmapFactory.decodeStream(in);
				    Log.e("bite", bitmap+"");
				    Bitmap newBitmap = zoomBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);  
			                 // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常   
			        bitmap.recycle();
					imageView.setImageBitmap(newBitmap);
					Log.e(" bit1", newBitmap+"");

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap bit = BitmapFactory.decodeFile(l[i].getPath());
			}
		}
			Log.e(" bit1", bitMapList.get(0) +"#2");
			gridAdpter ga = new gridAdpter();
			ga.setCon(getApplicationContext());
			ga.setLb(bitMapList);
			g.setAdapter(ga);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.xianshi, menu);
		return true;
	}
	
	 public static Bitmap zoomBitmap(Bitmap bitmap,int width,int height){
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scalewidth = (float)width/w;
			float scaleheight =(float) height/h;
			matrix.postScale(scalewidth, scaleheight);
			Bitmap newbit = Bitmap.createBitmap(bitmap, 0,0, w, h,matrix,true);
			srf = new SoftReference<Bitmap>(newbit);
			bitMapList.add(srf);
			srf = null;
			return newbit;
			
		}
}
