package com.hobbyvillage.backend.admin_users;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminUsersDTO {
	private Integer userCode;
	private String email;
	private String name;
	private String nickname;
	private Date birthday;
	private String phone;
	private String profPicture;
	private Integer savedMoney;
	private Integer deleted;
}
