package com.superboard.onbrd.home.service;

import com.superboard.onbrd.home.dto.PushToggleDto;
import com.superboard.onbrd.home.repository.CustomPushToggleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PushToggleServiceImpl implements PushToggleService {

    private CustomPushToggleRepository customPushToggleRepository;

    @Override
    public PushToggleDto getPushToggle(long id) {
        return customPushToggleRepository.getPushToggle(id);
    }

    @Override
    public Long updatePushToggle(PushToggleDto dto) {
        return customPushToggleRepository.updatePushToggle(dto);
    }

}
