package com.hobbyvillage.backend.user_cartNdibs;

import lombok.Data;

@Data
public class cartNdibsDTO {

	private int cartCode;
	private String email;
	private String prodCode;
	private int period;
	private String prodBrand;
	private String prodName;
	private String prodContent;
	private int prodPrice;
	private int prodShipping;
	private int prodIsRental;
	private String prodPicture;
}
