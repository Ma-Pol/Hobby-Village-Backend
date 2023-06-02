package com.hobbyvillage.backend.stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthlyReviewDTO {
	private String month;
	private Integer reviewCount;
}
