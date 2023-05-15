package com.hobbyvillage.backend.admin_orders;

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
}
