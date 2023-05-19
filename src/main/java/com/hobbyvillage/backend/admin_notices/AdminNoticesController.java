package com.hobbyvillage.backend.admin_notices;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.hobbyvillage.backend.UploadDir;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/m/notices")
public class AdminNoticesController {

    private AdminNoticesServiceImpl adminNoticesServiceImpl;

    public AdminNoticesController(AdminNoticesServiceImpl adminNoticesServiceImpl) {
        this.adminNoticesServiceImpl = adminNoticesServiceImpl;
    }

    @GetMapping("/count")
    public int getNoticeCount(@RequestParam(value = "filter", required = true) String filter,
                              @RequestParam(value = "keyword", required = false) String keyword) {
        int noticeCount;

        // 검색 여부 확인
        if (keyword == null) {
            noticeCount = adminNoticesServiceImpl.getNoticeCount(filter);
        } else {
            noticeCount = adminNoticesServiceImpl.getSearchNoticeCount(filter, keyword);
        }

        return noticeCount;
    }

    @GetMapping("")
    public List<AdminNoticesDTO> getNoticeList(@RequestParam(value = "filter", required = true) String filter,
                                               @RequestParam(value = "sort", required = true) String sort,
                                               @RequestParam(value = "pages", required = true) int pages,
                                               @RequestParam(value = "keyword", required = false) String keyword) {
        List<AdminNoticesDTO> noticeList;
        int pageNum = (pages - 1) * 10;

        // 검색 여부 확인
        if (keyword == null) {
            noticeList = adminNoticesServiceImpl.getNoticeList(filter, sort, pageNum);
        } else {
            noticeList = adminNoticesServiceImpl.getSearchNoticeList(filter, keyword, sort, pageNum);
        }

        return noticeList;
    }

    // 공지사항 상세 조회
    @GetMapping("/noticeDetails/{notCode}")
    public AdminNoticesDTO getNoticeDetail(@PathVariable(value = "notCode", required = true) int notCode) {
        return adminNoticesServiceImpl.getNoticeDetail(notCode);
    }

    // 공지사항 등록
    @PostMapping("/noticeCreate")
    public int createNotice(@RequestBody AdminNoticesDTO notice) {
        return adminNoticesServiceImpl.createNotice(notice);
    }

    // 공지사항 삭제
    @DeleteMapping("/noticeDelete/{notCode}")
    public int deleteNotice(@PathVariable int notCode) {
        return adminNoticesServiceImpl.deleteNotice(notCode);
    }

    // 공지사항 수정
    @PatchMapping("/noticeModify/{notCode}")
    public int modifyNotice(@PathVariable int notCode, @RequestBody AdminNoticesDTO notice) {
        return adminNoticesServiceImpl.modifyNotice(notCode, notice);
    }

    // 공지사항 첨부파일 업로드
    @PostMapping("/upload/img/{notCode}") // {notCode} 받아오기
    public int imageUpload(@PathVariable int notCode, @RequestParam(value = "uploadImg", required = true) MultipartFile[] uploadImg)
            throws IOException {
        // 이미지 업로드 위치 설정(상황에 맞게 RequestsFiles를 고쳐서 사용하세요)
        String uploadPath = UploadDir.uploadDir + "\\Uploaded\\NoticesFile";
        int result = 0;

        // 이미지 개수만큼 반복
        for (MultipartFile image : uploadImg) {

            // 값이 없는 이미지가 아닌 경우(정상적인 이미지인 경우)
            if (!image.isEmpty()) {
                String originalName = image.getOriginalFilename();
                String savedName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

                // 파일 저장 위치 + 저장할 파일명 설정(UUID를 사용해 파일명 중복을 피함)
                File fileName = new File(uploadPath, savedName);

                // 여기서 실제 업로드가 이뤄집니다.
                image.transferTo(fileName);

                // DB에 파일명을 저장할 경우 이곳에서 처리하기
                adminNoticesServiceImpl.createNoticeFile(notCode, originalName, savedName);

                result += 1;
            }
        }

        return result;
    }

}

