package com.blog.ssh.test.domain;

/**
 * OrderInfo entity. @author MyEclipse Persistence Tools
 */

public class OrderInfo implements java.io.Serializable {

	// Fields

	private Long id;
	private String orderNum;
	private Long userId;
	private Long doctorId;
	private String submitTime;
	private String bespokeDate;
	private String bespokeTime;
	private Long productId;
	private Double totalPrice;
	private String updateTime;
	private String orderStatus;
	private String comments;
	private Double splitRatio;
	private Long couponId;
	private Double usePointsAmount;
	private String payMethod;
	private Double useRemainBalance;
	private String remindInfo;
	private Long usePoints;
	private Double trafficSubsidyMoney;

	// Constructors

	/** default constructor */
	public OrderInfo() {
	}

	/** full constructor */
	public OrderInfo(String orderNum, Long userId, Long doctorId,
			String submitTime, String bespokeDate, String bespokeTime,
			Long productId, Double totalPrice, String updateTime,
			String orderStatus, String comments, Double splitRatio,
			Long couponId, Double usePointsAmount, String payMethod,
			Double useRemainBalance, String remindInfo, Long usePoints,
			Double trafficSubsidyMoney) {
		this.orderNum = orderNum;
		this.userId = userId;
		this.doctorId = doctorId;
		this.submitTime = submitTime;
		this.bespokeDate = bespokeDate;
		this.bespokeTime = bespokeTime;
		this.productId = productId;
		this.totalPrice = totalPrice;
		this.updateTime = updateTime;
		this.orderStatus = orderStatus;
		this.comments = comments;
		this.splitRatio = splitRatio;
		this.couponId = couponId;
		this.usePointsAmount = usePointsAmount;
		this.payMethod = payMethod;
		this.useRemainBalance = useRemainBalance;
		this.remindInfo = remindInfo;
		this.usePoints = usePoints;
		this.trafficSubsidyMoney = trafficSubsidyMoney;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public String getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getBespokeDate() {
		return this.bespokeDate;
	}

	public void setBespokeDate(String bespokeDate) {
		this.bespokeDate = bespokeDate;
	}

	public String getBespokeTime() {
		return this.bespokeTime;
	}

	public void setBespokeTime(String bespokeTime) {
		this.bespokeTime = bespokeTime;
	}

	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Double getSplitRatio() {
		return this.splitRatio;
	}

	public void setSplitRatio(Double splitRatio) {
		this.splitRatio = splitRatio;
	}

	public Long getCouponId() {
		return this.couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Double getUsePointsAmount() {
		return this.usePointsAmount;
	}

	public void setUsePointsAmount(Double usePointsAmount) {
		this.usePointsAmount = usePointsAmount;
	}

	public String getPayMethod() {
		return this.payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Double getUseRemainBalance() {
		return this.useRemainBalance;
	}

	public void setUseRemainBalance(Double useRemainBalance) {
		this.useRemainBalance = useRemainBalance;
	}

	public String getRemindInfo() {
		return this.remindInfo;
	}

	public void setRemindInfo(String remindInfo) {
		this.remindInfo = remindInfo;
	}

	public Long getUsePoints() {
		return this.usePoints;
	}

	public void setUsePoints(Long usePoints) {
		this.usePoints = usePoints;
	}

	public Double getTrafficSubsidyMoney() {
		return this.trafficSubsidyMoney;
	}

	public void setTrafficSubsidyMoney(Double trafficSubsidyMoney) {
		this.trafficSubsidyMoney = trafficSubsidyMoney;
	}

}