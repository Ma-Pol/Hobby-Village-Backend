package com.hobbyvillage.backend.user_reviews;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserReviewsDTO {
	private String revwCode;
	private String prodCode;
	private Integer revwRate;
	private String revwTitle;
	private String revwContent;
	private String revwWriter;
	private Date revwRegiDate;
	private Integer revwReport;
	private String email;
	private String profPicture; // 리뷰 작성자 프로필 사진 
}
