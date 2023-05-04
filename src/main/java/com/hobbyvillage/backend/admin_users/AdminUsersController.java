package com.hobbyvillage.backend.admin_users;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/users")
public class AdminUsersController {

	private AdminUsersServiceImpl adminUsersServiceImpl;

	public AdminUsersController(AdminUsersServiceImpl adminUsersServiceImpl) {
		this.adminUsersServiceImpl = adminUsersServiceImpl;
	}

	@GetMapping("/count")
	public int getUserCount(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int userCount;

		// 검색 여부 확인
		if (keyword == null) {
			userCount = adminUsersServiceImpl.getUserCount();
		} else {
			userCount = adminUsersServiceImpl.getSearchUserCount(condition, keyword);
		}

		return userCount;
	}

	@GetMapping("")
	public List<AdminUsersDTO> getUserList(@RequestParam(value = "sort", required = true) String sort,
			@RequestParam(value = "pages", required = true) int pages,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "keyword", required = false) String keyword) {
		List<AdminUsersDTO> userList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			userList = adminUsersServiceImpl.getUserList(sort, pageNum);
		} else {
			userList = adminUsersServiceImpl.getSearchUserList(condition, keyword, sort, pageNum);
		}

		return userList;
	}

	@DeleteMapping("/delete")
	public void deleteUser(@RequestParam(value = "userCode", required = true) int userCode) {
		adminUsersServiceImpl.deleteUser(userCode);
	}
}
