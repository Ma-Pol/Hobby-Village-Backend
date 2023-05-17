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
	private String prodCode;
	private Integer revwRate;
	private String revwTitle;
	private String revwContent;
	private String revwWriter;
	private Date revwRegiDate;
	private Integer revwReport;
}
