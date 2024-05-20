CREATE TABLE `place` (
     `id` bigint NOT NULL AUTO_INCREMENT,
     `google_id` varchar(255) DEFAULT NULL,
     `place_detail_id` bigint NOT NULL,
     `created_at` timestamp NULL DEFAULT NULL,
     `updated_at` timestamp NULL DEFAULT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY `google_id_UNIQUE` (`google_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci