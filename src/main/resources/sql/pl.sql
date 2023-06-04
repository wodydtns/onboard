-- insert to favorite_boardgame

declare
    row_num       number := 17;
    boardgame_num number := 93;
    now           date   := sysdate;
    member_num    number := 4389;

begin
    loop
        insert into FAVORITE_BOARDGAME
        values (row_num, member_num, boardgame_num, now, now);

        row_num := row_num + 1;
        boardgame_num := boardgame_num + 1;
        exit when boardgame_num > 110;
    end loop;
end ;

-- insert to review

declare
    row_num       number        := 63;
    boardgame_num number        := 77;
    now           date          := sysdate;
    content       varchar(4000) := 'content';
    member_num    number        := 818;

begin
    loop
        insert into REVIEW
        values (row_num, member_num, boardgame_num, 3, concat(content, boardgame_num), now, now, null, 0, 0);

        row_num := row_num + 1;
        boardgame_num := boardgame_num + 1;
        exit when boardgame_num > 110;
    end loop;
end;

-- insert to comments

declare
    row_num    number       := 25;
    writer_num number       := 4389;
    review_num number       := 63;
    now        date         := sysdate;
    content    varchar(255) := 'content';

begin
    loop
        insert into COMMENTS
        values (row_num, writer_num, review_num, concat(CONTENT, review_num), now, now, 0);

        row_num := row_num + 1;
        review_num := review_num + 1;
        exit when review_num > 96;
    end loop;
end;

-- insert to review_like

declare
    row_num    number := 1;
    member_num number := 4389;
    review_num number := 63;
    now        date   := sysdate;

begin
    loop
        insert into REVIEW_LIKE
        values (row_num, member_num, review_num, now, now);

        update REVIEW
        set LIKE_COUNT  = 1,
            MODIFIED_AT = now
        where ID = review_num;

        row_num := row_num + 1;
        review_num := review_num + 1;
        exit when review_num > 96;
    end loop;
end;
