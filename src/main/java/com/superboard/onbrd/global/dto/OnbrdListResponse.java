package com.superboard.onbrd.global.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OnbrdListResponse<T> {
	List<T> content;
}
