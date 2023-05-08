package com.hobbyvillage.backend.admin_notices;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    // get boards data
    public List<NoticeDTO> getAllNotice() {
        return noticeRepository.findAll();
    }

    // create board
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        return noticeRepository.save(noticeDTO);
    }

    // get board one by id
    public NoticeDTO getNotice(Integer no) {
        NoticeDTO noticeDTO = noticeRepository.findById(no)
                .orElseThrow(() -> new ResourceNotFoundException("Not exist Board Data by no : ["+no+"]"));
        return ResponseEntity.ok(noticeDTO);
    }

    // update board
    public ResponseEntity<NoticeDTO> updateNotice(
            Integer no, NoticeDTO updatedNotice) {
        NoticeDTO noticeDTO = noticeRepository.findById(no)
                .orElseThrow(() -> new ResourceNotFoundException("Not exist Board Data by no : ["+no+"]"));
        noticeDTO.setNotCategory(updatedNotice.getNotCategory());
        noticeDTO.setNotTitle(updatedNotice.getNotTitle());
        noticeDTO.setNotContent(updatedNotice.getNotContent());

        NoticeDTO endUpdatedBoard = noticeRepository.save(noticeDTO);
        return ResponseEntity.ok(endUpdatedBoard);
    }

    // delete board
    public ResponseEntity<Map<String, Boolean>> deleteNotice(Integer no) {
        NoticeDTO noticeDTO = noticeRepository.findById(no)
                .orElseThrow(() -> new ResourceNotFoundException("Not exist Board Data by no : ["+no+"]"));

        noticeRepository.delete(noticeDTO);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted Board Data by id : ["+no+"]", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
