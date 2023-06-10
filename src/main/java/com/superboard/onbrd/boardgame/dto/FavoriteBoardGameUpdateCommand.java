package com.superboard.onbrd.boardgame.dto;

import com.superboard.onbrd.report.dto.ReportCreateCommand;
import com.superboard.onbrd.report.dto.ReportPostRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FavoriteBoardGameUpdateCommand {

    private long memberId;
    private long boardGameId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String favoriteBoardGameLikesYn;

    public static FavoriteBoardGameUpdateCommand of(long memberId,long boardGameId,String favoriteBoardGameLikesYn) {
        FavoriteBoardGameUpdateCommand command = new FavoriteBoardGameUpdateCommand();
        command.memberId = memberId;
        command.boardGameId = boardGameId;
        command.createdAt = LocalDateTime.now();
        command.modifiedAt = LocalDateTime.now();
        command.favoriteBoardGameLikesYn = favoriteBoardGameLikesYn;

        return command;
    }
}
