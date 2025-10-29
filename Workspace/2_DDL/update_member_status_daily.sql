-- 이벤트 스케줄러 활성화
-- 여기는 rds를 사용하고 있어서 루트계정 자체가 제한되어서 rds의 파라미터 그룹에서 허용설정
SET GLOBAL event_scheduler = ON;

-- 오늘 자정부터 매일 실행되는 이벤트 생성
DELIMITER $$

CREATE EVENT update_member_status_daily
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_DATE
DO
BEGIN
    UPDATE member m
    JOIN ban b ON m.id = b.userId
    SET m.memStsId = 1
    WHERE b.endDate <= NOW()
      AND m.memStsId != 1
      AND m.memStsId != 5;
END$$

DELIMITER ;