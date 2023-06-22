﻿CREATE TABLE "MEMBER"
(
    "ID"                          NUMBER                           NOT NULL PRIMARY KEY,
    "EMAIL"                       VARCHAR2(65)                     NOT NULL,
    "NICKNAME"                    VARCHAR2(20)                     NOT NULL,
    "PROFILE_CHARACTER"           VARCHAR2(100)                    NOT NULL,
    "MEMBER_LEVEL"                VARCHAR2(20)                     NOT NULL,
    "POINT"                       NUMBER                           NOT NULL,
    "STATUS"                      VARCHAR2(10)                     NOT NULL,
    "ROLE"                        VARCHAR(10)                      NOT NULL,
    "IS_SOCIAL"                   NUMBER(1)                        NOT NULL,
    "BADGES"                      VARCHAR2(255) DEFAULT '["JOIN"]' NOT NULL,
    "PASSWORD_CHANGE_DELAY_COUNT" NUMBER        DEFAULT 0          NOT NULL,
    "LAST_VISIT_AT"               DATE                             NOT NULL,
    "SERIAL_VISIT_DAYS"           NUMBER                           NOT NULL,
    "TOTAL_ATTEND_DAYS"           NUMBER                           NOT NULL,
    "CREATED_AT"                  DATE                             NOT NULL,
    "MODIFIED_AT"                 DATE                             NOT NULL
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
    "MEMBER_ID"   NUMBER NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "TAG_ID"      NUMBER NOT NULL REFERENCES "TAG" ("ID") ON DELETE CASCADE,
    "CREATED_AT"  DATE   NOT NULL,
    "MODIFIED_AT" DATE   NOT NULL
);

CREATE TABLE "INQUIRY"
(
    "ID"          NUMBER         NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER         NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "ADMIN_EMAIL" VARCHAR2(255)  NULL,
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
    "MEMBER_ID"    NUMBER NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "BOARDGAME_ID" NUMBER NOT NULL REFERENCES "BOARDGAME" ("ID") ON DELETE CASCADE,
    "CREATED_AT"   DATE   NOT NULL,
    "MODIFIED_AT"  DATE   NOT NULL
);

CREATE TABLE "REVIEW"
(
    "ID"           NUMBER              NOT NULL PRIMARY KEY,
    "WRITER_ID"    NUMBER              NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "BOARDGAME_ID" NUMBER              NULL REFERENCES "BOARDGAME" ("ID") ON DELETE SET NULL,
    "GRADE"        FLOAT               NOT NULL,
    "CONTENT"      VARCHAR2(4000)      NOT NULL,
    "IMAGES"       VARCHAR2(1000),
    "LIKE_COUNT"   NUMBER    DEFAULT 0 NOT NULL,
    "IS_HIDDEN"    NUMBER(1) DEFAULT 0 NOT NULL,
    "CREATED_AT"   DATE                NOT NULL,
    "MODIFIED_AT"  DATE                NOT NULL
);

CREATE TABLE "REVIEW_LIKE"
(
    "ID"          NUMBER NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "REVIEW_ID"   NUMBER NOT NULL REFERENCES "REVIEW" ("ID") ON DELETE CASCADE,
    "CREATED_AT"  DATE   NOT NULL,
    "MODIFIED_AT" DATE   NOT NULL
);

CREATE TABLE "COMMENTS"
(
    "ID"          NUMBER              NOT NULL PRIMARY KEY,
    "WRITER_ID"   NUMBER              NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "REVIEW_ID"   NUMBER              NULL REFERENCES "REVIEW" ("ID") ON DELETE SET NULL,
    "CONTENT"     VARCHAR2(4000)      NOT NULL,
    "IS_HIDDEN"   NUMBER(1) DEFAULT 0 NOT NULL,
    "CREATED_AT"  DATE                NOT NULL,
    "MODIFIED_AT" DATE                NOT NULL
);

CREATE TABLE "COMMENT_LIKE"
(
    "ID"          NUMBER NOT NULL PRIMARY KEY,
    "MEMBER_ID"   NUMBER NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "COMMENT_ID"  NUMBER NOT NULL REFERENCES "COMMENTS" ("ID") ON DELETE CASCADE,
    "CREATED_AT"  DATE   NOT NULL,
    "MODIFIED_AT" DATE   NOT NULL
);

CREATE TABLE "NOTICE"
(
    "ID"          NUMBER         NOT NULL PRIMARY KEY,
    "ADMIN_ID"    NUMBER         NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
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
    "MEMBER_ID"     NUMBER        NOT NULL PRIMARY KEY REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "REFRESH_TOKEN" VARCHAR2(300) NULL
);

CREATE TABLE "OAUTH_ID"
(
    "MEMBER_ID" NUMBER        NOT NULL PRIMARY KEY REFERENCES "MEMBER" ("ID") ON DELETE CASCADE,
    "OAUTH_ID"  VARCHAR2(100) NOT NULL,
    "PROVIDER"  VARCHAR2(100) NOT NULL
);

CREATE TABLE "REPORT"
(
    "ID"               NUMBER       NOT NULL PRIMARY KEY,
    "WHISTLEBLOWER_ID" NUMBER       NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "RESOLVER_ID"      NUMBER       NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "TYPE"             VARCHAR2(20) NOT NULL,
    "POST_ID"          NUMBER       NOT NULL,
    "RESOLVED_YN"      NUMBER(1)    NOT NULL,
    "CREATED_AT"       DATE         NOT NULL,
    "MODIFIED_AT"      DATE         NOT NULL
);

CREATE TABLE "NOTIFICATION"
(
    "ID"                NUMBER         NOT NULL PRIMARY KEY,
    "NOTIFICATION_TYPE" VARCHAR2(50)   NOT NULL,
    "PAYLOAD"           VARCHAR2(4000) NOT NULL,
    "IS_CHECKED"        NUMBER(1)      NOT NULL,
    "PUSHED_AT"         DATE           NOT NULL,
    "RECEIVER_ID"       NUMBER         REFERENCES "MEMBER" ("ID") ON DELETE SET NULL
);

CREATE TABLE "BADGE_HISTORY"
(
    "ID"             NUMBER         NOT NULL PRIMARY KEY,
    "MEMBER_ID"      NUMBER         NOT NULL REFERENCES "MEMBER" ("ID") ON DELETE SET NULL,
    "PRE_BADGES"     VARCHAR2(4000) NOT NULL,
    "CURRENT_BADGES" VARCHAR2(4000) NOT NULL,
    "CHECKED_YN"     NUMBER(1)      NOT NULL,
    "CREATED_AT"     DATE           NOT NULL,
    "MODIFIED_AT"    DATE           NOT NULL
)
