package com.superboard.onbrd.boardgame.entity;

import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.entity.CommentLike;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardgameLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardgame_id")
    private BoardGame boardGame;

    public static BoardgameLike of(Member member, BoardGame boardGame) {
        BoardgameLike boardgameLike = new BoardgameLike();
        boardgameLike.member = member;
        boardgameLike.boardGame = boardGame;

        return boardgameLike;
    }
}
