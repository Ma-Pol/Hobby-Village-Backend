package com.hobbyvillage.backend.user_purchase;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserPurchaseMapper {
	@Select("SELECT prodIsRental FROM products WHERE prodCode = #{prodCode};")
	int getProductState(@Param("prodCode") String prodCode);

	@Select("SELECT * FROM users WHERE email = #{email};")
	UserPurchaseUserDTO getUserInfo(@Param("email") String email);

	@Select("SELECT * FROM userAddress WHERE email = #{email} ORDER BY -isDefault, addressCode;")
	List<UserPurchaseAddressDTO> getAddressList(@Param("email") String email);
}
