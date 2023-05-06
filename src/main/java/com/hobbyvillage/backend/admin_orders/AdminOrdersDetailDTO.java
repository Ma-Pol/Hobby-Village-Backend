package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrdersDetailDTO {
	private String odrNumber;
	private String odrPayment;
	private String odrEmail;
	private Integer odrZipCode;
	private String odrAddress1;
	private String odrAddress2;
	private Date odrDate;
	private Integer userCode;
}
