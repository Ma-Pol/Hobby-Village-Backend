package com.hobbyvillage.backend.user_users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

	private int userCode;
	private String email;
	private String password;
    private String name;
    private String nickname;
    private String birthday;
    private String phone;
    private String profPicture;
    private int addressCode;
    private String zipCode;
    private String address1;
    private String address2;
    private int isDefault;

	
}