package com.hobbyvillage.backend.admin_notices;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/m/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

//    // get all board
//    @GetMapping("/noticeDTO")
//    public List<NoticeDTO> getAllNotices() {
//        return noticeService.getAllNotice();
//    }

    // create board
    @PostMapping("/noticeDTO")
    public NoticeDTO createNotice(@RequestBody NoticeDTO noticeDTO) {
        return noticeService.createNotice(noticeDTO);
    }

    // get board
    @GetMapping("/noticeDetails/{notCode}")
    public NoticeDTO getNoticeByNo(
            @PathVariable Integer notCode) {
        return noticeService.getNotice(notCode);
    }

    // update board
    @PutMapping("/noticeDTO/{no}")
    public ResponseEntity<NoticeDTO> updateNoticeByNo(
            @PathVariable Integer no, @RequestBody NoticeDTO noticeDTO){

        return noticeService.updateNotice(no, noticeDTO);
    }

    // delete board
    @DeleteMapping("/noticeDTO/{no}")
    public ResponseEntity<Map<String, Boolean>> deleteNoticeByNo(
            @PathVariable Integer no) {

        return noticeService.deleteNotice(no);
    }
}