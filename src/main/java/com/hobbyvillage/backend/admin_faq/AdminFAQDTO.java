package com.hobbyvillage.backend.admin_faq;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AdminFAQDTO {
	private int faqCode;
	private String faqTitle;
	private String faqCategory;
	private String faqContent;
	
}
