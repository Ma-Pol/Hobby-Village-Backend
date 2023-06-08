package com.hobbyvillage.backend.user_users;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
	private String email;
	private String password;
	private String nickname;
	private String profPicture;
}
