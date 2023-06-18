package com.superboard.onbrd.boardgame.repository;

import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.boardgame.entity.BoardgameLike;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.review.entity.Comment;
import com.superboard.onbrd.review.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardgameLikeRepository extends JpaRepository<BoardgameLike, Long> {

    Optional<BoardgameLike> findByMemberAndBoardGame(Member member, BoardGame boardGame);

    boolean existsByMember_EmailAndBoardGame_Id(String memberEmail, Long boardGameId);
}
