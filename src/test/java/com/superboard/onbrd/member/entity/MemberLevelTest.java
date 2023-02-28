package com.superboard.onbrd.member.entity;

import static com.superboard.onbrd.member.entity.ActivityPoint.*;
import static com.superboard.onbrd.member.entity.MemberLevel.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemberLevelTest {
	@Test
	void test() {
		MemberLevel expected = SPADE;

		int point = 4000;
		MemberLevel actual = getLevelCorrespondingPoint(point);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void point() {
		int expected = 50;

		int actual = REVIEW_WRITING.point();

		assertThat(actual).isEqualTo(expected);
	}
}
