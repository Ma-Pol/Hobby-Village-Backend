package com.hobbyvillage.backend.user_requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestsDTO {
	private String reqSort;
	private String reqEmail;
	private String reqBank;
	private String reqAccountNum;
	private String reqCategory;
	private String reqTitle;
	private String reqContent;
}
