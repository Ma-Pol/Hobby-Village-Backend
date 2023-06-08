package com.hobbyvillage.backend.user_cartNdibs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCartDTO {
	private Integer cartCode;
	private String prodCode;
	private Integer period;
	private String prodCategory;
	private String prodBrand;
	private String prodHost;
	private String prodName;
	private Integer prodPrice;
	private Integer prodShipping;
	private Integer prodIsRental;
	private String prodPicture;
}
