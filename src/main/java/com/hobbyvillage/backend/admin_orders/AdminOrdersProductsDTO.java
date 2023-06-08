package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrdersProductsDTO {
	private Integer opCode;
	private String courierCompany;
	private String trackingNumber;
	private String prodCode;
	private Date deliDate;
	private Integer rentalPeriod;
	private Date deadline;
	private String odrState;
	private String prodCategory;
	private String prodName;
	private Integer prodPrice;
	private Integer prodShipping;
}
