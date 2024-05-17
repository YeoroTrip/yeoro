CREATE TABLE `user` (
    `id` int NOT NULL AUTO_INCREMENT,
    `user_id` varchar(20) NOT NULL,
    `password` varchar(50) NOT NULL,
    `nickname` varchar(20) NOT NULL,
    `picture_url` text,
    `provider_type` enum('google','kakao') DEFAULT NULL,
    `is_admin` tinyint(1) NOT NULL DEFAULT '0',
    `refresh_token` varchar(255) DEFAULT NULL,
    `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`),
    UNIQUE KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;