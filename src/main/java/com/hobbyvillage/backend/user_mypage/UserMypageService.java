package com.hobbyvillage.backend.user_mypage;

import com.hobbyvillage.backend.admin_notices.AdminNoticesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMypageService {
    int createAddress(UserMypageDTO address);
    List<UserMypageDTO> getAddress(String email);
    int modifyAddress(UserMypageDTO address);
    int deleteAddress(int addressCode);
    UserMypageDTO getAddressDetail(int addressCode);
}
