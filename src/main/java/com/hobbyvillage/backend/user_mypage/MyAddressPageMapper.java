package com.hobbyvillage.backend.user_mypage;

import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface MyAddressPageMapper {
	// 배송지 목록 조회
	@Select("SELECT * FROM userAddress WHERE email = #{email} ORDER BY -isDefault;")
	List<MyAddressPageDTO> getAddressList(@Param("email") String email);

	// 배송지 접근 권한 조회
	@Select("SELECT COUNT(*) FROM userAddress WHERE addressCode = #{addressCode} AND email = #{email};")
	int checkAddress(@Param("addressCode") int addressCode, @Param("email") String email);

	// 배송지 단일 정보 조회
	@Select("SELECT * FROM userAddress WHERE addressCode = #{addressCode};")
	MyAddressPageDTO getAddressDetail(@Param("addressCode") int addressCode);

	// 배송지 삭제
	@Delete("DELETE FROM userAddress WHERE addressCode = #{addressCode};")
	int deleteAddress(@Param("addressCode") int addressCode);

	// 기존 기본 배송지 삭제(isDefault = 1 -> 0, 등록/수정할 배송지를 기본 배송지로 설정한 경우)
	@Update("UPDATE userAddress SET isDefault = 0 WHERE email = #{email} AND isDefault = 1;")
	void deleteDefaultAddress(@Param("email") String email);

	// 배송지 등록
	@Insert("INSERT INTO userAddress(receiver, zipCode, address1, address2, phone, deliRequest, isDefault, email) "
			+ "VALUES(#{receiver}, #{zipCode}, #{address1}, #{address2}, #{phone}, #{deliRequest}, #{isDefault}, #{email});")
	int createAddress(MyAddressPageDTO addressData);

	// 배송지 수정
	@Update("UPDATE userAddress SET receiver = #{receiver}, zipCode = #{zipCode}, address1 = #{address1}, address2 = #{address2}, "
			+ "phone = #{phone}, deliRequest = #{deliRequest}, isDefault = #{isDefault} WHERE addressCode = #{addressCode};")
	int modifyAddress(MyAddressPageDTO addressData);
}
