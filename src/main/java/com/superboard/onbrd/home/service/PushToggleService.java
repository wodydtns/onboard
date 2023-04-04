package com.superboard.onbrd.home.service;

import com.superboard.onbrd.home.dto.PushToggleDto;

public interface PushToggleService {

    PushToggleDto getPushToggle(long id);

    Long updatePushToggle(PushToggleDto dto);
}
