package com.hobbyvillage.backend.user_products;

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
public class UserProductsDTO {
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
	private String profPicture; // 상품 호스트 프로필 사진 
	private Float revwRate;
	private List<String> prodTag;
	private List<String> prodPicture;
	private String brand;
	private String brandLogo;
}
