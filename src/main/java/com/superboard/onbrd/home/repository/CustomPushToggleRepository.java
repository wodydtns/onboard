package com.superboard.onbrd.home.repository;

import com.superboard.onbrd.home.dto.PushToggleDto;

public interface CustomPushToggleRepository {

    public PushToggleDto getPushToggle(long id);

    public Long updatePushToggle(PushToggleDto dto);
}
