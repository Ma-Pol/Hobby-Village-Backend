package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrdersDetailDTO {
	private String odrPayment;
	private Integer exactPrice;
	private Integer usedSavedMoney;
	private Integer cancelPrice;
	private Integer cancelSavedMoney;
	private String odrEmail;
	private String odrPhone;
	private String receiver;
	private Integer odrZipCode;
	private String odrAddress1;
	private String odrAddress2;
	private Date odrDate;
	private Integer userCode;
}
