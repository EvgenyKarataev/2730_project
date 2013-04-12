-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2013 年 04 月 12 日 16:17
-- 服务器版本: 5.5.24-log
-- PHP 版本: 5.4.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `2730_project`
--
CREATE DATABASE `2730_project` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `2730_project`;

-- --------------------------------------------------------

--
-- 表的结构 `sharable_file`
--

CREATE TABLE IF NOT EXISTS `sharable_file` (
  `userId` int(11) NOT NULL,
  `filename` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`userId`,`filename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- 转存表中的数据 `sharable_file`
--

INSERT INTO `sharable_file` (`userId`, `filename`) VALUES
(1, 'AplusB.avi'),
(1, 'AplusB.mp4');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`userId`, `userName`, `password`) VALUES
(1, 'user1', 'user1');

--
-- 限制导出的表
--

--
-- 限制表 `sharable_file`
--
ALTER TABLE `sharable_file`
  ADD CONSTRAINT `sharable_file_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
