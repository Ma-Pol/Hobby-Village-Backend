package com.hobbyvillage.backend.user_main;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMainProductDTO {
	private String prodCode;
	private String prodBrand;
	private String prodName;
	private Integer prodPrice;
	private Integer prodDibs;
}
