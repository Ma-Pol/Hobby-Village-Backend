package com.hobbyvillage.backend.user_main;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsermainController {

	private UsermainServiceImpl usermainServiceImpl;
	
	public UsermainController(UsermainServiceImpl usermainServiceImpl) {
		this.usermainServiceImpl = usermainServiceImpl;
	}
	
	// 인기 상품 조회
	@GetMapping("/most-popular-products")
   	public List<UsermainDTO> getmostpopularproducts() {
		return usermainServiceImpl.mostpopularproducts();
	}
	// 인기 브랜드 조회
	@GetMapping("/most-popular-brand-products")
	public List<UsermainDTO> mostpopularbrand() {
		return usermainServiceImpl.mostpopularbrand();
	}
}

