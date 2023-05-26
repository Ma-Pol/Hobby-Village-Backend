package com.hobbyvillage.backend.admin_users;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminUserMapper {



  @Select("select a.userCode, a.email, a.password, a.name, a.nickname, a.birthday, a.phone, a.profPicture  ,a.savedMoney\r\n"
  + "     , b.addressCode, b.zipCode, b.address1, b.address2, b.isDefault\r\n"
  + "  from users a\r\n"
  + "  left outer join userAddress  b\r\n"
  + "    on a.email = b.email\r\n"
  + " where a.userCode = #{userCode} and b.isDefault = 1;")
  AdminUserDTO getTotalUsers(int userCode);




}
