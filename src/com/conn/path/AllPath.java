package com.conn.path;

public class AllPath {
	private  static String getFuadress = "http://draw.ideer.cn/api.php" ;
	
	
	public static String getGetFuadress() {
		return getFuadress;
	}
	public static void setGetFuadress(String getFuadress) {
		AllPath.getFuadress = getFuadress;
	}
	public static String Get(){
		String path = getFuadress+ "/Api/Pic/pic/";
		return path;
	}

}
