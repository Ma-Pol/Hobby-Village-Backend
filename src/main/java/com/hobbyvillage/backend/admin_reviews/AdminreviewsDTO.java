package com.hobbyvillage.backend.admin_reviews;

import java.sql.Date;

import lombok.Data;

@Data
public class AdminreviewsDTO {
	
	private String revwCode;
	private Date revwRegiDate;
	private String prodCode;
	private String prodName;
	private int revwRate;
	private String revwWriter;
	private String revwTitle;
	private int revwReport;
	private String revwPicture;
	private String revwContent;

}
