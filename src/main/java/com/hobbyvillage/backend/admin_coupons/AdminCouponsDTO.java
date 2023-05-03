package com.hobbyvillage.backend.admin_coupons;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminCouponsDTO {
	private Integer couponCode;
	private String couponName;
	private Integer discountPer;
	private Integer discountFix;
	private Date startDate;
	private Date deadline;
}
