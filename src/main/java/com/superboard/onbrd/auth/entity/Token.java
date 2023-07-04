package com.superboard.onbrd.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.superboard.onbrd.auth.dto.PushTokenPostRequest;
import com.superboard.onbrd.member.entity.Member;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
    @Id
    @Column(name = "member_id")
    private Long id;
    @Column
    private String androidPushToken;
    @Column
    private String applePushToken;
    @Column
    private String signOutAccessToken;

    @Column
    private String refreshToken;
    @Column
    private LocalDateTime refreshTokenExpiredAt = LocalDateTime.now();
    @Column
    private String oauthGrantToken;
    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id")
    private Member member;

    public static Token from(Member member) {
        Token token = new Token();
        token.member = member;

        return token;
    }

    public static Token validateToken(PushTokenPostRequest pushTokenPostRequest, Long memberId) {
        Token token = new Token();
        token.setId(memberId);
        token.setAndroidPushToken(pushTokenPostRequest.getPushTokenValue());
        return token;

    }
}
