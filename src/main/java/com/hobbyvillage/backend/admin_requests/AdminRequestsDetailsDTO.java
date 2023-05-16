package com.hobbyvillage.backend.admin_requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminRequestsDetailsDTO {
	private Integer reqCode;
	private String reqSort;
	private String reqEmail;
	private String reqCategory;
	private String reqTitle;
	private String reqContent;
	private String reqProgress;
	private String nickname;
}
