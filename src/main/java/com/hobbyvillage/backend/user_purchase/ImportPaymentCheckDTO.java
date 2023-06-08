package com.hobbyvillage.backend.user_purchase;

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
