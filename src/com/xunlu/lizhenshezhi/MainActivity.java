package com.xunlu.lizhenshezhi;

import java.io.ByteArrayOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.Browser;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.date.CreateSQDate;
import com.date.ListMap;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	private ImageView hhdf, jfcx;
	private ImageView sz;
	private SQLiteDatabase db = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int Witch = dm.widthPixels;
		int higth = dm.heightPixels;
		ListMap.setWindowHigth(higth);
		ListMap.setWindowWigth(Witch);
		CreateSQDate mb = new CreateSQDate(getApplicationContext(), "2s", null, 1);
		db = mb.getWritableDatabase();// ���ӿ�
		hhdf = (ImageView) findViewById(R.id.hhdf);
		jfcx = (ImageView) findViewById(R.id.jfcx);
		sz = (ImageView) findViewById(R.id.sz);

	}

	// �滭��ֵ���ת
	public void hhdf(View v) {
		Intent intent = new Intent(this, hhdf.class);
		startActivity(intent);
	}

	// ����ѡ�����ҳ
	public void sz(View v) {
		Intent intent = new Intent(this, ShezhiActivity.class);
		
		startActivity(intent);
	}

	// ���ֲ�ѯ����ת
	public void jfcx(View v) {
		Intent intent = new Intent(this, jfcx.class);
		startActivity(intent);

	}

	/**
	 * 
	 * �ж�״̬���Ƿ���ʾ
	 */

	public static boolean isSystemBarVisible(final Activity context) {

		int flag = context.getWindow().getDecorView().getSystemUiVisibility();

		// return (flag & View.SYSTEM_UI_FLAG_SHOW_FULLSCREEN) != 0;

		return (flag & 0x8) == 0;

	}

	/**
	 * 
	 * ����ϵͳ���ɼ���
	 */

	public static void setSystemBarVisible(final Activity context,
			boolean visible) {

		int flag = context.getWindow().getDecorView().getSystemUiVisibility();
		// ��ȡ��ǰSystemUI��ʾ״̬

		// int fullScreen = View.SYSTEM_UI_FLAG_SHOW_FULLSCREEN;

		int fullScreen = 0x8; // 4.1
								// View.java��Դ���������صĳ���SYSTEM_UI_FLAG_SHOW_FULLSCREEN����ʵEclipse����Ҳ���Ե���ϵͳ���ؽӿڣ�������ȡ��android.jar������Ͳ����ˡ�
		if (visible) { // ��ʾϵͳ��
			if ((flag & fullScreen) != 0) { // flag��־λ���Ѿ�ӵ��ȫ����־SYSTEM_UI_FLAG_SHOW_FULLSCREEN

				context.getWindow().getDecorView()
						.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
				// ��ʾϵͳ��
			}
		} else { // ����ϵͳ��

			if ((flag & fullScreen) == 0) { // flag��־λ�в�����ȫ����־SYSTEM_UI_FLAG_SHOW_FULLSCREEN

				context.getWindow().getDecorView()
						.setSystemUiVisibility(flag | fullScreen);
			}
		}
	}
	//ͼƬת��
	public void AddDate(Bitmap icon){
		if (icon == null) {  
            return;  
       }  
         // ����ͼ��Ҫ���浽��������ڲ����ݿ��У�ϵͳ���������ΪSQLite��ʽ��BrowserҲ�����⣬��ΪͼƬ�Ƕ����Ƶ�����ʹ���ֽ�����洢���ݿ��  
         // BLOB����  
        final ByteArrayOutputStream os = new ByteArrayOutputStream();  
         // ��Bitmapѹ����PNG���룬����Ϊ100%�洢          
         icon.compress(Bitmap.CompressFormat.PNG, 100, os);   
         // ����SQLite��Content��������Ҳ����ʹ��raw  
         ContentValues values = new ContentValues();   
         // д�����ݿ��Browser.BookmarkColumns.TOUCH_ICON�ֶ�  
         values.put(Browser.BookmarkColumns._COUNT, os.toByteArray());           
//           DBUtil.update(....);//���ø��»��߲��뵽���ݿ�ķ���
	}
/**	
	String []columns = {}; 
	Cursor or = db.query("majors", columns, null, null, null, null, null);
	while(or.moveToNext()){
		String name = or.getString(0);
		Log.e("dddd", or.getString(0));
		int id = or.getInt(1);
		Log.e("dddd", or.getInt(1)+"");
		Major m = new Major();
		m.setId(id);
		m.setMojorName(name);
		lm.add(m);
		Log.e("list", lm.size()+"");
	}
	
	L1Adpter l = new L1Adpter();
	l.setCon(getApplicationContext());
	l.setLm(lm);
	lv.setAdapter(l);	
}
**/

}
