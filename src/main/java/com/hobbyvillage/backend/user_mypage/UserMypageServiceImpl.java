package com.hobbyvillage.backend.user_mypage;
import com.hobbyvillage.backend.admin_notices.AdminNoticesDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMypageServiceImpl implements UserMypageService{

    private UserMypageMapper mapper;

    public UserMypageServiceImpl(UserMypageMapper mapper) {
        this.mapper = mapper;
    }

    // 배송지 등록
    @Override
    public int createAddress(UserMypageDTO address){
        if(address.getIsDefault() == 1) {
            Integer addressCode = mapper.searchIsDefault(address.getEmail());
            if(addressCode != null) {
                mapper.updateIsDefault(addressCode);
            }
        }
        int result = mapper.createAddress(address);
        return result;
    }

    // 배송지 조회
    @Override
    public List<UserMypageDTO> getAddress(String email){ return mapper.getAddress(email); }

    // 배송지 수정
    @Override
    public int modifyAddress(UserMypageDTO address){
        return mapper.modifyAddress(address);
    }

    // 배송지 삭제
    @Override
    public int deleteAddress(int addressCode){
        return mapper.deleteAddress(addressCode);
    };

    // 배송지 상세 조회
    @Override
    public UserMypageDTO getAddressDetail(int addressCode){
        return mapper.getAddressDetail(addressCode);
    }
}
