package com.hobbyvillage.backend.admin_orders;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrdersCancelOrderDTO {
	private Integer opCode;
	private String odrNumber;
	private String prodCode;
	private Integer prodPrice;
	private Integer rentalPeriod;
	private Integer prodShipping;
	private Integer exactPrice;
	private String odrEmail;
	private Integer usedSavedMoney;
	private Integer check;
}
