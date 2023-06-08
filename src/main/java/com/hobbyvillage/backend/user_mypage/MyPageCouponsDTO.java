package com.hobbyvillage.backend.user_mypage;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyPageCouponsDTO {
	private Integer couponCode;
	private String couponName;
	private Integer discountPer;
	private Integer discountFix;
	private Date startDate;
	private Date deadline;
}
