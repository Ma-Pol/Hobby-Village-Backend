package com.hobbyvillage.backend.user_reviews;

import java.sql.Date;

import lombok.Data;

@Data
public class UserreviewsDTO {
	private String email;
	private String revwCode;
	private int revwRate;
	private String revwTitle;
	private Date revwRegiDate;
	private int revwReport;
	private String prodCode;
	private String prodName;
	private String revwContent;
	private String revwPicture;
	
}
