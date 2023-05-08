package com.hobbyvillage.backend.admin_notices;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hobbyvillage.backend.admin_notices.NoticeDTO;

public interface NoticeRepository extends JpaRepository<NoticeDTO, Integer> {

}
