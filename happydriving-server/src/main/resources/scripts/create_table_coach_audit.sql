
-- ----------------------------
-- Table structure for `coach_auditinfo`
-- ----------------------------
DROP TABLE IF EXISTS `coach_auditinfo`;
CREATE TABLE `coach_auditinfo` (
  `ID` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `audit_admin` varchar(255) NOT NULL,
  `audit_status` varchar(40) NOT NULL,
  `audit_message` varchar(255) NOT NULL,
  `audit_at` datetime DEFAULT NULL,
  `mark` tinyint(1) NOT NULL,
  `label` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;