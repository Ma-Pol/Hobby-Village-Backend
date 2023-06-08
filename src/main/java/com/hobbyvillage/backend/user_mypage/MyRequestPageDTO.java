package com.hobbyvillage.backend.user_mypage;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyRequestPageDTO {
	private Integer reqCode;
	private String reqSort;
	private String reqEmail;
	private String reqBank;
	private String reqAccountNum;
	private String reqCategory;
	private String reqTitle;
	private String reqContent;
	private Date reqDate;
	private String reqProgress;
}
