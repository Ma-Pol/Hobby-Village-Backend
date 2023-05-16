package com.hobbyvillage.backend.user_cs;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FaqDTO {
	private Integer faqCode;
	private String faqCategory;
	private String faqTitle;
	private String faqContent;
	private Date faqDate;
}