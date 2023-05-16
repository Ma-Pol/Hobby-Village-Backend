package com.hobbyvillage.backend.admin_requests;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminRequestsDTO {
	private Integer reqCode;
	private String reqSort;
	private String reqEmail;
	private String reqBank;
	private String reeqAccountNum;
	private String reqCategory;
	private String reqTitle;
	private String reqContent;
	private Date reqDate;
	private String reqProgress;
	private Integer userCode;
	private String nickname;
}
