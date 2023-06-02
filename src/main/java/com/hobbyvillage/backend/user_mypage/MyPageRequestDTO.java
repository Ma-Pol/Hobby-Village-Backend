package com.hobbyvillage.backend.user_mypage;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyPageRequestDTO {
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
