package com.hobbyvillage.backend.admin_qna;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminQustionsDTO {
	private Integer qstCode;
	private String qstTitle;
	private String qstContent;
	private String qstCategory;
	private Date qstDate;
	private Integer qstState;
	private Integer userCode;
	private String qstWriter;
}
