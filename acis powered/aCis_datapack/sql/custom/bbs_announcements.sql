CREATE TABLE IF NOT EXISTS `bbs_announcements` (
  `announce_id` INT NOT NULL default 0,
  `announce_title` varchar(150),
  `announce_text` varchar(1000),
  `announce_date` varchar(10),
  `author` varchar(16),
  PRIMARY KEY (`announce_id`)
);

INSERT INTO `bbs_announcements` VALUES ('1', ' Welcome to L2JxTreme', 'Hello dear user.<br1>\r\nWelcome to L2Jxtreme!<br1>\r\nPlease configure it by your desire :)<br1>\r\nI wish you best luck with your server!<br>\r\nIf you need support feel free to contact me at <font color=\"88ff11\">l2jxtreme.com</font><br>\r\n<br><br>\r\n<font color=\"ff3322\">This message can be deleted directly from database!</font>', '22/08/2014', 'L2JxTteme Team');
