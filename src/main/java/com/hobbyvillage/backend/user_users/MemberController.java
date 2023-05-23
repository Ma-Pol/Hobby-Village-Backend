package com.hobbyvillage.backend.user_users;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hobbyvillage.backend.admin_faq.AdminFAQDTO;



@RestController
public class MemberController {

	private MemberServiceImpl mService;

	public MemberController(MemberServiceImpl mService) {
		this.mService = mService;
	}
	
	@RequestMapping("/login")
	public int login(@RequestBody MemberDTO users) throws Exception {
		int res = mService.login(users);

		return res;
	}
	
	@RequestMapping("/users/register/nickname")
	public boolean nicknameCheck(@RequestBody MemberDTO users) throws Exception {
		int res = mService.nicknameCheck(users);
		
		if(res > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping("/users/register/email")
	public boolean emailCheck(@RequestBody MemberDTO users) throws Exception {
		int res = mService.emailCheck(users);
		
		if(res > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@RequestMapping("/signup")
	public int signup(@RequestBody MemberDTO users) throws Exception {
		int res = mService.signup(users);
		return res;
	}
	
	@RequestMapping("/users/{email}/modify")
	public MemberDTO getUserDetail(@PathVariable(value = "email", required = true) String email) throws Exception {
		MemberDTO UserDetail = mService.getUserDetail(email);
		return UserDetail;
	}
	
	@PostMapping("/users/modify")
	public int modifyMember(@RequestBody MemberDTO users) {
		int res = mService.modifyMember(users);
		return res;
	}
	
	@PostMapping("/users/withdrawal")
	public int deleteMember(@RequestBody MemberDTO users) {
		int res = mService.deleteMember(users);
		return res;
	}
	

	
}
