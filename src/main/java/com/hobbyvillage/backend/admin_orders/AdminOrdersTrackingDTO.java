package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrdersTrackingDTO {
	private Integer opCode;
	private String courierCompany;
	private String trackingNumber;
	private String odrPhone;
	private String prodName;
	private Date deadline;
	private Integer datediff;
}
