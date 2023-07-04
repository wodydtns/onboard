package com.superboard.onbrd.home.entity;

import com.superboard.onbrd.auth.entity.MemberDetails;
import com.superboard.onbrd.home.dto.PushTogglePatchRequest;
import com.superboard.onbrd.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

    public static PushToggle from(Member member){
        PushToggle pushToggle = new PushToggle();
        pushToggle.setMember(member);

        return pushToggle;
    }

    public static PushToggle of(Member member, PushTogglePatchRequest request){
        PushToggle pushToggle = new PushToggle();
        pushToggle.setMember(member);
        pushToggle.setCommentYn(request.getCommentYn());
        pushToggle.setFavoriteTagYn(request.getFavoriteTagYn());
        return pushToggle;
    }

}
