SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `character_rebirths`;
CREATE TABLE `character_rebirths` (
  `playerId` int(20) NOT NULL,
  `rebirthCount` int(2) NOT NULL,
  PRIMARY KEY (`playerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;