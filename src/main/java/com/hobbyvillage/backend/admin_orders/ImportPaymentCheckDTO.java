package com.hobbyvillage.backend.admin_orders;

import java.util.Map;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImportPaymentCheckDTO {
	private int code;
	private String message;
	private Map<String, Object> response;
}