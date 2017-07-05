/*
 Navicat Premium Data Transfer

 Source Server         : repairsshop
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : localhost
 Source Database       : minishop_test

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : utf-8

 Date: 07/05/2017 23:45:21 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `appointments`
-- ----------------------------
DROP TABLE IF EXISTS `appointments`;
CREATE TABLE `appointments` (
  `id` varchar(40) NOT NULL,
  `user_id` varchar(40) NOT NULL,
  `item_id` varchar(40) NOT NULL,
  `book_date` date NOT NULL,
  `book_time` time NOT NULL,
  `state` varchar(20) NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `appointments`
-- ----------------------------
BEGIN;
INSERT INTO `appointments` VALUES ('04500f145f2e4e5e8ced9126fa91fbf6', '2a0cc4b7ed334287a5cde20840a7c9df', 'dae885ed2cc9421d8b4123becaaa9779', '2017-03-17', '10:30:00', 'Pending', null, '2017-03-17 23:49:17', '2017-03-17 23:49:17'), ('ae8976498df94032b47b7ff26e8b2db1', '2a0cc4b7ed334287a5cde20840a7c9df', 'f9c652b34b7544318e9672f71c376c48', '2017-05-04', '12:00:00', 'Pending', null, '2017-05-04 00:05:45', '2017-05-04 00:05:45');
COMMIT;

-- ----------------------------
--  Table structure for `assign_coupons`
-- ----------------------------
DROP TABLE IF EXISTS `assign_coupons`;
CREATE TABLE `assign_coupons` (
  `user_id` varchar(40) NOT NULL,
  `coupon_code` varchar(40) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `state` varchar(20) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`coupon_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `cart_items`
-- ----------------------------
DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items` (
  `cart_id` varchar(40) NOT NULL,
  `item_id` varchar(40) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `color` varchar(20) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `cart_items`
-- ----------------------------
BEGIN;
INSERT INTO `cart_items` VALUES ('2a0cc4b7ed334287a5cde20840a7c9df', 'f9c652b34b7544318e9672f71c376c48', '1', null, '2017-06-21 23:07:36', '2017-06-21 23:07:36');
COMMIT;

-- ----------------------------
--  Table structure for `carts`
-- ----------------------------
DROP TABLE IF EXISTS `carts`;
CREATE TABLE `carts` (
  `cart_id` varchar(40) NOT NULL,
  `total_price` decimal(10,0) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `carts`
-- ----------------------------
BEGIN;
INSERT INTO `carts` VALUES ('2a0cc4b7ed334287a5cde20840a7c9df', '100', '2017-03-18 22:36:43', '2017-03-18 22:36:43'), ('49ce5c4d95444fd1bc3bc0414eeb485b', '0', '2017-03-18 19:55:07', '2017-03-18 19:55:07');
COMMIT;

-- ----------------------------
--  Table structure for `coupons`
-- ----------------------------
DROP TABLE IF EXISTS `coupons`;
CREATE TABLE `coupons` (
  `code` varchar(40) NOT NULL,
  `coupon_name` varchar(50) DEFAULT NULL,
  `valid_date_from` date NOT NULL,
  `valid_date_to` date NOT NULL,
  `amount` decimal(15,2) NOT NULL DEFAULT '0.00',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `coupons`
-- ----------------------------
BEGIN;
INSERT INTO `coupons` VALUES ('39176703020695', '12', '2017-03-20', '2017-03-20', '10.00', '2017-03-20 17:29:32', '2017-03-20 17:29:32'), ('35178303220412', '12', '2017-03-20', '2017-03-20', '12.00', '2017-03-20 17:44:35', '2017-03-20 17:44:35');
COMMIT;

-- ----------------------------
--  Table structure for `dict`
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict` (
  `id` int(11) NOT NULL,
  `name` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `dict`
-- ----------------------------
BEGIN;
INSERT INTO `dict` VALUES ('10', '1212'), ('-5', '121'), ('19', null), ('-10', null), ('-3', null), ('-15', null), ('1', null), ('20', null);
COMMIT;

-- ----------------------------
--  Table structure for `item_categories`
-- ----------------------------
DROP TABLE IF EXISTS `item_categories`;
CREATE TABLE `item_categories` (
  `category_id` varchar(40) NOT NULL,
  `parent_category_id` varchar(40) NOT NULL,
  `name` varchar(50) NOT NULL,
  `order_by` int(11) DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `item_categories`
-- ----------------------------
BEGIN;
INSERT INTO `item_categories` VALUES ('130beca0cdd347348b03d78629aaa033', '0', 'HUAWEI', '0', '2017-03-17 11:50:08'), ('149e4fc379964fa0b1c2ff0919621b2f', '0', 'SUMSANG', '0', '2017-03-17 11:51:25'), ('18bce930467f43b2bccf21d03e5c78c4', '0', 'Moto', '5', '2017-03-21 14:05:41'), ('1f8e2d6caec24f37b13b6d32f5554df7', '987f3959dc55442aa0700f9f3d5bef15', 'iPhone6s Plus/iPad New ddd ', '0', '2017-03-17 11:53:31'), ('2ccf3e966e9a4247afaf7a2875ad0ed0', '0', 'iPad', '0', '2017-03-17 11:49:58'), ('31b0d87d5eea4a248d757b727075ef0c', '149e4fc379964fa0b1c2ff0919621b2f', 'Galaxy S6 Edge Plus G928F', '0', '2017-03-20 19:33:33'), ('396122d518384ff8960531b8375d324b', '987f3959dc55442aa0700f9f3d5bef15', 'iPhone6s', '0', '2017-03-17 11:53:22'), ('542f6c7c46b04aac9d130f09529f51cf', '2ccf3e966e9a4247afaf7a2875ad0ed0', 'iPad Mini4', '0', '2017-03-17 12:06:39'), ('5a8a0f83046a468aba06475737f61111', '987f3959dc55442aa0700f9f3d5bef15', 'iPhone6 Plus', '0', '2017-03-17 11:53:14'), ('5d083db4aaaf40ad852cc5736f84fae7', '18bce930467f43b2bccf21d03e5c78c4', 'Moto2', '2', '2017-03-21 14:06:24'), ('987f3959dc55442aa0700f9f3d5bef15', '0', 'iPhone', '0', '2017-03-17 11:49:42'), ('9c387cbded974216a3f99de9ae587680', '18bce930467f43b2bccf21d03e5c78c4', 'Moto1', '1', '2017-03-21 14:05:48'), ('b99c10134c7e4a36879a70f0e860895a', '987f3959dc55442aa0700f9f3d5bef15', 'iPhone6', '0', '2017-03-17 11:51:39');
COMMIT;

-- ----------------------------
--  Table structure for `items`
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items` (
  `item_id` varchar(40) NOT NULL,
  `item_name` varchar(100) NOT NULL,
  `item_list_image` varchar(100) DEFAULT NULL,
  `item_detail_image` varchar(100) DEFAULT NULL,
  `unit_price` decimal(20,0) NOT NULL,
  `discount_price` decimal(20,0) DEFAULT NULL,
  `description` text,
  `brand_id` varchar(40) NOT NULL,
  `brand_name` varchar(40) DEFAULT NULL,
  `model_id` varchar(40) NOT NULL,
  `model_name` varchar(40) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `items`
-- ----------------------------
BEGIN;
INSERT INTO `items` VALUES ('f9c652b34b7544318e9672f71c376c48', 'repair glass', '/item-images/thumbnail_images/iphone-repairs.png', '/item-images/detail_images/iphone-repairs-2-large.png', '100', '80', null, '987f3959dc55442aa0700f9f3d5bef15', 'iPhone', '396122d518384ff8960531b8375d324b', 'iPhone6s', '10', 'new', '2017-05-04 00:05:35', '2017-03-18 00:18:31');
COMMIT;

-- ----------------------------
--  Table structure for `mail_logs`
-- ----------------------------
DROP TABLE IF EXISTS `mail_logs`;
CREATE TABLE `mail_logs` (
  `id` varchar(40) NOT NULL,
  `m_from` varchar(50) NOT NULL,
  `m_to` varchar(500) NOT NULL,
  `state` varchar(20) NOT NULL,
  `comment` varchar(500) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `mail_logs`
-- ----------------------------
BEGIN;
INSERT INTO `mail_logs` VALUES ('18d5206c587f4a18a8e61ad97f8b7a03', '153718687@qq.com', 'whzlong@163.com', 'success', null, '2017-03-29 12:44:11'), ('427a79325ce2423693299318f10c9390', '153718687@qq.com', 'whzlong@163.com', 'success', null, '2017-04-04 22:01:45'), ('5b73e511162247d3b5a3ab57932384e8', '153718687@qq.com', 'whzlong@sina.cn', 'success', null, '2017-03-18 19:43:33'), ('9fe0c6b6b216407e8f8140fa47d425c8', '153718687@qq.com', 'whzlong@sina.cn', 'success', null, '2017-03-19 00:49:37');
COMMIT;

-- ----------------------------
--  Table structure for `order_details`
-- ----------------------------
DROP TABLE IF EXISTS `order_details`;
CREATE TABLE `order_details` (
  `order_id` bigint(20) NOT NULL,
  `item_id` varchar(40) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_price` decimal(10,0) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `order_details`
-- ----------------------------
BEGIN;
INSERT INTO `order_details` VALUES ('17040649958', 'f9c652b34b7544318e9672f71c376c48', '1', '100', '2017-04-06 23:40:53', '2017-04-06 23:40:53'), ('17040652832', 'f9c652b34b7544318e9672f71c376c48', '2', '100', '2017-04-06 23:45:00', '2017-04-06 23:45:00'), ('170318909691', 'f9c652b34b7544318e9672f71c376c48', '1', '100', '2017-03-18 23:03:54', '2017-03-18 23:03:54'), ('170328238756', 'f9c652b34b7544318e9672f71c376c48', '1', null, '2017-03-28 12:29:15', '2017-03-28 12:29:15'), ('170401143370', 'f9c652b34b7544318e9672f71c376c48', '1', null, '2017-04-01 00:17:46', '2017-04-01 00:17:46'), ('170406395298', 'f9c652b34b7544318e9672f71c376c48', '1', '100', '2017-04-06 22:48:53', '2017-04-06 22:48:53'), ('170406861358', 'f9c652b34b7544318e9672f71c376c48', '2', '100', '2017-04-06 23:34:01', '2017-04-06 23:34:01');
COMMIT;

-- ----------------------------
--  Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` bigint(20) NOT NULL,
  `user_id` varchar(40) NOT NULL,
  `pay_date` date DEFAULT NULL,
  `ship_address` varchar(400) DEFAULT NULL,
  `total_amount` decimal(15,0) DEFAULT NULL,
  `comment` varchar(300) DEFAULT NULL,
  `state` varchar(20) NOT NULL,
  `coupon_code` varchar(40) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `orders`
-- ----------------------------
BEGIN;
INSERT INTO `orders` VALUES ('17040649958', '2a0cc4b7ed334287a5cde20840a7c9df', '2017-04-06', null, '100', null, 'Paid', null, '2017-04-06 23:40:53', '2017-04-06 23:40:53'), ('17040652832', '2a0cc4b7ed334287a5cde20840a7c9df', '2017-04-06', null, '100', null, 'Paid', null, '2017-04-06 23:45:00', '2017-04-06 23:45:00'), ('170318909691', '2a0cc4b7ed334287a5cde20840a7c9df', '2017-03-20', '', '100', '', 'Repairing', null, '2017-03-28 11:51:32', '2017-03-18 23:03:54'), ('170328238756', '2a0cc4b7ed334287a5cde20840a7c9df', null, '', '100', '', 'Repairing', null, '2017-03-29 12:44:08', '2017-03-28 12:29:15'), ('170401143370', '2a0cc4b7ed334287a5cde20840a7c9df', null, '', '100', '', 'Repairing', null, '2017-04-04 22:01:43', '2017-04-01 00:17:46'), ('170406395298', '2a0cc4b7ed334287a5cde20840a7c9df', '2017-04-06', null, '100', null, 'Paid', null, '2017-04-06 22:48:53', '2017-04-06 22:48:53'), ('170406861358', '2a0cc4b7ed334287a5cde20840a7c9df', '2017-04-06', null, '100', null, 'Paid', null, '2017-04-06 23:34:01', '2017-04-06 23:34:01');
COMMIT;

-- ----------------------------
--  Table structure for `setting`
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting` (
  `id` varchar(20) NOT NULL,
  `currency_abbreviation` varchar(15) DEFAULT NULL,
  `currency_symbol` varchar(10) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `shop_address` varchar(300) DEFAULT NULL,
  `shop_postcode` varchar(30) DEFAULT NULL,
  `shop_telephone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `setting`
-- ----------------------------
BEGIN;
INSERT INTO `setting` VALUES ('0', 'GBP', '£', 'whzlong@gmail.com', 'Extra Mobile 55 Rathbone place London  W1T 1JS', 'WET', '02074360545');
COMMIT;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(40) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(90) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `postcode` varchar(20) DEFAULT NULL,
  `address` varchar(400) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `state` varchar(20) NOT NULL DEFAULT '0',
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  `role` varchar(50) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `users`
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('208691d2597e4eb085b3f52616c2eeed', '153718687@qq.com', '$2a$10$ARzRx2n9fproUY0tFmisZeNCCySk2lpEkTga.Do1cq2Vh1vkFzojW', 'gavin', 'guo', '', '', '', 'Pending', '1', 'USER', '2017-03-18 23:08:39', '2017-03-18 23:08:39'), ('2a0cc4b7ed334287a5cde20840a7c9df', 'whzlong@163.com', '$2a$10$XJ3xXqzCnOfmIzAkDaY4Sunx797y55gyVjbBEcRhL5Vm40B8Ynz06', 'gavin', 'wu', '', '', '', 'Active', '0', 'ADMIN', '2017-03-09 21:59:19', '2017-03-09 21:59:19'), ('d9e2697b2d864959ab76e51e7dd3039c', 'whzlong@sina.cn', '$2a$10$babcsJUy0Z7xsQsWXNiNZODAa6W3qUlzYwu.1NblZA98GafTMbxVS', 'gavin', 'wu', '', '', '', 'Active', '0', 'USER', '2017-03-19 00:49:35', '2017-03-19 00:49:35');
COMMIT;

-- ----------------------------
--  Table structure for `verifications`
-- ----------------------------
DROP TABLE IF EXISTS `verifications`;
CREATE TABLE `verifications` (
  `verification_code` varchar(40) NOT NULL,
  `user_id` varchar(40) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`verification_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `verifications`
-- ----------------------------
BEGIN;
INSERT INTO `verifications` VALUES ('1c5af6065a9d4cdcaec9a3ea909f0f32', '208691d2597e4eb085b3f52616c2eeed', '2017-03-18 23:08:39'), ('83fd92ddcd384e87beab9687ec35a261', '49ce5c4d95444fd1bc3bc0414eeb485b', '2017-03-18 19:43:31'), ('c24083f94f4d4fa2966e909e77d8090d', 'd9e2697b2d864959ab76e51e7dd3039c', '2017-03-19 00:49:35');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
