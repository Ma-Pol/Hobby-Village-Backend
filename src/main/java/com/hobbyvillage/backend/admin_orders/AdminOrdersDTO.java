package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrdersDTO {
	private Integer odrCode;
	private Integer odrNumber;
	private String prodCode;
	private String odrEmail;
	private String odrPayment;
	private Integer odrZipCode;
	private String odrAddress1;
	private String odrAddress2;
	private Date odrDate;
	private Date deliDate;
	private Integer rentalPeriod;
	private Date deadline;
	private String odrState;
	private Integer userCode;
}
