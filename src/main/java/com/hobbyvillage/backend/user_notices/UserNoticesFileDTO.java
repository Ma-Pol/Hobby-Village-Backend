package com.hobbyvillage.backend.user_notices;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserNoticesFileDTO {
	private Integer notFileCode;
	private String notFileOriName;
	private String notFileSavName;
}
