package com.hobbyvillage.backend.stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthlyOrderDTO {
	private String month;
	private Integer orderCount;
	private Integer exOrderCount;
}
