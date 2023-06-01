package com.hobbyvillage.backend.user_mypage;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyAddressPageDTO {
	private Integer addressCode;
	private String receiver;
	private Integer zipCode;
	private String address1;
	private String address2;
	private String phone;
	private String deliRequest;
	private Integer isDefault;
	private String email;
}
