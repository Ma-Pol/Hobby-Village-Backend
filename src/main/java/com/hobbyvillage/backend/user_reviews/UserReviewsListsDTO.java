package com.hobbyvillage.backend.user_reviews;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserReviewsListsDTO {
	private String revwCode;
	private String revwWriter;
	private Integer revwRate;
	private String revwTitle;
	private String revwContent;
	private Date revwRegiDate;
	private Integer revwReport;
	private String prodCode;
	private String prodName;
}
