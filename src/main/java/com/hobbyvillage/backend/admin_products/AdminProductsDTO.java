package com.hobbyvillage.backend.admin_products;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
	private Integer prodDeleted;
	private Integer prodDibs;
	private Integer rentalCount;
	private Integer prodIsRental;
	private Integer prodShipping;
	private Integer reqCode;
	private Integer userCode;
	private Float revwRate;
	private List<String> prodTag;
	private List<String> prodPicture;
}

