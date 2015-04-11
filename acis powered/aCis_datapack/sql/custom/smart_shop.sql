CREATE TABLE `smart_shop` (
  `id` int(1) NOT NULL DEFAULT '0',
  `item_id` int(5) DEFAULT NULL,
  `item_enchant` int(5) DEFAULT NULL,
  `cost_item_id` int(5) DEFAULT NULL,
  `cost_item_count` bigint(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;