INSERT INTO MemberStatus
VALUES
(null ,'정상'),
(null ,'탈퇴'),
(null ,'정지'),
(null ,'휴면상태'),
(null ,'블랙리스트');


INSERT INTO ScoreStandard
VALUES
(null ,'등산완료[하]', 1),
(null ,'등산완료[중]', 2),
(null ,'등산완료[상]', 3),
(null ,'봉우리 도장획득', 5),
(null ,'산 도장획득', 10);


INSERT INTO MemberRank
VALUES
(null ,'씨앗', 0),
(null ,'새싹이', 15),
(null ,'나뭇잎', 50),
(null ,'꽃잎', 100),
(null ,'열매', 180),
(null ,'황금씨앗', 400);


INSERT INTO MountainRankStandard
VALUES
(null ,'산신령'),
(null ,'봉우리마스터'),
(null ,'산길탐험가');


INSERT INTO ReportTargetType
VALUES
(null ,'댓글'),
(null ,'게시판'),
(null ,'회원');


INSERT INTO ReportCategory
VALUES
(null ,'성희롱', 0),
(null ,'성폭행', 0),
(null ,'폭행', 0),
(null ,'사기', 0),
(null ,'협박', 1),
(null ,'욕설', 3),
(null ,'도배글', 3),
(null ,'혐오컨텐츠', 3),
(null ,'노쇼', 5);
   

INSERT INTO CourseDifficulty
VALUES
(null ,'상'),
(null ,'중'),
(null ,'하'); 


INSERT INTO CrewMemberRole
VALUES
(null ,'크루원', null),
(null ,'크루장', '모집'),
(null ,'크루장', '강퇴');


INSERT INTO AuthorityList
VALUES
(null ,'사용자', null),
(null ,'관리자', null);

