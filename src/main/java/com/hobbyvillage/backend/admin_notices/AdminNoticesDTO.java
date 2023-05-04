package com.hobbyvillage.backend.admin_notices;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminNoticesDTO {
	private Integer notCode;
	private String notCategory;
	private String notTitle;
	private String notContent;
	private Date notDate;
	private Integer notView;
}
