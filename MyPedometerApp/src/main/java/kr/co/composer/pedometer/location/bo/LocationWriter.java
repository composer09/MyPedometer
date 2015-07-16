//package kr.co.composer.pedometer.location.bo;
//
//import android.location.Location;
//import android.util.Log;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.Date;
//
//import kr.co.bigwalk.HealthApplication;
//import kr.co.bigwalk.bo.crypto.CryptoVersion;
//import kr.co.bigwalk.bo.crypto.TextCrypto;
//import kr.co.bigwalk.date.DateUtils;
//import kr.co.bigwalk.pref.UserPreferenceManager;
//import kr.co.bigwalk.util.PathUtil;
//import kr.co.composer.pedometer.application.PedometerApplication;
//import kr.co.composer.pedometer.util.PathUtil;
//
//public class LocationWriter {
//	private static final String EXTERNAL_ROOT = "bigwalk";
//	private static final String TAG = LocationWriter.class.getName();
//	private static final String FILE_PATH = "%d_%s";
//	private static final String LOCATION_DELIMITER = "\n";
//	private String fileName;
//	private PrintWriter writer;
//	private CryptoVersion version = CryptoVersion.VER1;
//	private TextCrypto textCrypto = version.getTextCrypto();
//
//	public LocationWriter(PedometerApplication application) throws IOException {
//		String rootDir = PathUtil.getRootDir(application);
//		fileName = rootDir + "/" + getFileName(UserPreferenceManager.getInstance().getUserId());
//		Log.d(TAG, "gps filename : " + fileName);
//
////		FileOutputStream fos = new FileOutputStream(fileName);
//		writer = new PrintWriter(new FileWriter(fileName));
//
//		writer.write(version.getCode() + LOCATION_DELIMITER);
//	}
//
//	public LocationWriter(HealthApplication application, String filename) throws IOException {
//		this.fileName = filename;
//		writer = new PrintWriter(new FileWriter(fileName, true));
//	}
//
//	public void write(Location location) {
//		String locationString = location.getLongitude()
//				+ LocationFileRow.DELIMETER + location.getLatitude()
//				+ LocationFileRow.DELIMETER + location.getAltitude();
//		String encryptedLocationString = textCrypto.encrypt(locationString);
//		writer.write(encryptedLocationString + LOCATION_DELIMITER);
//	}
//
//	public void flush() {
//		writer.flush();
//	}
//
//	public void close() {
//		writer.close();
//	}
//
//	private String getFileName(long userId) {
//		String now = DateUtils.format(new Date(), "yyyyMMddHHmmss");
//
//		return String.format(FILE_PATH, userId, now);
//	}
//
//	public String getFileName() {
//		return this.fileName;
//	}
//}
