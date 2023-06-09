package com.superboard.onbrd.member.entity;

import static com.superboard.onbrd.member.entity.ActivityPoint.*;
import static com.superboard.onbrd.member.entity.Badge.*;
import static com.superboard.onbrd.member.entity.MemberLevel.*;
import static com.superboard.onbrd.member.entity.MemberRole.*;
import static com.superboard.onbrd.member.entity.MemberStatus.*;
import static java.time.temporal.ChronoUnit.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.superboard.onbrd.global.converter.BadgeSortedSetConverter;
import com.superboard.onbrd.global.converter.MemberLevelConverter;
import com.superboard.onbrd.global.converter.MemberRoleConverter;
import com.superboard.onbrd.global.converter.MemberStatusConverter;
import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.member.dto.member.SignUpRequest;
import com.superboard.onbrd.oauth2.dto.OauthSignUpRequest;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.entity.Review;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, updatable = false)
	private String email;
	@Column(nullable = false)
	private String nickname;
	@Column
	private String profileCharacter;
	@Column(name = "member_level", nullable = false)
	@Convert(converter = MemberLevelConverter.class)
	private MemberLevel level = PLAYER;
	@Column(nullable = false)
	private int point = 0;
	@Column(nullable = false)
	@Convert(converter = MemberStatusConverter.class)
	private MemberStatus status = ACTIVE;
	@Column(nullable = false)
	@Convert(converter = MemberRoleConverter.class)
	private MemberRole role = ROLE_USER;
	@Column(nullable = false)
	private Boolean isSocial = false;
	@Column(nullable = false)
	@Convert(converter = BadgeSortedSetConverter.class)
	private SortedSet<Badge> badges = new TreeSet<>(Set.of(JOIN));
	@Column(nullable = false)
	private int passwordChangeDelayCount = 1;
	@Column
	private LocalDateTime lastVisitAt = LocalDateTime.now();
	@Column
	private int serialVisitDays = 1;
	@Column
	private int totalAttendDays = 1;

	@OneToMany
	@JoinColumn(name = "writer_id")
	private List<Review> reviews;
	@OneToMany
	@JoinColumn(name = "writer_id")
	private List<Comment> comments;

	public static Member from(SignUpRequest request) {
		return new Member(
			request.getEmail(), request.getNickname(), request.getProfileCharacter());
	}

	public static Member fromOauth(OauthSignUpRequest request) {
		Member member = new Member();

		member.email = request.getEmail();
		member.nickname = request.getNickname();
		member.profileCharacter = request.getProfileCharacter();
		member.isSocial = true;

		return member;
	}

	public boolean hasEmail(String email) {
		return this.email.equals(email);
	}

	public Set<Badge> getBadges() {
		return Collections.unmodifiableSet(badges);
	}

	public List<Review> getReviews() {
		return Collections.unmodifiableList(reviews);
	}

	public int getReviewCount() {
		return reviews.size();
	}

	public List<Comment> getComments() {
		return Collections.unmodifiableList(comments);
	}

	public int getCommentCount() {
		return comments.size();
	}

	public void updateNickname(String nickname) {
		this.nickname = nickname;
	}

	public void updateProfileCharacter(String profileCharacter) {
		this.profileCharacter = profileCharacter;

		gainBadge(SET_PROFILE_CHARACTER);
	}

	public void delayPasswordChange() {
		passwordChangeDelayCount++;
	}

	public void resetPasswordChangeCount() {
		passwordChangeDelayCount = 0;
	}

	public void updateLevel(MemberLevel level) {
		this.level = level;
	}

	public void increasePoint(int point) {
		this.point += point;
	}

	public void attendAt(LocalDateTime dateTime) {
		if (lastVisitAt.isAfter(dateTime)) {
			return;
		}

		int diff = (int)DAYS.between(lastVisitAt, dateTime);

		switch (diff) {
			case 0:
				lastVisitAt = dateTime;
				break;

			case 1:
				lastVisitAt = dateTime;
				serialVisitDays++;
				totalAttendDays++;
				increasePoint(ATTENDANCE.getPoint());
				break;

			default:
				lastVisitAt = dateTime;
				serialVisitDays = 1;
				totalAttendDays++;
				increasePoint(ATTENDANCE.getPoint());
				break;
		}

		if (totalAttendDays >= 30) {
			gainBadge(ATTEND_THIRTY_DAYS);
		} else if (totalAttendDays >= 7) {
			gainBadge(ATTEND_SEVEN_DAYS);
		}
	}

	public void writeReview(Review review) {
		if (!this.reviews.contains(review)) {
			this.reviews.add(review);
		}

		increasePoint(REVIEW_WRITING.getPoint());
		updateLevel(
			getLevelCorrespondingPoint(getPoint()));

		gainBadge(POST_FIRST_REVIEW);

		if (getReviewCount() >= 5) {
			gainBadge(POST_FIVE_REVIEWS);
		}
	}

	public void writeComment(Comment comment) {
		if (!this.comments.contains(comment)) {
			this.comments.add(comment);
		}

		increasePoint(COMMENT_WRITING.getPoint());
		updateLevel(
			getLevelCorrespondingPoint(getPoint()));

		if (getCommentCount() >= 5) {
			gainBadge(POST_FIVE_COMMENTS);
		}

		this.comments.add(comment);
	}

	public void gainReviewLike(long likeCount) {
		increasePoint(REVIEW_LIKED.getPoint());
		updateLevel(
			MemberLevel.getLevelCorrespondingPoint(getPoint()));

		if (likeCount >= 10) {
			gainBadge(GAIN_TEN_REVIEW_LIKES);
		}
	}

	public void gainAuthority(MemberRole role) {
		this.role = role;
	}

	public void gainBadge(Badge badge) {
		this.badges.add(badge);
	}

	public void withdraw() {
		this.status = WITHDRAWN;
	}

	public void suspend() {
		this.status = SUSPENDED;
	}

	public void kick() {
		this.status = KICKED;
	}

	public void integrate() {
		this.isSocial = true;
	}

	private Member(String email, String nickname, String profileCharacter) {
		this.email = email;
		this.nickname = nickname;
		this.profileCharacter = profileCharacter;
	}
}
