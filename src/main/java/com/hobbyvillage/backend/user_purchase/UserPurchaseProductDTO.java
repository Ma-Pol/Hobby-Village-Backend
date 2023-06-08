package com.hobbyvillage.backend.user_purchase;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPurchaseProductDTO {
	private String prodCode;
	private String prodName;
	private Integer prodPrice;
	private Integer prodShipping;
	private String prodHost;
	private Integer prodIsRental;
	private String prodPicture;
	private Integer period;
}
