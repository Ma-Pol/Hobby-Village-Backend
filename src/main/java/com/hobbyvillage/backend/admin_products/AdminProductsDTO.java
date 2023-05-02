package com.hobbyvillage.backend.admin_products;

import java.sql.Date;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdminProductsDTO {
	private String prodCode;
	private String prodCategory;
	private String prodBrand;
	private String prodName;
	private String prodContent;
	private Integer prodPrice;
	private String prodHost;
	private Date prodRegiDate;
	private Integer prodDibs;
	private Integer rentalCount;
	private Integer prodIsRental;
	private Integer prodShipping;
	private Integer reqCode;
	private Integer userCode;
}
