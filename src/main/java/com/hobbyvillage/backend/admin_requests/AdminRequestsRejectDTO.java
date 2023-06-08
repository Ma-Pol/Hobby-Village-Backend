package com.hobbyvillage.backend.admin_requests;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminRequestsRejectDTO {
	private Integer reqCode;
	private String reqTitle;
	private String reqPhone;
	private String rejectReason;
}
