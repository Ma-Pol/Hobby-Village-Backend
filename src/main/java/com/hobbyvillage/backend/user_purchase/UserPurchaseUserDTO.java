package com.hobbyvillage.backend.user_purchase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPurchaseUserDTO {
	private String email;
	private String name;
	private String phone;
	private Integer savedMoney;
}
