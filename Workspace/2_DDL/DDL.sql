`Member`/* ==================== 테이블 삭제 ============================= */
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS ScoreStandard;
DROP TABLE IF EXISTS MountainRank;
DROP TABLE IF EXISTS MountainRankStandard;
DROP TABLE IF EXISTS MountainBookmark;
DROP TABLE IF EXISTS MountainStamp;
DROP TABLE IF EXISTS MountainReview;
DROP TABLE IF EXISTS CourseStamp;
DROP TABLE IF EXISTS CourseBookmark;
DROP TABLE IF EXISTS CourseReview;
DROP TABLE IF EXISTS ClimbCheck;
DROP TABLE IF EXISTS ClimbRecord;
DROP TABLE IF EXISTS MemberStatus;
DROP TABLE IF EXISTS MemberAuthority;
DROP TABLE IF EXISTS AuthorityList;
DROP TABLE IF EXISTS LoginFailureRecord;

DROP TABLE IF EXISTS LoginRecord;
DROP TABLE IF EXISTS CrewClimbBoard;
DROP TABLE IF EXISTS CrewMember;
DROP TABLE IF EXISTS CrewApplyHistory;
DROP TABLE IF EXISTS CrewApply;
DROP TABLE IF EXISTS Crew;
DROP TABLE IF EXISTS CrewClimbRecord;
DROP TABLE IF EXISTS CrewMemberRole;
DROP TABLE IF EXISTS CrewMemberHistory;
DROP TABLE IF EXISTS CrewRankStandard;
DROP TABLE IF EXISTS CrewMemberAuth;

DROP TABLE IF EXISTS Mountain;
DROP TABLE IF EXISTS MountainImage;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS CourseDifficulty;
DROP TABLE IF EXISTS RecommendGear;
DROP TABLE IF EXISTS PlaceCodeStandard;

DROP TABLE IF EXISTS NoticeBoard;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Board;
DROP TABLE IF EXISTS BoardImage;
DROP TABLE IF EXISTS CrewBoard;
DROP TABLE IF EXISTS Likes;
DROP TABLE IF EXISTS CrewBoardImage;
DROP TABLE IF EXISTS NoticeBoardImage;

DROP TABLE if EXISTS ReportTargetType;
DROP TABLE if EXISTS ReportCategory;
DROP TABLE if EXISTS ReportCategoryStandard;
DROP TABLE if EXISTS Report;
DROP TABLE if EXISTS Blacklist;
DROP TABLE if EXISTS Ban;

/* ==================== 테이블 생성 ============================= */
CREATE TABLE Member (
	id	bigint	 auto_increment	,
	memId	varchar(255)	NULL,
	email	varchar(255)	NOT NULL,
	nickname	varchar(255)	NOT NULL,
	memPwd	varchar(255)	NOT NULL,
	memName	varchar(255)	NOT NULL,
	birth	date	NOT NULL	DEFAULT now(),
	gender	varchar(1)	NOT NULL	,
	signInDate	date	NOT NULL	DEFAULT now()	,
	lastLogin	datetime	NOT NULL	DEFAULT now(),
	climbCnt	bigint	NOT NULL	DEFAULT 0,
	banCnt	int	NULL	DEFAULT 0,
	loginFailCnt	int	NULL	DEFAULT 0,
	quitDate	datetime	NULL	,
	loginLockUntil datetime	NOT NULL	,
	score	int	NOT NULL	DEFAULT 0	,
	memRankId	bigint	NULL,
	memStsId	bigint	NULL,
	crewId	bigint	NULL	,
	constraint pk_Member_id primary key(id),
	constraint ch_Member_gender check(gender in('F','M')),
	constraint uk_Member_email Unique(email)
);

CREATE TABLE ScoreStandard (
	id	bigint	NOT null auto_increment,
	name	VARCHAR(255)	NOT NULL,
	score	INTEGER	NOT null,
	constraint pk_ScoreStandard_id primary key(id)
);

CREATE TABLE MemberRank (
	id	bigint	NOT null auto_increment,
	name	VARCHAR(255)	NOT NULL,
	standard	INTEGER	NOT NULL,
	constraint pk_MemberRank_id primary key(id)
);

CREATE TABLE MountainRankStandard (
	id	bigint	NOT null auto_increment,
	rankName	VARCHAR(255)	NOT NULL,
	constraint pk_MountainRankStandard_id primary key(id)
);


CREATE TABLE MountainRank (
	id	bigint	NOT null auto_increment,
	mtRankId	BIGINT	NULL,
	cumId	BIGINT	NOT NULL,
	frtrlId	VARCHAR(10)	NULL,
	constraint pk_MountainRank_id primary key(id)
);

CREATE TABLE MountainBookmark (
	id	bigint	NOT NULL	auto_increment, 
	time	DATETIME	NULL,
	frtrlId	VARCHAR(10)	NULL,
	cumId	BIGINT	NOT NULL,
	constraint pk_MountainBookmark_id primary key(id)
);


CREATE TABLE MountainStamp (
	id	bigint	NOT null auto_increment,
	date	datetime	NOT NULL,
	cumId	bigint	NOT NULL,
	frtrlId	varchar(10)	NULL,
	constraint pk_MountainStamp_id primary key(id)
);

CREATE TABLE MountainReview (
	id	bigint	NOT NULL	auto_increment,
	registTime	datetime	NOT NULL,
	updateTime	datetime	NULL,
	content	varchar(2000)	NOT NULL,
	cumId	BIGINT	NOT NULL,
	frtrlId	varchar(10)	NULL,
	constraint pk_MountainReview_id primary key(id)
);

CREATE TABLE CourseStamp (
	id	bigint	NOT NULL	auto_increment,
	date	datetime	NOT NULL,
	poiId	varchar(10)	NOT NULL,
	cumId	BIGINT	NULL,
	constraint pk_CourseStamp_id primary key(id)
);

CREATE TABLE CourseBookmark (
	id	bigint	NOT NULL	auto_increment,
	bookmarkDate	varchar(255)	NULL,
	cumId	BIGINT	NOT NULL,
	poiId	varchar(10)	NULL,
	constraint pk_CourseBookmark_id primary key(id)
);

CREATE TABLE CourseReview (
	id	bigint	NOT NULL	auto_increment,
	commitDate	datetime	NULL,
	updateDate	datetime	NULL,
	reviewContent	varchar(2000)	NOT NULL,
	poiId	varchar(10)	NOT NULL,
	cumId	BIGINT	NOT NULL,
	constraint pk_CourseReview_id primary key(id)
);


CREATE TABLE ClimbCheck (
	id	bigint	NOT NULL	auto_increment,
	stateCode	varchar(255)	NULL DEFAULT 'N',
	updateTime	datetime	NULL DEFAULT now(),
	endTime	datetime	NULL,
	cumId	BIGINT	NOT NULL,
	frtrlId	varchar(10)	NULL,
	poiId	varchar(10)	NOT NULL,
	constraint pk_ClimbCheck_id primary key(id)
);

CREATE TABLE ClimbRecord (
	id	bigint	NOT NULL	auto_increment,
	startTime	datetime	NOT NULL,
	endTime	datetime	NOT NULL,
	cumId	BIGINT	NOT NULL,
	frtrlId	varchar(10)	NULL,
	poiId	varchar(10)	NOT NULL,
	constraint pk_ClimbRecord_id primary key(id)
);


CREATE TABLE MemberStatus (
	id	bigint	NOT null auto_increment,
	status	VARCHAR(255)	NOT NULL,
	constraint pk_MemberStatus_id primary key(id)
);

CREATE TABLE MemberAuthority (
	id	bigint	NOT null auto_increment,
	cumId	BIGINT	NOT NULL,
	authId	BIGINT	NULL,
	constraint pk_MemberAuthority_id primary key(id)
);


CREATE TABLE AuthorityList (
	id	bigint	NOT null auto_increment,
	authName	VARCHAR(255)	NOT NULL,
	authDescribe	VARCHAR(255)	NULL,
	constraint pk_AuthorityList_id primary key(id)
);

CREATE TABLE LoginFailureRecord (
	id	bigint	NOT NULL	auto_increment,
	date	datetime	NOT NULL	DEFAULT now(),
	ip	VARCHAR(255)	NULL,
	reason	VARCHAR(2000)	NOT NULL,
	cumId	BIGINT	NOT NULL,
	constraint pk_LoginFailureRecord_id primary key(id)
);

CREATE TABLE LoginRecord (
	id	bigint	NOT NULL	auto_increment,
	date	datetime	NOT NULL,
	ip	varchar(255)	NULL,
	preAddr	VARCHAR(255)	NOT NULL,
	cumId	BIGINT	NOT NULL,
	constraint pk_LoginRecord_id primary key(id)
);

CREATE TABLE IF NOT EXISTS CrewRankStandard
(
    id                    BIGINT AUTO_INCREMENT       NOT NULL,
    crewRankName          VARCHAR(255) NULL,
    crewRankStandardScore INTEGER      NULL,
    CONSTRAINT pk_CrewRankStandard_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CrewClimbBoard
(
    id                        BIGINT AUTO_INCREMENT        NOT NULL,
    crewClimbStartDate        DATETIME      NOT NULL,
    crewClimbRecruitStartDate DATETIME      NOT NULL,
    crewClimbRecruitEndDate   DATETIME      NOT NULL,
    crewClimbContent          VARCHAR(2000) NULL,
    crewClimbIsDeleted        VARCHAR(1)    NOT NULL DEFAULT 'N' COMMENT 'Y,N',
    crewClimbAmountOfPeople   INTEGER       NOT NULL,
    crewClimbIsEnded          VARCHAR(1)    NULL     DEFAULT 'N' COMMENT 'Y,N',
    crewId                    BIGINT        NOT NULL,
    frtrlId                   VARCHAR(10)       NOT NULL,
    crewMemberId              BIGINT        NULL,
    CONSTRAINT ch_CrewClimbBoard_crewClimbIsDeleted CHECK (crewClimbisDeleted IN ('Y', 'N')),
    CONSTRAINT ch_CrewClimbBoard_crewClimbIsEnded CHECK (crewClimbIsEnded IN ('Y', 'N')),
    CONSTRAINT pk_CrewClimbBoard_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CrewMember
(
    id                       BIGINT AUTO_INCREMENT   NOT NULL,
    crewMemberJoinDate       DATETIME NOT NULL,
    crewMemberRoleUpdateDate DATETIME NULL,
    crewId                   BIGINT   NOT NULL,
    cumId                    BIGINT   NOT NULL,
    crewRoleId               BIGINT   NOT NULL,
    CONSTRAINT pk_CrewMember_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CrewApplyHistory
(
    id                         BIGINT AUTO_INCREMENT     NOT NULL,
    crewId                     BIGINT     NOT NULL,
    crewApplyHistoryDate       DATETIME   NOT NULL,
    crewApplyHistoryIsAccepted VARCHAR(1) NULL COMMENT 'Y,N',
    crewApplyHistoryAcceptDate DATETIME   NULL,
    cumId                      BIGINT     NOT NULL,
    CONSTRAINT ch_CrewApplyHistory_crewApplyHistoryIsAccepted CHECK (crewApplyHistoryIsAccepted IN ('Y', 'N')),
    CONSTRAINT pk_CrewApplyHistory_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CrewApply
(
    id            BIGINT AUTO_INCREMENT   NOT NULL,
    crewApplyDate DATETIME NOT NULL,
    crewId        BIGINT   NOT NULL,
    cumId         BIGINT   NOT NULL,
    CONSTRAINT pk_CrewApply_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Crew
(
    id             BIGINT AUTO_INCREMENT       NOT NULL,
	  crewName       VARCHAR(255) NOT NULL,
    crewIsRecruit  VARCHAR(1)   NOT NULL DEFAULT 'Y' COMMENT 'Y,N',
    crewCreateDate DATETIME     NOT NULL,
    crewMaxPeople  INTEGER      NOT NULL DEFAULT 5,
    crewIntro      VARCHAR(255) NULL,
    crewIsDeleted  varchar(1)   NOT NULL DEFAULT 'N' COMMENT 'Y,N',
    crewBanDate    INTEGER      NOT NULL DEFAULT 30,
    crewRankGauge  BIGINT       NOT NULL,
    CONSTRAINT pk_Crew_id PRIMARY KEY (id),
    CONSTRAINT ch_Crew_crewIsRecruit CHECK (crewIsRecruit IN ('Y', 'N')),
    CONSTRAINT ch_Crew_crewIsDeleted CHECK (crewIsDeleted IN ('Y', 'N'))
);

CREATE TABLE IF NOT EXISTS CrewClimbRecord
(
    id                        BIGINT AUTO_INCREMENT     NOT NULL,
    crewClimbHistoryIsSucceed VARCHAR(1) NULL COMMENT 'Y,N',
    crewClimbId               BIGINT     NOT NULL,
    crewMemberId              BIGINT     NULL,
    frtrlId                   VARCHAR(10) NULL,
    CONSTRAINT pk_CrewClimbRecord_id PRIMARY KEY (id),
    CONSTRAINT ch_CrewClimbRecord_crewClimbHistoryIsSucceed CHECK (crewClimbHistoryIsSucceed IN ('Y', 'N'))
);

CREATE TABLE IF NOT EXISTS CrewMemberRole
(
    id           BIGINT AUTO_INCREMENT       NOT NULL,
    crewRoleName VARCHAR(255) NULL
    CONSTRAINT pk_CrewMemberRole_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CrewMemberAuth
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    crewRoleAuth VARCHAR(255)          NOT NULL,
    crewRoleId   BIGINT                NOT NULL,
    CONSTRAINT pk_CrewMemberAuth_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS CrewMemberHistory
(
    id                               BIGINT AUTO_INCREMENT        NOT NULL,
    crewRoleId                       BIGINT        NOT NULL,
    crewId                           BIGINT        NOT NULL,
    crewMemberHistoryJoinDate        DATETIME      NOT NULL,
    crewMemberHistoryStateUpdateDate DATETIME      NULL,
    crewMemberHistoryState           VARCHAR(255)  NOT NULL COMMENT '1: 강퇴, 2: 탈퇴, 3: 가입, 4: 등급변경',
    crewMemberHistoryUpdateReason    VARCHAR(2000) NULL,
    cumId                            BIGINT        NOT NULL,
    CONSTRAINT pk_CrewMemberHistory_id PRIMARY KEY (id),
    CONSTRAINT ch_CrewMemberHistory_crewMemberHistoryState CHECK (crewMemberHistoryState IN ('BANNED', 'QUITED', 'JOINED', 'ROLECHANGED'))
);

CREATE TABLE IF NOT EXISTS Mountain (
	frtrlId	varchar(10)	NOT NULL,
	frtrlNm	varchar(20)	NOT NULL,
	ctpvNm	varchar(20)	NULL,
	addrNm	varchar(255)	NULL,
	lat	double	NOT NULL,
	lot	double	NOT NULL,
	aslAltide	double	NULL,
	crtrDt	DATETIME	NULL,
	mtnCd	bigint	NULL,
	stampPath	varchar(255)	NULL,
	stampUpdateDate	DATETIME	NULL,
	CONSTRAINT pk_Mountain_frtrlId PRIMARY KEY (frtrlId)
);

CREATE TABLE IF NOT EXISTS MountainImage (
	id	bigint AUTO_INCREMENT NOT NULL,
	renaming	varchar(255)	NULL,
	originName	varchar(255)	NULL,
	path	varchar(255)	NULL,
	frtrlId	varchar(10)	NULL,
	CONSTRAINT pk_MountainImage_id PRIMARY KEY (id)
);



CREATE TABLE IF NOT EXISTS Course (
	poiId	varchar(10)	NOT NULL,
	createDate	DATETIME	NULL,
	lat	double	NOT NULL,
	lot	double	NOT NULL,
	aslAltide	double	NOT NULL,
	placeNm	varchar(255)	NULL,
	dscrtCn	varchar(255)	NULL,
	orgnExmnnPrgrsDrcntNm	varchar(255)	NULL,
	sgnpstDstn1Nm	varchar(255)	NULL,
	orgnSgnpstDstn1DrcntNm	varchar(255)	NULL,
	sgnpstDstn2Nm	varchar(255)	NULL,
	orgnSgnpstDstn2DrcntNm	varchar(255)	NULL,
	sgnpstDstn3Nm	varchar(255)	NULL,
	orgnSgnpstDstn3DrcntNm	varchar(255)	NULL,
	sgnpstDstn4Nm	varchar(255)	NULL,
	orgnSgnpstDstn4DrcntNm	varchar(255)	NULL,
	orgnSgnpstDstn1DrcntCd	varchar(255)	NULL,
	orgnSgnpstDstn2DrcntCd	varchar(255)	NULL,
	orgnSgnpstDstn3DrcntCd	varchar(255)	NULL,
	orgnSgnpstDstn4DrcntCd	varchar(255)	NULL,
	crtrDt	DATETIME	NULL,
	stampPath	varchar(255)	NULL,
	stampUpdateDate	DATETIME	NULL,
	orgnPlaceTpeCd	varchar(10)	NOT NULL,
	frtrlId	varchar(10)	NOT NULL,
	rankId	bigint	NOT NULL,
	CONSTRAINT pk_Course_poiId PRIMARY KEY (poiId)
);

CREATE TABLE IF NOT EXISTS CourseDifficulty (
	id	bigint AUTO_INCREMENT	NOT NULL,
	rank	varchar(5)	NOT NULL,
	CONSTRAINT pk_CourseDifficulty_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS RecommendGear (
	id	bigint AUTO_INCREMENT	NOT NULL,
	name	varchar(255)	NOT NULL,
	frtrlId	varchar(10)	NULL,
	CONSTRAINT pk_RecommendGear_id PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS PlaceCodeStandard (
	orgnPlaceTpeCd	varchar(10)	NOT NULL,
	orgnPlaceTpeNm	varchar(255)	NULL,
	CONSTRAINT pk_PlaceCodeStandard_orgnPlaceTpeCd PRIMARY KEY (orgnPlaceTpeCd)
);


CREATE TABLE NoticeBoard
(
    id         BIGINT AUTO_INCREMENT        NOT NULL,
    title      VARCHAR(255)  NOT NULL,
    content    VARCHAR(2000) NOT NULL,
    createDate DATETIME      NOT NULL,
    modifyDate DATETIME      NULL,
    isDeleted  VARCHAR(1)    NOT NULL DEFAULT 'N',
    cumId      BIGINT        NOT NULL,
    CONSTRAINT pk_NoticeBoard_id PRIMARY KEY (id),
    CONSTRAINT ch_NoticeBoard_isDeleted CHECK (isDeleted IN ('Y', 'N'))
);



CREATE TABLE Comment
(
    id           BIGINT AUTO_INCREMENT       NOT NULL,
    content      VARCHAR(255) NOT NULL,
    createDate   DATETIME     NOT NULL,
    modifyDate   DATETIME     NULL,
    cumId        BIGINT       NOT NULL,
    pid          BIGINT       NULL,
    postId       BIGINT       NULL,
    noticePostId BIGINT       NULL,
    crewPostId   BIGINT       NULL,
    CONSTRAINT pk_Comment_id PRIMARY KEY (id)
);

CREATE TABLE Board
(
    id         BIGINT AUTO_INCREMENT        NOT NULL,
    title      VARCHAR(255)  NOT NULL,
    content    VARCHAR(2000) NOT NULL,
    createDate DATETIME      NOT NULL,
    modifyDate DATETIME      NULL,
    isDeleted  VARCHAR(1)    NOT NULL DEFAULT 'N',
    cumId      BIGINT        NOT NULL,
    CONSTRAINT pk_Board_id PRIMARY KEY (id),
    CONSTRAINT ch_Board_isDeleted CHECK (isDeleted IN ('Y', 'N'))
);



CREATE TABLE BoardImage
(
    id         BIGINT AUTO_INCREMENT       NOT NULL,
    originName VARCHAR(255) NOT NULL,
    renaming   VARCHAR(255) NOT NULL,
    thumbnail  VARCHAR(255) NULL,
    path       VARCHAR(255) NOT NULL,
    postId     BIGINT       NOT NULL,
    CONSTRAINT pk_BoardImage_id PRIMARY KEY (id)
);



CREATE TABLE CrewBoard
(
    id         BIGINT AUTO_INCREMENT        NOT NULL,
    title      VARCHAR(255)  NOT NULL,
    content    VARCHAR(2000) NOT NULL,
    createDate DATETIME      NOT NULL,
    modifyDate DATETIME      NULL,
    isDeleted  VARCHAR(1)    NOT NULL DEFAULT 'N',
    isNotice   VARCHAR(1)    NOT NULL DEFAULT 'N',
    crewId     BIGINT        NOT NULL,
    cumId      BIGINT        NOT NULL,
    CONSTRAINT pk_CrewBoard_id PRIMARY KEY (id),
    CONSTRAINT ch_CrewBoard_isDeleted CHECK (isDeleted IN ('Y', 'N')),
    CONSTRAINT ch_CrewBoard_isNotice CHECK (isNotice IN ('Y', 'N'))
);

CREATE TABLE Likes
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    cumId      BIGINT NOT NULL,
    postId     BIGINT NULL,
    crewPostId BIGINT NULL,
    CONSTRAINT pk_Likes_id PRIMARY KEY (id)
);



CREATE TABLE CrewBoardImage
(
    id         BIGINT AUTO_INCREMENT       NOT NULL,
    originName VARCHAR(255) NOT NULL,
    renaming   VARCHAR(255) NOT NULL,
    thumbnail  VARCHAR(255) NULL,
    path       VARCHAR(255) NOT NULL,
    postId     BIGINT       NOT NULL,
    CONSTRAINT pk_CrewBoardImage_id PRIMARY KEY (id)
);



CREATE TABLE NoticeBoardImage
(
    id         BIGINT AUTO_INCREMENT       NOT NULL,
    originName VARCHAR(255) NOT NULL,
    renaming   VARCHAR(255) NOT NULL,
    thumbnail  VARCHAR(255) NULL,
    path       VARCHAR(255) NOT NULL,
    postId     BIGINT       NOT NULL,
    CONSTRAINT pk_NoticeBoardImage_id PRIMARY KEY (id)
);



CREATE TABLE ReportTargetType (
id BIGINT AUTO_INCREMENT NOT NULL,
reportTargetName VARCHAR(30) NOT NULL ,
CONSTRAINT pk_ReportTargetType PRIMARY KEY (id),
CONSTRAINT uq_ReportTargetType_name UNIQUE (reportTargetName)
);

CREATE TABLE ReportCategory (
id BIGINT AUTO_INCREMENT NOT NULL,
reportName VARCHAR(30) NOT NULL,
countStandard INTEGER NOT NULL,
CONSTRAINT pk_ReportCategory PRIMARY KEY (id),
CONSTRAINT uq_ReportCategory_name UNIQUE (reportName)
);

CREATE TABLE ReportCategoryStandard (
id BIGINT AUTO_INCREMENT NOT NULL,
reportTargetId BIGINT NOT NULL,
categoryId BIGINT NOT NULL,
CONSTRAINT pk_ReportCategoryStandard PRIMARY KEY (id)
);

CREATE TABLE Report (
id BIGINT NOT NULL auto_increment,
date DATETIME NOT NULL,
type VARCHAR(20) NOT NULL ,
adminId VARCHAR(255) NULL,
reason VARCHAR(2000) NOT NULL,
isAccepted VARCHAR(1) NULL DEFAULT 'N',
reportedId BIGINT NOT NULL,
categoryId BIGINT NOT NULL,
reportId BIGINT NOT NULL,
crewPostId BIGINT NULL,
replyId BIGINT NULL,
postId BIGINT NULL,
suspension_cycle INT NOT NULL DEFAULT 0,
CONSTRAINT pk_Report PRIMARY KEY (id),
CONSTRAINT ch_Report_IsAccepted CHECK (isAccepted IN ('Y','N'))
);

CREATE TABLE Blacklist (
id BIGINT NOT NULL auto_increment,
createDate DATETIME NOT NULL,
adminId BIGINT NOT NULL,
reportReasonId BIGINT NOT NULL,
memberId BIGINT NULL,
CONSTRAINT pk_Blacklist PRIMARY KEY (id)
);

CREATE TABLE Ban (
id BIGINT NOT NULL auto_increment,
reason VARCHAR(255) NOT NULL,
startDate DATETIME NOT NULL,
endDate DATETIME NOT NULL,
userId BIGINT NOT NULL,
adminId BIGINT NOT NULL,
reportReasonId BIGINT NOT NULL,
CONSTRAINT pk_Ban PRIMARY KEY (id),
CONSTRAINT ch_Ban_Date CHECK (endDate > startDate)
);


create table ProfileOfMember (
	id bigint not null auto_increment,
	dirPath varchar(255),
	filePath varchar(255),
	cumId bigint,
	constraint pk_ProfileOfMember_id primary key(id)
);


/* ==================== 외래키 제약조건 생성 ============================= */

alter table Member
add constraint fk_Member_Crew_crewId foreign key(crewId) references Crew (id) ON DELETE SET NULL,
add constraint fk_Member_MemberStatus_mtRankId foreign key(memStsId) references MemberStatus(id) ON DELETE SET NULL ,
add constraint fk_Member_MemberRank_frtrlId foreign key(memRankId) references MemberRank (id) ;

alter table MountainRank
add constraint fk_MountainRank_Member_cumId foreign key(cumId) references Member (id),
add constraint fk_MountainRank_MountainRankStandard_mtRankId foreign key(mtRankId) references MountainRankStandard(id) ON DELETE SET NULL,
add constraint fk_MountainRank_Mountain_frtrlId foreign key(frtrlId) references Mountain(frtrlId);

alter table MountainBookmark
add constraint fk_MountainBookmark_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_MountainBookmark_Mountain_frtrlId foreign key(frtrlId) references Mountain (frtrlId);

alter table MountainStamp
add constraint fk_MountainStamp_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_MountainStamp_Mountain_frtrlId foreign key(frtrlId) references Mountain(frtrlId);

alter table MountainReview
add constraint fk_MountainReview_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_MountainReview_Mountain_frtrlId foreign key(frtrlId) references Mountain(frtrlId);

alter table CourseStamp
add constraint fk_CourseStamp_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_CourseStamp_Course_frtrlId foreign key(poiId) references Course(poiId);

alter table CourseBookmark
add constraint fk_CourseBookmark_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_CourseBookmark_Course_frtrlId foreign key(poiId) references Course(poiId);

alter table CourseReview
add constraint fk_CourseReview_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_CourseReview_Course_frtrlId foreign key(poiId) references Course(poiId);

alter table ClimbCheck
add constraint fk_ClimbCheck_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_ClimbCheck_Mountain_frtrlId foreign key(frtrlId) references Mountain(frtrlId),
add constraint fk_ClimbCheck_Course_frtrlId foreign key(poiId) references Course(poiId);

alter table ClimbRecord
add constraint fk_ClimbRecord_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_ClimbRecord_Mountain_frtrlId foreign key(frtrlId) references Mountain(frtrlId),
add constraint fk_ClimbRecord_Course_frtrlId foreign key(poiId) references Course(poiId);

alter table MemberAuthority
add constraint fk_MemberAuthority_Member_cumId foreign key(cumId) references Member  (id),
add constraint fk_MemberAuthority_AuthorityList_cumId foreign key(authId) references AuthorityList  (id) ON DELETE SET NULL;

alter table LoginFailureRecord
add constraint fk_LoginFailureRecord_Member_cumId foreign key(cumId) references Member  (id);

alter table LoginRecord
add constraint fk_LoginRecord_Member_cumId foreign key(cumId) references Member  (id);



ALTER TABLE CrewMember
    ADD CONSTRAINT fk_CrewMember_Crew_crewId FOREIGN KEY (crewId) REFERENCES Crew (id),
    ADD CONSTRAINT fk_CrewMember_Member_cumId FOREIGN KEY (cumId) REFERENCES Member (id),
    ADD CONSTRAINT fk_CrewMember_CrewMemberRole_crewRoleId FOREIGN KEY (crewRoleId) REFERENCES CrewMemberRole (id);

ALTER TABLE CrewApplyHistory
    ADD CONSTRAINT fk_CrewApplyHistory_Member_cumId FOREIGN KEY (cumId) REFERENCES Member (id);

ALTER TABLE CrewApply
    ADD CONSTRAINT fk_CrewApply_Crew_crewId FOREIGN KEY (crewId) REFERENCES Crew (id),
    ADD CONSTRAINT fk_CrewApply_Member_cumId FOREIGN KEY (cumId) REFERENCES Member (id);

ALTER TABLE Crew
    ADD CONSTRAINT fk_Crew_CrewRankStandard_crewRankGauge FOREIGN KEY (crewRankGauge) REFERENCES CrewRankStandard (id);

ALTER TABLE CrewMemberHistory
    ADD CONSTRAINT fk_CrewMemberHistory_Member_cumId FOREIGN KEY (cumId) REFERENCES Member (id);

ALTER TABLE CrewClimbBoard
    ADD CONSTRAINT fk_CrewClimbBoard_Crew_crewId FOREIGN KEY (crewId) REFERENCES Crew (id),
    ADD CONSTRAINT fk_CrewClimbBoard_Mountain_frtrlId FOREIGN KEY (frtrlId) REFERENCES Mountain (frtrlId),
    ADD CONSTRAINT fk_CrewClimbBoard_CrewMember_crewMemberId FOREIGN KEY (crewMemberId) REFERENCES CrewMember (id) ON DELETE SET NULL;

ALTER TABLE CrewClimbRecord
    ADD CONSTRAINT fk_CrewClimbRecord_CrewClimbBoard_crewClimbId FOREIGN KEY (crewClimbId) REFERENCES CrewClimbBoard (id),
    ADD CONSTRAINT fk_CrewClimbRecord_Mountain_frtrlId FOREIGN KEY (frtrlId) REFERENCES Mountain (frtrlId),
    ADD CONSTRAINT fk_CrewClimbRecord_CrewMember_crewMemberId FOREIGN KEY (crewMemberId) REFERENCES CrewMember (id) ON DELETE SET NULL;

ALTER TABLE CrewMemberAuth
    ADD CONSTRAINT fk_CrewMemberAuth_CrewMemberRole_crewRoleId FOREIGN KEY (crewRoleId) REFERENCES CrewMemberRole (id);


ALTER TABLE MountainImage ADD CONSTRAINT fk_MountainImage_Mountain_frtrlId 
	FOREIGN KEY (frtrlId) REFERENCES Mountain(frtrlId);
	
ALTER TABLE Course ADD CONSTRAINT fk_Course_PlaceCodeStandard_orgnPlaceTpeCd 
	FOREIGN KEY (orgnPlaceTpeCd) REFERENCES PlaceCodeStandard(orgnPlaceTpeCd) 
	ON DELETE CASCADE;
	
ALTER TABLE Course ADD CONSTRAINT fk_Course_Mountain_frtrlId 
	FOREIGN KEY (frtrlId) REFERENCES Mountain(frtrlId);
	
ALTER TABLE Course ADD CONSTRAINT fk_Course_CourseDifficulty_rankId 
	FOREIGN KEY (rankId) REFERENCES CourseDifficulty(id);
	
ALTER TABLE RecommendGear ADD CONSTRAINT fk_RecommendGear_Mountain_frtrlId 
	FOREIGN KEY (frtrlId) REFERENCES Mountain(frtrlId);

ALTER TABLE NoticeBoard 
    ADD CONSTRAINT fk_NoticeBoard_Member_cumId FOREIGN KEY (cumId) REFERENCES Member(id);
    
ALTER TABLE CrewBoard
    ADD CONSTRAINT fk_CrewBoard_Crew_crewId FOREIGN KEY (crewId) REFERENCES Crew(id),
    ADD CONSTRAINT fk_CrewBoard_CrewMember_cumId FOREIGN KEY (cumId) REFERENCES CrewMember(id);
    
ALTER TABLE Board
    ADD CONSTRAINT fk_Board_Member_cumId FOREIGN KEY (cumId) REFERENCES Member(id);

ALTER TABLE Likes
    ADD CONSTRAINT fk_Likes_Member_cumId FOREIGN KEY (cumId) REFERENCES Member(id),
    ADD CONSTRAINT fk_Likes_Board_postId FOREIGN KEY (postId) REFERENCES Board(id),
    ADD CONSTRAINT fk_Likes_CrewBoard_crewPostId FOREIGN KEY (crewPostId) REFERENCES CrewBoard(id);

ALTER TABLE Comment
    ADD CONSTRAINT fk_Comment_Member_cumId FOREIGN KEY (cumId) REFERENCES Member(id),
    ADD CONSTRAINT fk_Comment_Comment_pid FOREIGN KEY (pid) REFERENCES Comment(id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_Comment_Board_postId FOREIGN KEY (postId) REFERENCES Board(id),
    ADD CONSTRAINT fk_Comment_NoticeBoard_noticePostId FOREIGN KEY (noticePostId) REFERENCES NoticeBoard(id),
    ADD CONSTRAINT fk_Comment_CrewBoard_crewPostId FOREIGN KEY (crewPostId) REFERENCES CrewBoard(id);

ALTER TABLE BoardImage
    ADD CONSTRAINT fk_BoardImage_Board_postId FOREIGN KEY (postId) REFERENCES Board(id);

ALTER TABLE CrewBoardImage
    ADD CONSTRAINT fk_CrewBoardImage_CrewBoard_postId FOREIGN KEY (postId) REFERENCES CrewBoard(id);

ALTER TABLE NoticeBoardImage
    ADD CONSTRAINT fk_NoticeBoardImage_NoticeBoard_postId FOREIGN KEY (postId) REFERENCES NoticeBoard(id);

ALTER TABLE ReportCategoryStandard
ADD CONSTRAINT fk_ReportCategoryStandard_ReportTargetType_reportTargetId
FOREIGN KEY (reportTargetId) REFERENCES ReportTargetType (id);

ALTER TABLE ReportCategoryStandard
ADD CONSTRAINT fk_ReportCategoryStandard_ReportCategory_categoryId
FOREIGN KEY (categoryId) REFERENCES ReportCategory (id);


ALTER TABLE Report
ADD CONSTRAINT fk_Report_Member_reportedId
FOREIGN KEY (reportedId) REFERENCES Member (id);

ALTER TABLE Report
ADD CONSTRAINT fk_Report_MEMBER_reportId
FOREIGN KEY (reportId) REFERENCES Member (id);

ALTER TABLE Report
ADD CONSTRAINT fk_Report_ReportCategory_categoryId
FOREIGN KEY (categoryId) REFERENCES ReportCategory (id);

ALTER TABLE Report
ADD CONSTRAINT fk_Report_CrewBoard_crewPostId
FOREIGN KEY (crewPostId) REFERENCES CrewBoard (id);

ALTER TABLE Report
ADD CONSTRAINT fk_Report_Comment_replyId
FOREIGN KEY (replyId) REFERENCES Comment (id);

ALTER TABLE Report
ADD CONSTRAINT fk_Report_Board_postId
FOREIGN KEY (postId) REFERENCES Board (id);


ALTER TABLE Blacklist
ADD CONSTRAINT fk_Blacklist_Member_adminId
FOREIGN KEY (adminId) REFERENCES Member (id);

ALTER TABLE Blacklist
ADD CONSTRAINT fk_Blacklist_Member_memberId
FOREIGN KEY (memberId) REFERENCES Member (id);

ALTER TABLE Blacklist
ADD CONSTRAINT FK_Blacklist_ReportCategory_reportReasonId
FOREIGN KEY (reportReasonId) REFERENCES ReportCategory (id);


ALTER TABLE Ban
ADD CONSTRAINT fk_Ban_Member_userId
FOREIGN KEY (userId) REFERENCES Member (id);

ALTER TABLE Ban
ADD CONSTRAINT fk_Ban_Member_adminId
FOREIGN KEY (adminId) REFERENCES Member (id);

ALTER TABLE Ban
ADD CONSTRAINT fk_Ban_ReportCategory_reportReasonId
FOREIGN KEY (reportReasonId) REFERENCES ReportCategory (id);

alter table ProfileOfMember 
add constraint fk_ProfileOfMember_Member_cumId
foreign key(cumId) references Member(id);