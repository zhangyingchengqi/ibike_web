package com.yc.projects.yc74ibike.bean;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.data.annotation.Id;

import io.swagger.annotations.ApiModelProperty;

public class Bike implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "编号")
	private String bid;
	@ApiModelProperty(value = "状态: 0 未启用  1 启用且未开锁  2. 开锁使用中  3. 报修中  4. 骑行 ")
	private int status;
	@ApiModelProperty(value = "二维码")
	private String qrcode;
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	@ApiModelProperty(value = "经度")
	private Double longitude;

	@Id // 对应到mongo _id
	private String id;
	private Double[] loc = new Double[2];

	private String phoneNum;
	private String[] types;
	private String openid;

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double[] getLoc() {
		return loc;
	}

	public void setLoc(Double[] loc) {
		this.loc = loc;
	}

	/**
	 * 0未启用
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
	 * 3.报修
	 */
	public static final int INTROUBLE = 3;

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
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
		return "Bike [bid=" + bid + ", status=" + status + ", qrcode=" + qrcode + ", latitude=" + latitude + ", longitude=" + longitude + ", id=" + id + ", loc=" + Arrays.toString(loc) + ", phoneNum="
				+ phoneNum + ", types=" + Arrays.toString(types) + ", openid=" + openid + "]";
	}

}
