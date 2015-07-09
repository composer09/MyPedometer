package kr.co.composer.pedometer.bo.location;

public class Health {
	private int rowId;
	
	// 운동한 사용자 ID
	private long userId;
	
	// 운동 시작 시간
	private long startTime;
	
	// 운동 종료 시간
	private long endTime;
	
	// 소모한 칼로리
	private int calory;
	
	// 운동 시간
	private int time;
	
	// 이동 거리 (유효)
	private float validDistance;
	
	// 이동 거리 (비유효)
	private float invalidDistance;
	
	// 측정된 point
	private int point;
	
	private String gpsFileName;
	
	// 운동정보 업로드 성공여부
	private boolean uploadSuccess;
	
	// 기부 성공여부
	private boolean donationSuccess;
	
	public int getRowId() {
		return rowId;
	}
	
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public int getCalory() {
		return calory;
	}
	
	public void setCalory(int calory) {
		this.calory = calory;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}

	public float getValidDistance() {
		return validDistance;
	}

	public void setValidDistance(float validDistance) {
		this.validDistance = validDistance;
	}

	public float getInvalidDistance() {
		return invalidDistance;
	}

	public void setInvalidDistance(float invalidDistance) {
		this.invalidDistance = invalidDistance;
	}
	
	public float getTotalDistance() {
		return this.getValidDistance() + this.getInvalidDistance();
	}
	
	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public boolean isUploadSuccess() {
		return uploadSuccess;
	}

	public void setUploadSuccess(boolean uploadSuccess) {
		this.uploadSuccess = uploadSuccess;
	}

	public boolean isDonationSuccess() {
		return donationSuccess;
	}

	public void setDonationSuccess(boolean donationSuccess) {
		this.donationSuccess = donationSuccess;
	}
	
	public String getGpsFileName() {
		return gpsFileName;
	}

	public void setGpsFileName(String locationFile) {
		this.gpsFileName = locationFile;
	}

}
