CREATE DATABASE IF NOT EXISTS `heroes_app`;
USE `heroes_app`;

CREATE TABLE IF NOT EXISTS `guild` (
  `guild_id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(200) NOT NULL,
  PRIMARY KEY (`guild_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `hero` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `guild_id` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`guild_id`) REFERENCES guild(`guild_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `tavern` (
    `id` int(5) NOT NULL AUTO_INCREMENT,
    `name` varchar(200) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE `tavern_hero` (
    `hero_id` int(5) NOT NULL,
    `tavern_id` int(5) NOT NULL,
    FOREIGN KEY (`hero_id`) REFERENCES hero (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`tavern_id`) REFERENCES tavern (`id`) ON DELETE CASCADE,
    PRIMARY KEY (`hero_id`, `tavern_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `pet` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `health_points` int(3) NOT NULL,
  `damage` int(3) NOT NULL,
  `hero_id` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`hero_id`) REFERENCES hero(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

INSERT INTO `guild` (`guild_id`, `name`, `description`) VALUES
	(1, 'Guild of thieves', 'Just a little description about this guild');

INSERT INTO `hero` (`id`, `name`, `guild_id`) VALUES
	(1, 'Angular 2', 1),
	(2, 'Spring Boot', 1),
	(3, 'Java', 1),
	(4, 'Android', null);

INSERT INTO `pet` (`id`, `hero_id`, `name`, `health_points`, `damage`) VALUES
	(1, 1, 'Doggy', 100, 80),
	(2, 2, 'Tiger', 65, 90),
	(3, 3, 'Wolverine', 70, 60),
	(4, 4, 'Moskia', 85, 20);

INSERT INTO `tavern` (`id`, `name`) VALUES
    (1, 'Optima'),
    (2, 'The Slaughtered Lamb');

INSERT INTO `tavern_hero` (`hero_id`, `tavern_id`) VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (3, 2),
    (4, 2);