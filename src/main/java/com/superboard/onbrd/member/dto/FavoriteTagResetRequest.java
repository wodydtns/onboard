package com.superboard.onbrd.member.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavoriteTagResetRequest {
	private List<Long> tagIds;
}
