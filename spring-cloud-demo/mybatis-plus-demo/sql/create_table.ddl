CREATE TABLE `eng_cet6` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `word` varchar(50) DEFAULT NULL,
                            `phonetic` varchar(100) DEFAULT NULL,
                            `meaning` varchar(200) DEFAULT NULL,
                            `example_sentence` varchar(200) DEFAULT NULL COMMENT '例句',
                            `has_mastered` int DEFAULT NULL,
                            `insert_time` datetime DEFAULT NULL,
                            `update_time` datetime DEFAULT NULL,
                            `related_word` varchar(200) DEFAULT NULL COMMENT '只做一个冗余，但是不保存值',
                            `not_recognize` int DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `meaning_index` (`meaning`)
) ENGINE=InnoDB AUTO_INCREMENT=6529 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `eng_curr_position` (
                                     `id` int NOT NULL,
                                     `curr_pos` int DEFAULT NULL,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `eng_exa_sent` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `word_id` int DEFAULT NULL,
                                `example` varchar(200) DEFAULT NULL,
                                `insert_time` timestamp NULL DEFAULT NULL,
                                `last_update_time` timestamp NULL DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='例句';

CREATE TABLE `eng_pic` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `word_id` int DEFAULT NULL,
                           `fileName` varchar(100) DEFAULT NULL,
                           `filePath` varchar(200) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `eng_word_rel` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `word1_id` int DEFAULT NULL,
                                `word2_id` int DEFAULT NULL,
                                `insert_time` timestamp NULL DEFAULT NULL,
                                `last_update_time` timestamp NULL DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `word1_index` (`word1_id`),
                                KEY `word2_index` (`word2_id`)
) ENGINE=InnoDB AUTO_INCREMENT=549 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关联单词';
