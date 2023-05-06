package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrdersProductsDTO {
	private Integer opCode;
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
