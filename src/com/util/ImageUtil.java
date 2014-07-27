package com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;

import com.date.ListMap;

public class ImageUtil {
/**
	 * ѹ��ͼƬ
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap,int width,int height){
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scalewidth =(float) width/w;
		float scaleheight =(float) height/h;
		matrix.postScale(scalewidth, scaleheight);
		Bitmap newbit = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	    ListMap.getLb().put(ListMap.getIndex(), newbit);
		return newbit;
	}
	
	/** 
	 * ͼƬת�Ҷ� 
	 * @param bmSrc 
	 * @return 
	 * @throws Exception 
	 */  
	public static Bitmap bitmap2Gray(Bitmap bmSrc) throws Exception  
	{  
		if (bmSrc==null){
			return null;
		}
	    int w=bmSrc.getWidth(), h=bmSrc.getHeight();
	    int[] pix = new int[w * h];
	    bmSrc.getPixels(pix, 0, w, 0, 0, w, h);

	    int alpha=0xFF<<24;
	    Bitmap result= bmSrc.copy(Bitmap.Config.ARGB_8888, true);
	    for (int i = 0; i < h; i++) {
		    for (int j = 0; j < w; j++) {
			    // ������ص���ɫ
			    int color = bmSrc.getPixel(j, i);
			    int red = Color.red(color); //((color & 0x00FF0000) >> 16);
			    int green = Color.green(color); //((color & 0x0000FF00) >> 8);
			    int blue = Color.blue(color); //color & 0x000000FF;
			    int gray = (int)((float)red*0.3+(float)green*0.59+(float)blue*0.11);
			    gray = ((float)gray*1.4f) > 255f ? 255 : (int)((float)gray*1.4f);
			    int newcolor = alpha | (gray << 16) | (gray << 8) | gray;
			    result.setPixel(j, i, newcolor);
		    }
	    }
	    String path = initPath();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();    
	    result.compress(Bitmap.CompressFormat.PNG, 100, baos);
	    //data2file(baos.toByteArray(),path);
	    return result;
	}
	
	public static long getGrayPercent(Bitmap bm) throws Exception{
		return getPixCount(bm);
	}
	/**
	 * ��ȡͼ�����������ۼ�ֵ
	 * @return percent
	 * @throws Exception 
	 */
	public static long getPixCount(Bitmap bm) throws Exception{
		Bitmap grayBm = ImageUtil.bitmap2Gray(bm);
		long gotPoint = 0;
		long percent = 0;
		if (grayBm!=null)
		{
			int h = grayBm.getHeight();
			int w = grayBm.getWidth();
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int color = grayBm.getPixel(x, y);
					int r = Color.red(color);
					int g = Color.green(color);
					int b = Color.blue(color);
					gotPoint+=r;
				}
			}
			percent = gotPoint ;
		}
		return percent;
	}
	/**�����յõ���ͼ��Ķ���Ĳ��ָ�ȥ */
	public static Bitmap filterBitmap(Bitmap in){
		int inWidth=in.getWidth(),inHeight=in.getHeight();
		Bitmap filter = Bitmap.createBitmap(in, inWidth*3/16, inHeight * 9 /160, inWidth*11/16, inHeight * 38 /60);
		in.recycle();
		return filter;
	}
	/**
	 * ����ͼ��ĻҶȷ���ֵ������������׻򴿺ڻ�һɫ�ķ������
	 * @return ������60-100��
	 */
	public static int gradeBitmap(Bitmap filter){
//		int inWidth=in.getWidth(),inHeight=in.getHeight();
//		Bitmap filter = Bitmap.createBitmap(in, inWidth*3/16, inHeight * 3 /80, inWidth*8/16, inHeight * 16 /30);
		//�Ƚ�ͼ��2x2�и�õ������ÿһ����з�����㣬�ٶ�ֵȡƽ�����ǽ��
		//�������Ա����е�ͼ���������ȫͿ��ʣ�����׶���ø߷�
		int xSize=2,ySize=2;
		int widthUnit=filter.getWidth()/xSize;
		int heightUnit=filter.getHeight()/ySize;
		ArrayList<Bitmap> cutedList=new ArrayList<Bitmap>();
		for(int x=0 ; x<xSize ; x++){
			for(int y=0 ; y<ySize ; y++){
				Bitmap bitmap = Bitmap.createBitmap(filter, x*widthUnit, y*heightUnit, widthUnit, heightUnit);
				cutedList.add(bitmap);
			}
		}
		int scoreSum = 0;
		for(Bitmap cuted:cutedList){
			scoreSum+=gradeBitmapReal(cuted);
			cuted.recycle();
		}
//		try {
//			File outputFile = new File(initPath().replace("gray.jpg", "filter.jpg"));
//			filter.compress(CompressFormat.JPEG, 80, new FileOutputStream(outputFile));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
		filter.recycle();
		return scoreSum/cutedList.size();
	}
	/**
	 * ����ͼ��ĻҶȷ���ֵ������������׻򴿺ڻ�һɫ�ķ������
	 * @return ������60-100��
	 */
	private static int gradeBitmapReal(Bitmap cuted){
		int w=cuted.getWidth(), h=cuted.getHeight();
		int n= w*h;
		long sum = 0;//�Ҷȵĺ�
		long squareSum = 0;//�Ҷȵ�ƽ���ĺ�
		
	    for (int i = 0; i < h; i++) {
		    for (int j = 0; j < w; j++) {
			    // ������ص���ɫ
			    int color = cuted.getPixel(j, i);
			    int red = Color.red(color); //((color & 0x00FF0000) >> 16);
			    int green = Color.green(color); //((color & 0x0000FF00) >> 8);
			    int blue = Color.blue(color); //color & 0x000000FF;
			    int gray = (red*30+green*59+blue*11)/100;//����float��ԭ��int�����������
//			    gray = ((float)gray*1.4f) > 255f ? 255 : (int)((float)gray*1.4f);
			    gray = Math.min(100, gray);//��ɫ��100�ĻҶ�
//			    gray-=100;//��ɫ��100�ĻҶ�
//			    gray = Math.max(0, gray);
			    sum+=gray;
			    squareSum+=(gray*gray);
		    }
	    }
	    long average = sum/n ;
	    long squareAverage = squareSum/n ;
	    long dx = squareAverage - average * average;//����D��X)=E[X^2]-[E(x)]^2 "ƽ����ƽ����ȥƽ����ƽ��"
	    int maxDx = 50*50; //������󷽲һ���һ���ͼ������
	    maxDx/=2;//�������޻�׼�������������ø�һ��
//	    int minDx = 0;//������С����,��ɫͼƬ�����
	    
	    int score = (int) (dx * 40 / maxDx + 50);
//		Log.d("gradeBitmapReal", "score"+score+",dx="+dx+",squareAverage="+squareAverage+",average="+average);
	    if(score > 100) score = 100;
	    if(score <60) score =60;
	    return score;
	}
	
	public static String initPath(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = format.format(date);
		// ��SD���ϴ����ļ���
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/myCamera/pics/gray");
		if (!file.exists()) {
			file.mkdirs();
		}
		String path = file.getPath()+ "/" + time + "gray.jpg";
		return path;
	}
	
    /**
     * ���������
     * @param data
     * @param path
     * @throws Exception
     */
    public static void data2file(byte[] data, String path) throws Exception {
    	    FileOutputStream out = null;
			out = new FileOutputStream(path);
			out.write(data);
			out.close();
	}
	
}
