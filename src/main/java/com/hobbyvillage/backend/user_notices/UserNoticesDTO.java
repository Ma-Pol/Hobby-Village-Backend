package com.hobbyvillage.backend.user_notices;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserNoticesDTO {
	private Integer notCode;
	private String notCategory;
	private String notTitle;
	private String notContent;
	private Date notDate;
	private Integer notView;
}
