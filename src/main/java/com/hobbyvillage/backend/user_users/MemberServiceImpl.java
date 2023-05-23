package com.hobbyvillage.backend.user_users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hobbyvillage.backend.admin_faq.AdminFAQDTO;

@Transactional
@Service("mService")
public class MemberServiceImpl {

	private MemberMapper mapper;
	
	public MemberServiceImpl(MemberMapper mapper) {
		this.mapper = mapper;
	}

	public int login(MemberDTO users) {
		return mapper.login(users);
	}
	
	public int nicknameCheck(MemberDTO users) {
		return mapper.nicknameCheck(users);
	}
	
	public int emailCheck(MemberDTO users) {
		return mapper.emailCheck(users);
	}
	
	public int signup(MemberDTO users) {
		int res = mapper.signup(users);
		
		if(res > 0) {
			int res2 = mapper.signupAddr(users);
			return res2;
		} else {
			return res;
		}
	}
	
	public int signupAddr(MemberDTO users) {
		int res2 = mapper.signupAddr(users);
		return res2;
	}
	
	public MemberDTO getUserDetail(String email) {
		MemberDTO UserDetail = mapper.getUserDetail(email);
		return UserDetail;
	}


	public int modifyMember(MemberDTO users) {
		int res = mapper.modifyMember(users);		
		return res;
	}
	
	public int deleteMember(MemberDTO users) {
		int res = mapper.deleteMember(users);
		return res;
	}
	
}
