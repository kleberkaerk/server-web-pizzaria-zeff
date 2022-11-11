CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(104) DEFAULT NULL,
  `stocked` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `price_rating` varchar(13) DEFAULT NULL,
  `type` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
