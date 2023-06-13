package com.superboard.onbrd.boardgame.service;

public interface BoardgameLikeService {

    void createBoardgameLikeOrDeleteIfExist(String email, Long boardGameId);
}
