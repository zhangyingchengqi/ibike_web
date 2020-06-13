package com.yc.projects.yc74ibike.bean;

import java.io.Serializable;

public class Bike implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long bid;
	private int status;
	private String qrcode;
	private Double latitude;
	private Double longitude;

	/**
	 *  0未启用  
	 */
	public static final int UNACTIVE = 0;
	/**
	 * 1启用但未解锁  
	 */
	public static final int LOCK = 1;
	/**
	 * 2. 开锁使用中  
	 */
	public static final int USING = 2;
	/**
	 *  3.报修
	 */
	public static final int INTROUBLE = 3;

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Bike [bid=" + bid + ", status=" + status + ", qrcode=" + qrcode + ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
