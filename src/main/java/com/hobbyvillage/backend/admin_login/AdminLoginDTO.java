package com.hobbyvillage.backend.admin_login;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminLoginDTO {
	private String id;
	private String password;
	private String nickname;
	
}
