CREATE TABLE `place_opening_hours` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `sunday` varchar(255) NOT NULL,
    `monday` varchar(255) NOT NULL,
    `tuesday` varchar(255) NOT NULL,
    `wednesday` varchar(255) NOT NULL,
    `thursday` varchar(255) NOT NULL,
    `friday` varchar(255) NOT NULL,
    `saturday` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci