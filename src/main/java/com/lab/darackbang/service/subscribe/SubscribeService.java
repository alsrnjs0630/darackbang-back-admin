package com.lab.darackbang.service.subscribe;

import com.lab.darackbang.dto.subscribe.SubscribeDTO;
import com.lab.darackbang.dto.subscribe.SubscribeSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscribeService {
    Page<SubscribeDTO> findAll(SubscribeSearchDTO searchDTO, Pageable pageable);
}
