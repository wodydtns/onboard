package com.superboard.onbrd.home.service;

import com.superboard.onbrd.home.entity.PushToggle;
import com.superboard.onbrd.home.repository.PushToggleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.superboard.onbrd.home.dto.PushToggleDto;
import com.superboard.onbrd.home.repository.CustomPushToggleRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PushToggleServiceImpl implements PushToggleService {

	private final CustomPushToggleRepository customPushToggleRepository;

	private final PushToggleRepository pushToggleRepository;

	// FIXME  : customPushToggleRepository.getPushToggle 값이 Null일 경우 -> getPushToggle 값 추가
	@Override
	public PushToggleDto getPushToggle(long id) {
		return customPushToggleRepository.getPushToggle(id);
	}

	@Override
	public Long updatePushToggle(PushToggle pushToggle) {
		Optional<PushToggle> pushToggleResult = pushToggleRepository.findByMemberId(pushToggle.getMember().getId());
		if(pushToggleResult.isPresent()){
			PushToggle pushToggleToUpdate = pushToggleResult.get();
			pushToggleToUpdate.setFavoriteTagYn(pushToggle.getFavoriteTagYn());
			pushToggleToUpdate.setCommentYn(pushToggle.getCommentYn());
			pushToggleToUpdate.setMember(pushToggle.getMember());
			return pushToggleRepository.save(pushToggleToUpdate).getId();
		}else {
			return pushToggleRepository.save(pushToggle).getId();
		}
	}

}
