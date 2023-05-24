package com.hobbyvillage.backend.user_users;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private Integer userCode;
	private String email;
	private String password;
	private String name;
	private String nickname;
	private String birthday;
	private String phone;
	private String profPicture;
	private Integer addressCode;
	private String zipCode;
	private String address1;
	private String address2;
	private Integer isDefault;
}
