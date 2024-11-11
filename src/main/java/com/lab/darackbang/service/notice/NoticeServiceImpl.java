package com.lab.darackbang.service.notice;

import com.lab.darackbang.dto.common.PageDTO;
import com.lab.darackbang.dto.notice.NoticeDTO;
import com.lab.darackbang.dto.notice.NoticeSearchDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Override
    public PageDTO<NoticeDTO, NoticeSearchDTO> findAll(NoticeSearchDTO searchDTO, Pageable pageable) {
        return null;
    }
}
