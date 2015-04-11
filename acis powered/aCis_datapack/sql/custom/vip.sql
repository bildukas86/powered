CREATE TABLE IF NOT EXISTS `vips` (
  `charId` int(10) NOT NULL,
  `time` bigint(30) NOT NULL,
  PRIMARY KEY (`charId`,`time`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;