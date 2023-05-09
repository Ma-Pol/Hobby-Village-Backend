package com.hobbyvillage.backend.user_purchase;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
public class UserPurchaseController {

	private UserPurchaseServiceImpl userPurchaseServiceImpl;

	public UserPurchaseController(UserPurchaseServiceImpl userPurchaseServiceImpl) {
		this.userPurchaseServiceImpl = userPurchaseServiceImpl;
	}

	@GetMapping("/productState/{prodCode}")
	public int getProductDetail(@PathVariable(value = "prodCode", required = true) String prodCode) {
		return userPurchaseServiceImpl.getProductState(prodCode);
	}

	@GetMapping("/{email}")
	public UserPurchaseUserDTO getUserInfo(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getUserInfo(email);
	}

	@GetMapping("/addresses/{email}")
	public List<UserPurchaseAddressDTO> getAddressList(@PathVariable(value = "email", required = true) String email) {
		return userPurchaseServiceImpl.getAddressList(email);
	}

}
