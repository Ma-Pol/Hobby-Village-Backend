package com.hobbyvillage.backend.user_purchase;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPurchaseCouponDTO {
	private Integer couponCode;
	private String couponName;
	private Integer discountPer;
	private Integer discountFix;
	private Date deadline;
}
