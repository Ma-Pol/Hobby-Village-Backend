package com.hobbyvillage.backend.user_purchase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPurchaseProcessDTO {
	private String odrNumber;
	private String odrPayment;
	private Integer exactPrice;
	private Integer usedSavedMoney;
	private String odrEmail;
	private String odrPhone;
	private Integer odrZipCode;
	private String odrAddress1;
	private String odrAddress2;
	private String receiver;
	private String deliRequest;
	private String prodCode;
	private String prevOdrNumber;
	private Integer rentalPeriod;
	private String imp_uid;
}
