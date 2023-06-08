package com.hobbyvillage.backend.stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthlyRequestDTO {
	private String month;
	private Integer sellRequestCount;
	private Integer consignRequestCount;
}
