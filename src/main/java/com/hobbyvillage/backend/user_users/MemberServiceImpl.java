package com.hobbyvillage.backend.user_users;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MemberServiceImpl {

	private MemberMapper mapper;

	public MemberServiceImpl(MemberMapper mapper) {
		this.mapper = mapper;
	}

	public LoginDTO login(LoginDTO users) {
		LoginDTO result = new LoginDTO();
		result.setNickname("취미빌리지");
		
		int check = mapper.loginCheck(users);

		if (check == 1) {
			result = mapper.getUserData(users);
		}

		return result;
	}

	public int nicknameCheck(LoginDTO users) {
		return mapper.nicknameCheck(users);
	}

	public int emailCheck(LoginDTO users) {
		return mapper.emailCheck(users);
	}

	public int signup(LoginDTO users) {
		int res = mapper.signup(users);

		if (res > 0) {
			int res2 = mapper.signupAddr(users);
			return res2;
		} else {
			return res;
		}
	}

	public int signupAddr(LoginDTO users) {
		int res2 = mapper.signupAddr(users);
		return res2;
	}

	public LoginDTO getUserDetail(String email) {
		LoginDTO UserDetail = mapper.getUserDetail(email);
		return UserDetail;
	}

	public int modifyMember(LoginDTO users) {
		int res = mapper.modifyMember(users);
		return res;
	}

	public int deleteMember(LoginDTO users) {
		int res = mapper.deleteMember(users);
		return res;
	}

}
