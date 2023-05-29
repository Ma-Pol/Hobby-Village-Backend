package com.hobbyvillage.backend.admin_reviews;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminReviewsDTO {
	private String revwCode;
	private Date revwRegiDate;
	private String prodCode;
	private String prodName;
	private Integer revwRate;
	private String revwWriter;
	private String revwTitle;
	private Integer revwReport;
	private String revwContent;
}
