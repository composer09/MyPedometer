package kr.co.composer.pedometer.dao;

import android.net.Uri;


public class ContentProviderUri {
	private static final String FORMAT = "content://%s/%s";
	
	public static Uri pedometer(String uriAuthority) {
		return Uri.parse(String.format(FORMAT, uriAuthority, "pedometer"));
	}
}
