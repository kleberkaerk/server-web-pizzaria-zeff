CREATE TABLE `purchases` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `card_name` varchar(45) DEFAULT NULL,
  `date_and_time` varchar(16) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `delivered` bit(1) DEFAULT NULL,
  `finished` bit(1) DEFAULT NULL,
  `payment_through_the_website` bit(1) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4hks8pus3jkqhuxl71v80pohr` (`address_id`),
  KEY `FKm0ndjymn9p747pfp4515pio8i` (`user_id`),
  CONSTRAINT `FK4hks8pus3jkqhuxl71v80pohr` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`),
  CONSTRAINT `FKm0ndjymn9p747pfp4515pio8i` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
