﻿CREATE TABLE "MEMBER"
(
    "ID"                           NUMBER              NOT NULL PRIMARY KEY,
    "EMAIL"                        VARCHAR2(65)        NOT NULL,
    "NICKNAME"                     VARCHAR2(20)        NOT NULL,
    "PROFILE_CHARACTER"            VARCHAR2(100)       NOT NULL,
    "MEMBER_LEVEL"                 VARCHAR2(20)        NOT NULL,
    "POINT"                        NUMBER              NOT NULL,
    "STATUS"                       VARCHAR2(10)        NOT NULL,
    "ROLE"                         VARCHAR(10)         NOT NULL,
    "PASSWORD_CHANGE_DUE_EXTENDED" NUMBER(1) DEFAULT 0 NOT NULL,
    "LAST_VISIT"                   DATE                NULL,
    "CREATED_AT"                   DATE                NOT NULL,
    "MODIFIED_AT"                  DATE                NOT NULL
);

CREATE TABLE "TAG"
(
    "ID"   NUMBER       NOT NULL PRIMARY KEY,
    "NAME" VARCHAR2(20) NOT NULL,
    "TYPE" VARCHAR2(20) NOT NULL
);

CREATE TABLE "FAVORITE_TAG"
(
    "ID"          NUMBER NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER NOT NULL REFERENCES "MEMBER" ("ID"),
    "TAG_ID"      NUMBER NOT NULL REFERENCES "TAG" ("ID"),
    "CREATED_AT"  DATE   NOT NULL,
    "MODIFIED_AT" DATE   NOT NULL
);

CREATE TABLE "INQUIRY"
(
    "ID"          NUMBER         NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER         NOT NULL REFERENCES "MEMBER" ("ID"),
    "ADMIN_ID"    NUMBER         NULL REFERENCES "MEMBER" ("ID"),
    "TITLE"       VARCHAR2(100)  NOT NULL,
    "CONTENT"     VARCHAR2(4000) NOT NULL,
    "IS_ANSWERED" NUMBER(1)      NOT NULL,
    "ANSWER"      VARCHAR2(4000) NULL,
    "ANSWERED_AT" DATE           NULL,
    "CREATED_AT"  DATE           NOT NULL,
    "MODIFIED_AT" DATE           NOT NULL
);

CREATE TABLE "BOARDGAME"
(
    "ID"             NUMBER           NOT NULL PRIMARY KEY,
    "NAME"           VARCHAR2(30)     NOT NULL,
    "DESCRIPTION"    VARCHAR2(4000)   NOT NULL,
    "IMAGE"          VARCHAR2(100)    NOT NULL,
    "FAVORITE_COUNT" NUMBER DEFAULT 0 NOT NULL
);

CREATE TABLE "FAVORITE_BOARDGAME"
(
    "ID"           NUMBER NOT NULL PRIMARY KEY,
    "MEMBER_ID"    NUMBER NOT NULL REFERENCES "MEMBER" ("ID"),
    "BOARDGAME_ID" NUMBER NOT NULL REFERENCES "BOARDGAME" ("ID") ON DELETE CASCADE,
    "CREATED_AT"   DATE   NOT NULL,
    "MODIFIED_AT"  DATE   NOT NULL
);

CREATE TABLE "REVIEW"
(
    "ID"           NUMBER           NOT NULL PRIMARY KEY,
    "WRITER_ID"    NUMBER           NOT NULL REFERENCES "MEMBER" ("ID"),
    "BOARDGAME_ID" NUMBER           NOT NULL REFERENCES "BOARDGAME" ("ID") ON DELETE SET NULL,
    "GRADE"        FLOAT            NOT NULL,
    "CONTENT"      VARCHAR2(4000)   NOT NULL,
    "IMAGES"       VARCHAR2(1000),
    "LIKE_COUNT"   NUMBER DEFAULT 0 NOT NULL,
    "CREATED_AT"   DATE             NOT NULL,
    "MODIFIED_AT"  DATE             NOT NULL
);

CREATE TABLE "REVIEW_LIKE"
(
    "ID"          NUMBER NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER NOT NULL REFERENCES "MEMBER" ("ID"),
    "REVIEW_ID"   NUMBER NOT NULL REFERENCES "REVIEW" ("ID") ON DELETE CASCADE,
    "CREATED_AT"  DATE   NOT NULL,
    "MODIFIED_AT" DATE   NOT NULL
);

CREATE TABLE "COMMENT"
(
    "ID"          NUMBER         NOT NULL PRIMARY KEY,
    "WRITER_ID"   NUMBER         NOT NULL REFERENCES "MEMBER" ("ID"),
    "REVIEW_ID"   NUMBER         NOT NULL REFERENCES "REVIEW" ("ID") ON DELETE SET NULL,
    "CONTENT"     VARCHAR2(4000) NOT NULL,
    "CREATED_AT"  DATE           NOT NULL,
    "MODIFIED_AT" DATE           NOT NULL
);

CREATE TABLE "COMMENT_LIKE"
(
    "ID"          NUMBER NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER NOT NULL REFERENCES "MEMBER" ("ID"),
    "COMMENT_ID"  NUMBER NOT NULL REFERENCES "COMMENT" ("ID") ON DELETE CASCADE,
    "CREATED_AT"  DATE   NOT NULL,
    "MODIFIED_AT" DATE   NOT NULL
);

CREATE TABLE "NOTICE"
(
    "ID"          NUMBER         NOT NULL PRIMARY KEY,
    "ADMIN_ID"    NUMBER         NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "TITLE"       VARCHAR2(100)  NOT NULL,
    "CONTENT"     VARCHAR2(4000) NOT NULL,
    "CREATED_AT"  DATE           NOT NULL,
    "MODIFIED_AT" DATE           NOT NULL
);

CREATE TABLE "BOARDGAME_TAG"
(
    "ID"           NUMBER NOT NULL PRIMARY KEY,
    "BOARDGAME_ID" NUMBER NOT NULL REFERENCES "BOARDGAME" ("ID") ON DELETE CASCADE,
    "TAG_ID"       NUMBER NOT NULL REFERENCES "TAG" ("ID") ON DELETE CASCADE
);

CREATE TABLE "PASSWORD"
(
    "MEMBER_ID"        NUMBER        NOT NULL PRIMARY KEY REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "ENCODED_PASSWORD" VARCHAR2(100) NULL,
    "CREATED_AT"       DATE          NOT NULL,
    "MODIFIED_AT"      DATE          NOT NULL
);

CREATE TABLE "TOKEN"
(
    "MEMBER_ID"                NUMBER        NOT NULL PRIMARY KEY REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "ANDROID_PUSH_TOKEN"       VARCHAR2(100) NULL,
    "APPLE_PUSH_TOKEN"         VARCHAR2(100) NULL,
    "SIGN_OUT_ACCESS_TOKEN"    VARCHAR2(300) NULL,
    "REFRESH_TOKEN"            VARCHAR2(300) NULL,
    "REFRESH_TOKEN_EXPIRED_AT" DATE          NULL,
    "OAUTH_GRANT_TOKEN"        VARCHAR2(100) NULL
);
