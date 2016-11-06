CREATE TABLE `student_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `coach_schedule_id` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_student_schedule_user_id` (`user_id`),
  KEY `FK_student_schedule_coach_schedule_id` (`coach_schedule_id`),
  CONSTRAINT `FK_student_schedule_user_id` FOREIGN KEY (`user_id`) REFERENCES `coach_basic_infos_xueyuan` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_student_schedule_coach_schedule_id` FOREIGN KEY (`coach_schedule_id`) REFERENCES `coach_schedule` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;