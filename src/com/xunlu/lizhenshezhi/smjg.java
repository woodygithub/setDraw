package com.xunlu.lizhenshezhi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.util.ImageUtil;

public class smjg extends Activity {
	private ImageView iv;
	private ImageView ij;
	private Intent it;
	private TextView t2;
	private ImageView ge;
	private ImageView shi;
	private ImageView bai;
	private int fens;

	// 扫描结果
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smjg);
		iv = (ImageView) findViewById(R.id.iv_smjg);
//		ij = (ImageView) findViewById(R.id.GetJiang);
		bai = (ImageView) findViewById(R.id.bai);
		shi = (ImageView) findViewById(R.id.shi);
		ge = (ImageView) findViewById(R.id.ge);
//		ij.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				// 打印奖状
//			}
//		});
		it = getIntent();
		Uri img = it.getData();
		String fen =it.getStringExtra("score");
		String guli = it.getStringExtra("txt");
		Log.e("fendcdf", fen.length() + "");
		fens = Integer.parseInt(fen);
		Log.e("fens", fens + "");
		// -------百位
		if (fens == 100) {
			ge.setVisibility(View.GONE);
			shi.setVisibility(View.GONE);
			bai.setImageResource(R.drawable.onedoublezero);
		} else if (fens / 10 > 0) {
			int s1 = fens % 10;// ge
			int s2 = fens / 10;

			// ---------十位
			bai.setVisibility(View.GONE);
			switch (s2) {
			case 1:
				shi.setImageResource(R.drawable.ones);
				break;
			case 2:
				shi.setImageResource(R.drawable.two);
				break;
			case 3:
				shi.setImageResource(R.drawable.three);
				break;
			case 4:
				shi.setImageResource(R.drawable.fore);
				break;
			case 5:
				shi.setImageResource(R.drawable.five);
				break;
			case 6:
				shi.setImageResource(R.drawable.six);
				break;
			case 7:
				shi.setImageResource(R.drawable.senver);
				break;
			case 8:
				shi.setImageResource(R.drawable.eigth);
				break;
			case 9:
				shi.setImageResource(R.drawable.none);
				break;

			default:
				break;
			}

			switch (s1) {
			case 0:
				ge.setImageResource(R.drawable.o);
				break;
			case 1:
				ge.setImageResource(R.drawable.ones);
				break;
			case 2:
				ge.setImageResource(R.drawable.two);
				break;
			case 3:
				ge.setImageResource(R.drawable.three);
				break;
			case 4:
				ge.setImageResource(R.drawable.fore);
				break;
			case 5:
				ge.setImageResource(R.drawable.five);
				break;
			case 6:
				ge.setImageResource(R.drawable.six);
				break;
			case 7:
				ge.setImageResource(R.drawable.senver);
				break;
			case 8:
				ge.setImageResource(R.drawable.eigth);
				break;
			case 9:
				ge.setImageResource(R.drawable.none);
				break;
			default:
				break;
			}
		}
		// ------gewei
		// if(s.length>0&&s.length<2){//=只有一位
		// shi.setVisibility(View.GONE);
		// bai.setVisibility(View.GONE);
		// switch (s[0]) {
		// case 0:
		// ge.setBackgroundResource(R.drawable.o);
		// break;
		// case 1:
		// ge.setBackgroundResource(R.drawable.ones);
		// break;
		// case 2:
		// ge.setBackgroundResource(R.drawable.two);
		// break;
		// case 3:
		// ge.setBackgroundResource(R.drawable.three);
		// break;
		// case 4:
		// ge.setBackgroundResource(R.drawable.fore);
		// break;
		// case 5:
		// ge.setBackgroundResource(R.drawable.five);
		// break;
		// case 6:
		// ge.setBackgroundResource(R.drawable.six);
		// break;
		// case 7:
		// ge.setBackgroundResource(R.drawable.senver);
		// break;
		// case 8:
		// ge.setBackgroundResource(R.drawable.eigth);
		// break;
		// case 9:
		// shi.setBackgroundResource(R.drawable.none);
		// break;
		// default:
		// break;
		// }

		if(img!=null){
			String imgString =img.getPath();
			Bitmap bm = BitmapFactory.decodeFile(imgString);
			bm = ImageUtil.zoomBitmap(bm, bm.getWidth()/5, bm.getHeight()/5);
			iv.setImageBitmap(bm);
		}
		t2 = (TextView) findViewById(R.id.guli);
		t2.setText(guli);
	}

	public void backhome(View v) {
		// 点击右上角返回主页面的方法；
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	// 点击打印奖状的按钮
	public void printing(View v) {

	}
}
