package com.superboard.onbrd.home.service;

import com.superboard.onbrd.home.dto.PushToggleDto;
import com.superboard.onbrd.home.entity.PushToggle;

public interface PushToggleService {

    PushToggleDto getPushToggle(long id);

    Long updatePushToggle(PushToggle pushToggle);

}
