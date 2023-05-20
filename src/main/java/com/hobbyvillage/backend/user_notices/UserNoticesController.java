package com.hobbyvillage.backend.user_notices;

import com.hobbyvillage.backend.admin_notices.AdminNoticesDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notices")
public class UserNoticesController {

	private UserNoticesServiceImpl userNoticesServiceImpl;

	public UserNoticesController(UserNoticesServiceImpl userNoticesServiceImpl) {
		this.userNoticesServiceImpl = userNoticesServiceImpl;
	}

	@GetMapping("/count")
	public int getNoticeCount(@RequestParam(value = "filter", required = true) String filter,
			@RequestParam(value = "keyword", required = false) String keyword) {
		int noticeCount;

		// 검색 여부 확인
		if (keyword == null) {
			noticeCount = userNoticesServiceImpl.getNoticeCount(filter);
		} else {
			noticeCount = userNoticesServiceImpl.getSearchNoticeCount(filter, keyword);
		}

		return noticeCount;
	}

	@GetMapping("")
	public List<UserNoticesDTO> getNoticeList(@RequestParam(value = "filter", required = true) String filter,
											  @RequestParam(value = "sort", required = true) String sort,
											  @RequestParam(value = "pages", required = true) int pages,
											  @RequestParam(value = "keyword", required = false) String keyword) {
		List<UserNoticesDTO> noticeList;
		int pageNum = (pages - 1) * 10;

		// 검색 여부 확인
		if (keyword == null) {
			noticeList = userNoticesServiceImpl.getNoticeList(filter, sort, pageNum);
		} else {
			noticeList = userNoticesServiceImpl.getSearchNoticeList(filter, keyword, sort, pageNum);
		}

		return noticeList;
	}

	// 공지사항 상세 조회
	@GetMapping("/noticeDetails/{notCode}")
	public UserNoticesDTO getNoticeDetail(@PathVariable(value = "notCode", required = true) int notCode) {
		return userNoticesServiceImpl.getNoticeDetail(notCode);
	}

	// 공지사항 조회수 증가
	@PostMapping("/noticeUpdateView/{notCode}")
	public int read(@PathVariable int notCode) {
		return userNoticesServiceImpl.updateNoticeView(notCode);
	}
}
