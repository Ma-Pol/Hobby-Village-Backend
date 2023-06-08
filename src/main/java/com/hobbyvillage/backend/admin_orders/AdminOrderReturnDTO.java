package com.hobbyvillage.backend.admin_orders;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrderReturnDTO {
	private Integer opCode;
	private String prodCode;
	private Integer prodPrice;
	private Integer rentalPeriod;
	private String odrEmail;
}
