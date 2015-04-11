CREATE TABLE IF NOT EXISTS `hwid_bans` (
  `bans` int(255) NOT NULL AUTO_INCREMENT,
  `HWID` varchar(32) DEFAULT NULL,
  `HWIDSecond` varchar(32) DEFAULT NULL,
  `expiretime` int(11) NOT NULL DEFAULT '0',
  `comments` varchar(255) DEFAULT '',
  PRIMARY KEY (`bans`),
  UNIQUE KEY `HWID` (`HWID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
SET FOREIGN_KEY_CHECKS=0;