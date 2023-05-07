package com.hobbyvillage.backend.admin_orders;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminOrdersDTO {
	private String odrNumber;
	private String prodCode;
	private Date deliDate;
	private Integer rentalPeriod;
	private Date deadline;
	private String odrState;
	private Integer userCode;
	private String nickname;
}
