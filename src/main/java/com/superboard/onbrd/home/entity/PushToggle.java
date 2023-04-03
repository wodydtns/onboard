package com.superboard.onbrd.home.entity;

import com.superboard.onbrd.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PushToggle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Column(nullable = false)
    @ApiModelProperty(notes="사용자 댓글 알림 허용 여부" , example = "N")
    private String commentYn = "N";
    @Column(nullable = false)
    @ApiModelProperty(notes="선호 태그 푸시 허용 여부" , example = "N")
    private String favoriteTagYn = "N";

}
