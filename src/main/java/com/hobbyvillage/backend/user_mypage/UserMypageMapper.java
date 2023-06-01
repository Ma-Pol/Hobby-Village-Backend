package com.hobbyvillage.backend.user_mypage;

import com.hobbyvillage.backend.admin_notices.AdminNoticesDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMypageMapper {
    // 공지사항 등록
    @Insert("INSERT INTO userAddress (receiver, address1, address2, zipCode, phone, deliRequest, isDefault, email) " +
            "VALUES (#{receiver}, #{address1}, #{address2}, #{zipCode}, #{phone}, #{deliRequest}, #{isDefault}, #{email});")
    int createAddress(UserMypageDTO address);

    // 기본 배송지로 설정 체크박스 처리
    @Select("SELECT addressCode FROM userAddress WHERE email = #{email} AND isDefault = 1;")
    Integer searchIsDefault(String email);

    @Update("UPDATE userAddress SET isDefault = 0 WHERE addressCode = #{addressCode};")
    void updateIsDefault(int addressCode);

    // 배송지 목록 조회
    @Select("SELECT * FROM userAddress WHERE email = #{email};")
    List<UserMypageDTO> getAddress(@Param("email") String email);

    // 배송지 목록 수정
    @Update("UPDATE userAddress " +
            "SET receiver = #{receiver}, zipCode = #{zipCode}, address1 = #{address1}, address2 = #{address2}, " +
            "phone = #{phone}, deliRequest = #{deliRequest}, isDefault = #{isDefault} " +
            "WHERE addressCode = #{addressCode} ")
    int modifyAddress(UserMypageDTO address);

    // 배송지 삭제
    @Delete("DELETE FROM userAddress WHERE addressCode=#{addressCode} ")
    int deleteAddress(@Param("addressCode") int addressCode);

    // 배송지 상세 조회
    @Select("SELECT * FROM userAddress WHERE addressCode = #{addressCode};")
    UserMypageDTO getAddressDetail(@Param("addressCode") int addressCode);
}
