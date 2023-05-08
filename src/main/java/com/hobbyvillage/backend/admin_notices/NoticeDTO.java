//package com.hobbyvillage.backend.admin_notices;
//
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//@Entity
//@Table(name = "noticeDTO")
//@DynamicInsert
//@DynamicUpdate
//public class NoticeDTO {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer notCode;
//
//	@Column(name = "notCategory")
//	private String notCategory;
//
//	@Column(name = "notTitle")
//	private String notTitle;
//
//	@Column(name = "notContent")
//	private String notContent;
//
//	@Column(name = "notDate")
//	private Date notDate;
//
//	@Column(name = "notView")
//	private Integer notView;
//
//	public Integer getNotCode() {
//		return notCode;
//	}
//
//	public void setNotCode(Integer notCode) {
//		this.notCode = notCode;
//	}
//
//	public String getNotCategory() {
//		return notCategory;
//	}
//
//	public void setNotCategory(String notCategory) {
//		this.notCategory = notCategory;
//	}
//
//	public String getNotTitle() {
//		return notTitle;
//	}
//
//	public void setNotTitle(String notTitle) {
//		this.notTitle = notTitle;
//	}
//
//	public String getNotContent() {
//		return notContent;
//	}
//
//	public void setNotContent(String notContent) {
//		this.notContent = notContent;
//	}
//
//	public Date getNotDate() {
//		return notDate;
//	}
//
//	public void setNotDate(Date notDate) {
//		this.notDate = notDate;
//	}
//
//	public Integer getNotView() {
//		return notView;
//	}
//
//	public void setNotView(Integer notView) {
//		this.notView = notView;
//	}
//// ---Getter/Setter ---
//
//}