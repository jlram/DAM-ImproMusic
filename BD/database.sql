-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 03, 2018 at 04:19 PM
-- Server version: 5.7.19
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `impromusic`
--

-- --------------------------------------------------------

--
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
CREATE TABLE IF NOT EXISTS `challenge` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `ID_song` int(11) DEFAULT NULL,
  `ID_musician` int(11) DEFAULT NULL,
  `ID_winner` int(11) DEFAULT '0',
  `creat_date` date DEFAULT NULL,
  `fin_date` date DEFAULT NULL,
  `descr` text,
  PRIMARY KEY (`ID`),
  KEY `ID_song` (`ID_song`),
  KEY `ID_musician` (`ID_musician`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `challenge`
--

INSERT INTO `challenge` (`ID`, `name`, `ID_song`, `ID_musician`, `ID_winner`, `creat_date`, `fin_date`, `descr`) VALUES
(1, '1234234', 13, 2, NULL, '2018-05-24', '2018-05-27', '1234123412341234');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_musician` int(11) DEFAULT NULL,
  `com_date` date DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`ID`),
  KEY `ID_musician` (`ID_musician`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `musician`
--

DROP TABLE IF EXISTS `musician`;
CREATE TABLE IF NOT EXISTS `musician` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(15) DEFAULT NULL,
  `log_date` date DEFAULT NULL,
  `user_type` varchar(10) DEFAULT NULL,
  `id_pic` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `musician`
--

INSERT INTO `musician` (`ID`, `username`, `password`, `log_date`, `user_type`, `id_pic`) VALUES
(1, 'admin', 'admin', '2018-04-05', 'admin', 2),
(2, 'invitado', 'invitado', '2018-04-09', 'invitado', 1);

-- --------------------------------------------------------

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
CREATE TABLE IF NOT EXISTS `participation` (
  `ID` int 11 NOT NULL AUTO_INCREMENT,
  `ID_musician` int(11) NOT NULL,
  `ID_chall` int(11) NOT NULL,
  `part_date` date DEFAULT NULL,
  `votes` int(11) DEFAULT '0',
  `youtube` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_musician`,`ID_chall`),
  KEY `ID_chall` (`ID_chall`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `vote`;

CREATE TABLE `vote` (
  `ID_part` int(15) NOT NULL,
  `ID_musician` int(15) NOT NULL,
  `ID_chall` int(15) NOT NULL,
    PRIMARY KEY (`ID_musician`,`ID_chall`),
  KEY `ID_chall` (`ID_chall`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `song`
--

DROP TABLE IF EXISTS `song`;
CREATE TABLE IF NOT EXISTS `song` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) DEFAULT NULL,
  `author` varchar(25) DEFAULT NULL,
  `album` varchar(25) DEFAULT NULL,
  `genre` varchar(15) DEFAULT NULL,
  `cover` varchar(100) DEFAULT NULL,
  `link` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `song`
--

INSERT INTO `song` (`ID`, `name`, `author`, `album`, `genre`, `cover`, `link`) VALUES
(1, 'Funk', 'Boyish Giggles', 'Funk & Soul', 'Funk', 'http://hyperbruh.000webhostapp.com/music/funk.jpg', 'http://hyperbruh.000webhostapp.com/music/funk.mp3'),
(2, 'Post-Rock', 'milesov', 'Pira', 'Post-Rock', 'http://hyperbruh.000webhostapp.com/music/post.jpg', 'http://hyperbruh.000webhostapp.com/music/post.mp3'),
(3, 'Blues', 'DansGame', 'The Blues Album', 'Blues', 'http://hyperbruh.000webhostapp.com/music/blues.jpg', 'http://hyperbruh.000webhostapp.com/music/blues.mp3'),
(4, 'Psicodelia', 'TriHard', 'KKorner', 'Psicodelia', 'http://hyperbruh.000webhostapp.com/music/psicodelia.jpg', 'http://hyperbruh.000webhostapp.com/music/psicodelia.mp3'),
(5, 'Half Sister', 'Protomartyr', 'Relatives in Descent', 'Shoegaze', 'http://hyperbruh.000webhostapp.com/music/relatives.jpg', 'http://hyperbruh.000webhostapp.com/music/halfsister.mp3'),
(6, 'BrotherMan BIll', 'TerribleTim', 'Spreads Love', 'Country', 'http://hyperbruh.000webhostapp.com/music/brotherman.jpg', 'http://hyperbruh.000webhostapp.com/music/brotherman.mp3'),
(7, 'Comfortably Numb', 'Pink Floyd', 'The Wall', 'Rock', 'http://hyperbruh.000webhostapp.com/music/thewall.jpg', 'http://hyperbruh.000webhostapp.com/music/comfortably.mp3');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
