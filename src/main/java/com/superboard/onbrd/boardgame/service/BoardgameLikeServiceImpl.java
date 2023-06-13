package com.superboard.onbrd.boardgame.service;

import com.superboard.onbrd.boardgame.entity.BoardGame;
import com.superboard.onbrd.boardgame.entity.BoardgameLike;
import com.superboard.onbrd.boardgame.repository.BoardgameLikeRepository;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.service.MemberService;
import com.superboard.onbrd.review.entity.CommentLike;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardgameLikeServiceImpl implements BoardgameLikeService {

    private final BoardGameService boardGameService;

    private final MemberService memberService;

    private final BoardgameLikeRepository boardgameLikeRepository;

    @Override
    public void createBoardgameLikeOrDeleteIfExist(String email, Long boardGameId) {
        BoardGame boardGame = boardGameService.findVerifiedOneById(boardGameId);

        Member member = memberService.findVerifiedOneByEmail(email);

        Optional<BoardgameLike> boardgameLikeOptional = boardgameLikeRepository.findByMemberAndBoardGame(member, boardGame);
        boardgameLikeOptional.ifPresentOrElse(
                boardgameLikeRepository::delete,
                () -> boardgameLikeRepository.save(BoardgameLike.of(member,boardGame))
        );
    }
}
