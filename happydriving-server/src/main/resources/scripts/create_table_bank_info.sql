
-- ----------------------------
-- Table structure for `coach_auditinfo`
-- ----------------------------
DROP TABLE IF EXISTS `bank_info`;
CREATE TABLE `bank_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `bank_name` varchar(255) NOT NULL,
  `bank_code` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

INSERT INTO `bank_info` (`bank_name`) VALUES ('中国银行');
INSERT INTO `bank_info` (`bank_name`) VALUES ('建设银行');
INSERT INTO `bank_info` (`bank_name`) VALUES ('工商银行');
INSERT INTO `bank_info` (`bank_name`) VALUES ('农业银行');