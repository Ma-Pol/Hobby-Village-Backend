package com.hobbyvillage.backend.admin_faq;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminFaqsDTO {
	private Integer faqCode;
	private String faqCategory;
	private String faqTitle;
	private String faqContent;
	private Date faqDate;
}
