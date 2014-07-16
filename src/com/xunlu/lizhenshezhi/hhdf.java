package com.xunlu.lizhenshezhi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.date.ListMap;
import com.google.zxing.client.android.CaptureActivity;


public class hhdf extends Activity {
    private View layout;
    private ImageView paizhao;
    private Boolean flag =true;
    private static final int SCANNIN_GREQUEST_CODE = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        paizhao = (ImageView)findViewById(R.id.paizhao);
        AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
        
        LayoutInflater inflater = getLayoutInflater();
        View npd = inflater.inflate(R.layout.name_phon_dialog,(ViewGroup) findViewById(R.id.name_phon_dialog));
        
        aDialog.setTitle("请输入");
        aDialog.setIcon(android.R.drawable.ic_dialog_info);
        aDialog.setView(npd);
        aDialog.setPositiveButton("确定", null);
        aDialog.setNegativeButton("取消", null);
        aDialog.show();
    }

    public void paizhaoClick(View v) {
		new GetErwei().run();
	}
    
    
    /**
     * 返回
     * @param v
     */
    public void backhome(View v) {
		finish();
	}
    
    /**
     * 子线程开启预览
     * @author android
     *
     */
    class GetErwei implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(flag){
				Log.i("saomiao", "dddddddddd---=== GetErWeima() ");
				//Intent intent = new Intent(hhdf.this, MipcaActivityCapture.class);
				Intent intent = new Intent(hhdf.this, CaptureActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, hhdf.SCANNIN_GREQUEST_CODE);
				ListMap.setJ(0);
				flag = false;
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(hhdf.SCANNIN_GREQUEST_CODE==requestCode && resultCode==Activity.RESULT_OK)
		{
			Intent it = new Intent(hhdf.this, XianShiDafen.class);
			it.putExtras(data.getExtras());
            it.setData(data.getData());
			startActivity(it);
		}
		flag = true;
	}
   
}