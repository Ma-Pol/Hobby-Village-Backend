package com.hobbyvillage.backend.admin_reviews;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminreviewsController {
	
    private AdminreviewsServiceImpl adminreviewsServiceImpl;
    
    public AdminreviewsController(AdminreviewsServiceImpl adminreviewsServiceImpl) {
        this.adminreviewsServiceImpl = adminreviewsServiceImpl;
    }
    
    // 리뷰 상세 조회
    @GetMapping("/m/reviews/details/{revwCode}")
    public AdminreviewsDTO getreviewsdetails(@PathVariable(value = "revwCode", required = true) String revwCode) {
    	return adminreviewsServiceImpl.getreviewsdetails(revwCode);
    }
    
    // 리뷰 삭제
    @PostMapping("/m/reviews/delete")
    public int deletereivews(@RequestBody AdminreviewsDTO adminreviews) {
    	int res = adminreviewsServiceImpl.deletereivews(adminreviews);
		return res;
    }
}
