package com.superboard.onbrd.member.entity;

import static com.superboard.onbrd.member.entity.MemberLevel.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MemberLevelTest {
	@Test
	void test() {
		int point = 4000;
		MemberLevel expected = SPADE;
		MemberLevel actual = getLevelCorrespondingPoint(point);

		assertThat(actual).isEqualTo(expected);
	}
}
