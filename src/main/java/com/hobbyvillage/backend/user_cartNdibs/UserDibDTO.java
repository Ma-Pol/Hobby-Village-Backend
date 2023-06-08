package com.hobbyvillage.backend.user_cartNdibs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDibDTO {
	private Integer dibCode;
	private String prodCode;
	private String prodCategory;
	private String prodBrand;
	private Integer prodDibs;
	private String prodName;
	private Integer prodPrice;
	private Integer prodShipping;
	private Integer prodIsRental;
	private String prodPicture;
}
