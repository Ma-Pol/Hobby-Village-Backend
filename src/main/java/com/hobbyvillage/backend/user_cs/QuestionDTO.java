package com.hobbyvillage.backend.user_cs;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionDTO {
	private Integer qstCode;
	private String qstTitle;
	private String qstContent;
	private String qstCategory;
	private Date qstDate;
	private Integer qstState;
}
