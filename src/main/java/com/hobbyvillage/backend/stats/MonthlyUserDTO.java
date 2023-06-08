package com.hobbyvillage.backend.stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyUserDTO {
	private String month;
	private Integer joinCount;
	private Integer leaveCount;
}
