CREATE TABLE `notice` (
    `id` int NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `context` text,
    `hits` int DEFAULT '0',
    `is_important` tinyint(1) NOT NULL DEFAULT '0',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 초기 데이터 삽입
INSERT INTO `notice` (title, context, hits, is_important, created_at, updated_at) VALUES
('첫 번째 공지사항', '첫 번째 공지사항의 내용입니다.', 0, 1, NOW(), NOW()),
('두 번째 공지사항', '두 번째 공지사항의 내용입니다.', 0, 0, NOW(), NOW()),
('세 번째 공지사항', '세 번째 공지사항의 내용입니다.', 0, 1, NOW(), NOW()),
('네 번째 공지사항', '네 번째 공지사항의 내용입니다.', 0, 0, NOW(), NOW());