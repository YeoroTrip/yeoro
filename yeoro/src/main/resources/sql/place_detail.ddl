CREATE TABLE `place_detail` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `opening_hours_id` bigint NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `full_address` varchar(255) DEFAULT NULL,
    `latitude` decimal(20,17) DEFAULT NULL,
    `longitude` decimal(20,17) DEFAULT NULL,
    `category` varchar(255) DEFAULT NULL,
    `phone_number` varchar(25) DEFAULT NULL,
    `rating` decimal(2,1) DEFAULT NULL,
    `photo` varchar(255) DEFAULT NULL,
    `created_at` timestamp NULL DEFAULT NULL,
    `updated_at` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci