package com.hobbyvillage.backend.user_mypage;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/mypages")
public class UserMypageController {
    private UserMypageServiceImpl userMypageServiceImpl;

    public UserMypageController(UserMypageServiceImpl userMypageServiceImpl) {
        this.userMypageServiceImpl = userMypageServiceImpl;
    }

    // 배송지 주소 등록
    @PostMapping("/{email}/addresses/create")
    public int createAddress(@RequestBody UserMypageDTO address) {
        System.out.print(address);
        return userMypageServiceImpl.createAddress(address);
    }

    // 배송지 목록 조회
    @GetMapping("/{email}/addresses")
    public List<UserMypageDTO> getAddress(@PathVariable(value = "email", required = true) String email) {
        System.out.print(email);
        return userMypageServiceImpl.getAddress(email);
    }

    // 배송지 수정
    @PatchMapping("/addresses/modify")
    public int modifyNotice(@RequestBody UserMypageDTO address){
        System.out.print(address);
        return userMypageServiceImpl.modifyAddress(address);
    }

    // 배송지 삭제
    @DeleteMapping("{email}/addresses/delete/{addressCode}")
    public int deleteAddress(@PathVariable int addressCode){
        return userMypageServiceImpl.deleteAddress(addressCode);
    }

    // 배송지 상세 조회
    @GetMapping("/{email}/addresses/{addressCode}")
    public UserMypageDTO getAddressDetail(@PathVariable(value = "email", required = true) String email, @PathVariable(value = "addressCode", required = true) int addressCode) {
        System.out.print(addressCode);
        return userMypageServiceImpl.getAddressDetail(addressCode);
    }
}
