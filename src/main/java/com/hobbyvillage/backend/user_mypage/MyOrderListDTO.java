package com.hobbyvillage.backend.user_mypage;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyOrderListDTO {
	private Integer opCode;
	private String odrNumber;
	private Date odrDate;
	private String prodCode;
	private String prodHost;
	private String prodName;
	private Integer prodPrice;
	private Integer prodShipping;
	private String odrState;
	private Integer rentalPeriod;
	private Date deadline;
	private Integer exactPrice;
	private String receiver;
	private Integer odrZipCode;
	private String odrAddress1;
	private String odrAddress2;
	private String courierCompany;
	private String trackingNumber;
}
