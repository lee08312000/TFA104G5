package com.productReport.model;
import java.io.Serializable;
import java.sql.Timestamp;

// 商品檢舉(product_report)
public class ProductReportVO implements Serializable {

	private Integer productReportId; // 商品檢舉流水號
	private Integer productId; // 商品流水號
	private Integer memberId; // 會員流水號
	private Timestamp reportTime; // 檢舉時間
	private String reportReason; // 檢舉原因
	private Integer reportStatus; // 檢舉處理狀態
	
	
	
	public ProductReportVO() {
		super();
	}

	public ProductReportVO(Integer productReportId, Integer productId, Integer memberId, Timestamp reportTime,
			String reportReason, Integer reportStatus) {
		super();
		this.productReportId = productReportId;
		this.productId = productId;
		this.memberId = memberId;
		this.reportTime = reportTime;
		this.reportReason = reportReason;
		this.reportStatus = reportStatus;
	}
	
	public Integer getProductReportId() {
		return productReportId;
	}
	public void setProductReportId(Integer productReportId) {
		this.productReportId = productReportId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Timestamp getReportTime() {
		return reportTime;
	}
	public void setReportTime(Timestamp reportTime) {
		this.reportTime = reportTime;
	}
	public String getReportReason() {
		return reportReason;
	}
	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	
}
