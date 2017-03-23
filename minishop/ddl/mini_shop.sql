/*
 Navicat Premium Data Transfer

 Source Server         : repairsshop
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : localhost
 Source Database       : mini_shop

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : utf-8

 Date: 03/16/2017 16:01:01 PM
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
--  Table structure for `item_categories`
-- ----------------------------
DROP TABLE IF EXISTS `item_categories`;
CREATE TABLE `item_categories` (
  `category_id` varchar(40) NOT NULL,
  `parent_category_id` varchar(40) NOT NULL,
  `name` varchar(50) NOT NULL,
  `order_by` int(11) NOT NULL DEFAULT '0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

BEGIN;
INSERT INTO `setting` VALUES ('0', 'GBP', '£', 'whzlong@gmail.com', '37 Tottenham St, Fitzrovia, London W1T 4RU', 'WET');
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

BEGIN;
INSERT INTO `users` VALUES ('2a0cc4b7ed334287a5cde20840a7c9df', 'whzlong@163.com', '$2a$10$XJ3xXqzCnOfmIzAkDaY4Sunx797y55gyVjbBEcRhL5Vm40B8Ynz06', 'gavin', 'wu', '', '', '', 'Active', '0', 'ADMIN', '2017-03-09 21:59:19', '2017-03-09 21:59:19');
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

SET FOREIGN_KEY_CHECKS = 1;
