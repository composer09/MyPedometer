package kr.co.composer.pedometer.util;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class PathUtil {
	public static String getRootDir(Context context) {
		String root = "";
		if (isAvailableExternalMemory()) {
        	root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myPedometer";

			File file = new File(root);
			if (!file.exists()) {
				file.mkdir();
			}
		} else {
			root = context.getFilesDir().getAbsolutePath();
		}

		return root;
	}
	
	public static String getTempRootDir(Context context) {
		String root = "";
		if (isAvailableExternalMemory()) {
			root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/" + context.getPackageName();

			mkdirIfNotExists(root);
		} else {
			root = context.getFilesDir().getAbsolutePath();
		}

		return root;
	}
	
	public static boolean isAvailableExternalMemory()
    {
        String state = Environment.getExternalStorageState();
        
        return Environment.MEDIA_MOUNTED.equals(state) && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) == false;
    }

	public static String getTempImageDir(Context context) {
		String tempDir = getTempRootDir(context) + "/images";

		mkdirIfNotExists(tempDir);
		
		return tempDir;
	}


	private static void mkdirIfNotExists(String tempDir) {
		File file = new File(tempDir);

		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String getExternalImageFilePath(Context context, String filename) {
		return getTempImageDir(context) + "/" + filename; 
	}
	
	public static Uri getExternalImageFileUri(Context context, String filename) {
		return Uri.fromFile(new File(getExternalImageFilePath(context, filename))); 
	}

	public static String getImageRootDir(Context context) {
		String imageRootDir = getRootDir(context) + "/gallery/bigwalk";
		File file = new File(imageRootDir);

		if (!file.exists()) {
			file.mkdirs();
		}

		return imageRootDir;
	}
}
