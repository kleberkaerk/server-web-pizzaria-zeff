CREATE TABLE `purchased_products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `purchase_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjgr6qvq0y2ba3737jmy80gac` (`purchase_id`),
  CONSTRAINT `FKjgr6qvq0y2ba3737jmy80gac` FOREIGN KEY (`purchase_id`) REFERENCES `purchases` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
