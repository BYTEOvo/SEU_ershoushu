/*
 Navicat Premium Data Transfer

 Source Server         : seu_ershou
 Source Server Type    : MySQL
 Source Server Version : 50737
 Source Host           : localhost:3306
 Source Schema         : seu_ershoushu

 Target Server Type    : MySQL
 Target Server Version : 50737
 File Encoding         : 65001

 Date: 22/05/2022 10:40:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '图书编号',
  `username` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卖家编号\r\n',
  `cid` int(11) UNSIGNED NULL DEFAULT NULL COMMENT '图书类别编号',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `synopsis` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` double(10, 2) NOT NULL,
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `newness` double(10, 1) NULL DEFAULT NULL,
  `checked` tinyint(1) NULL DEFAULT 0,
  `sold` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_book_caterogy_on cid`(`cid`) USING BTREE,
  CONSTRAINT `fk_book_caterogy_on cid` FOREIGN KEY (`cid`) REFERENCES `category` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (3, '3', 5, '约恩•卡尔曼•斯特凡松', '鱼没有脚', '凯夫拉维克被称为冰岛最黑暗的地方，荒芜的熔岩，不能捕鱼的海，赶不走的美国军事基地。\n\n作家和出版商阿里因重病的父亲回到这里，少不更事的回忆汹涌而来，平克•弗洛伊德、偷美军卡车上的物资和一个他忘不掉的女孩。\n\n祖父与祖母是在东峡湾的海面上用勇气和意志拼出了生活的冰岛人，那时古老的风尚仍在流行，男人靠出海带来荣耀和光辉，女人在岸上忍受残酷的等待。祖辈的爱情，对自然的赞美和生命的光荣都深深吸引着阿里。\n\n在这同一片群山和海洋的地方，曾经的荣耀为什么变成只剩利润而非心跳的生命，阿里心中升起无法承受的问号。', 49.80, 'http://localhost:8443/api/file/m6pj3r.jpg', 2.0, 1, 0);
INSERT INTO `book` VALUES (4, '3', 5, '天蚕土豆', '斗破苍穹', '讲述了天才少年萧炎在创造了家族空前绝后的修炼纪录后突然成了废人，种种打击接踵而至。就在他即将绝望的时候，一缕灵魂从他手上的戒指里浮现，一扇全新的大门在面前开启，经过艰苦修炼最终成就辉煌的故事。', 85.40, 'http://localhost:8443/api/file/qwrowb.jpg', 3.0, 1, 0);
INSERT INTO `book` VALUES (5, '3', 1, '唐家三少', '斗罗小路', '《斗罗大陆》讲述的是穿越到斗罗大陆的唐三如何一步步修炼武魂，由人修炼为神，最终铲除了斗罗大陆上的邪恶力量，报了杀母之仇，成为斗罗大陆最强者的故事 [1]  。主要角色有唐三、小舞、戴沐白等。', 15.33, 'http://localhost:8443/api/file/blh7yk.jpg', 4.6, 1, 0);
INSERT INTO `book` VALUES (6, '3', 5, '刘慈欣', '三体', '文化大革命如火如荼地进行，天文学家叶文洁在其间历经劫难，被带到军方绝秘计划“红岸工程”。叶文洁以太阳为天线，向宇宙发出地球文明的第一声啼鸣，取得了探寻外星文明的突破性进展。三颗无规则运行的太阳主导下，四光年外的“三体文明”百余次毁灭与重生，正被逼迫不得不逃离母星，而恰在此时，他们接收到了地球发来的信息。对人性绝望的叶文洁向三体人暴露了地球的坐标，彻底改变了人类的命运。', 99.99, 'http://localhost:8443/api/file/171eye.jpg', 4.7, 1, 0);
INSERT INTO `book` VALUES (10, '3', 3, '龚书铎', '图说中国史', '十五年热销600万册，近一千余则经典历史故事，两千余幅珍贵的文物图片，五百多个应该掌握的历史小知识，带您回顾历史长河。', 42.25, 'http://localhost:8443/api/file/ywttl5.jpg', 1.8, 1, 0);
INSERT INTO `book` VALUES (11, '3', 3, '余华', '兄弟', '该小说讲述了小镇重组家庭中的两兄弟李光头和宋钢在二十世纪六七十年代和改革开放初期所经历的种种磨难。作者以荒诞手法再现历史，是为表现对六七十年代强权的批判，以及对改革开放初期民众精神生活匮乏的担忧和些许的人性关怀。', 85.20, 'http://localhost:8443/api/file/y0a4p4.jpg', 4.9, 1, 0);
INSERT INTO `book` VALUES (12, 'admin', 7, '顾诚', '南明史', '内斗就要亡国，亡国也要内斗！从南明的灭亡，看透人性的荒唐！荣获中国国家图书奖，北京市哲学社会科学优秀成果一等奖。明史大家顾诚代表作，豆瓣9.3分，不可不读的史学经典，读客熊猫君出品。', 64.90, 'http://localhost:8443/api/file/6k3rcd.jpg', 5.0, 1, 0);
INSERT INTO `book` VALUES (17, 'admin', 2, 'iu', 'LILAC', '《LILAC》是韩国女歌手李知恩（IU）的第五张正规专辑，共收录10首歌曲，于2021年3月25日发行', 30.00, 'http://localhost:8443/api/file/wlah4n.jpg', 3.0, 1, 0);
INSERT INTO `book` VALUES (18, '3', 1, '1', '1', '1', 1.00, '', 1.0, 1, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (0, '全部');
INSERT INTO `category` VALUES (1, '文学');
INSERT INTO `category` VALUES (2, '流行');
INSERT INTO `category` VALUES (3, '文化');
INSERT INTO `category` VALUES (4, '生活');
INSERT INTO `category` VALUES (5, '经管');
INSERT INTO `category` VALUES (6, '科技');
INSERT INTO `category` VALUES (7, '教科');

-- ----------------------------
-- Table structure for complaint
-- ----------------------------
DROP TABLE IF EXISTS `complaint`;
CREATE TABLE `complaint`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `oid` int(11) UNSIGNED NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time` datetime(6) NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_oid`(`oid`) USING BTREE,
  CONSTRAINT `fk_oid` FOREIGN KEY (`oid`) REFERENCES `orders` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of complaint
-- ----------------------------
INSERT INTO `complaint` VALUES (3, '1111', 12, '已完成', '2022-05-18 22:01:49.372000', '4');
INSERT INTO `complaint` VALUES (4, '123456', 15, '已完成', '2022-05-20 20:53:02.436000', '5');
INSERT INTO `complaint` VALUES (5, '11', 16, '未通过', '2022-05-20 20:58:07.589000', '3');
INSERT INTO `complaint` VALUES (6, '不需要了', 13, '已通过', '2022-05-20 21:02:59.649000', '4');
INSERT INTO `complaint` VALUES (7, '买错了', 17, '未审核', '2022-05-21 10:04:25.108000', '4');
INSERT INTO `complaint` VALUES (8, '2', 18, '已完成', '2022-05-21 10:05:48.609000', '4');
INSERT INTO `complaint` VALUES (9, '3', 19, '已完成', '2022-05-21 10:14:32.994000', '4');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `begin` datetime(6) NOT NULL,
  `end` datetime(6) NULL DEFAULT NULL,
  `buyer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seller` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bid` int(11) UNSIGNED NULL DEFAULT NULL,
  `receive` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_order_book_on bid`(`bid`) USING BTREE,
  CONSTRAINT `fk_order_book_on bid` FOREIGN KEY (`bid`) REFERENCES `book` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (9, '2022-05-17 18:05:17.846000', NULL, '3', 'admin', 12, '九龙湖校区梅园1', '九龙湖校区', '已完成');
INSERT INTO `orders` VALUES (12, '2022-05-12 21:59:25.000000', '2022-05-20 09:17:05.314000', '4', '3', 11, '1', '九龙湖校区梅园1', '已完成');
INSERT INTO `orders` VALUES (13, '2022-05-20 18:08:06.703000', NULL, '4', '3', 3, '1', '九龙湖桃园', '等待寄回');
INSERT INTO `orders` VALUES (14, '2022-05-20 20:46:52.448000', '2022-05-20 20:49:35.828000', '5', '3', 4, '1', '九龙湖桃园', '已完成');
INSERT INTO `orders` VALUES (15, '2022-05-20 20:52:54.927000', NULL, '5', '3', 10, '1', '九龙湖桃园', '已完成');
INSERT INTO `orders` VALUES (16, '2022-05-20 20:55:29.734000', '2022-05-20 20:58:54.836000', '3', 'admin', 17, '九龙湖桃园', '九龙湖校区', '已完成');
INSERT INTO `orders` VALUES (17, '2022-05-21 10:04:08.699000', NULL, '4', '3', 5, '1', '九龙湖桃园', '已寄出,申诉中');
INSERT INTO `orders` VALUES (18, '2022-05-21 10:05:39.102000', '2022-05-21 10:05:48.633000', '4', '3', 6, '1', '九龙湖桃园', '已完成');
INSERT INTO `orders` VALUES (19, '2022-05-21 10:14:19.356000', '2022-05-21 10:14:33.018000', '4', '3', 18, '1', '九龙湖桃园', '已退款');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phonenumber` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` double(10, 2) NOT NULL DEFAULT 1000.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'qwert', '0c61e7e8d308b3761f7572c150b4427b', '4Z+ZQzGw2f8cFlatHCCBXg==', 'admin', '九龙湖校区', '1381', 1000.00);
INSERT INTO `user` VALUES (5, 'admin', '3bcd75f7f2ee9131eff83a6103461b56', 'G5QDdQGlOqpgNdYoyTCcXw==', 'admin', '九龙湖校区', '1382', 35.00);
INSERT INTO `user` VALUES (6, 'qwe', 'a06c8e4e3a25c7936b3304b35fb7f23b', 'qvRbkX8y29aG0e93J7c4Ng==', 'user', '九龙湖校区', '1383', 1000.00);
INSERT INTO `user` VALUES (7, 'test', '9b45ceebc4fc1025bbfcbce6895af03c', 'bWdamfNmLmVyH9YdtETlJg==', 'user', '九龙湖校区', '1384', 1000.00);
INSERT INTO `user` VALUES (8, 'test1', '988d3d094879262709f3b10b15233ea6', 'BtwGIm3VYNIQAstBqrhuDA==', 'user', '九龙湖校区', '1385', 1000.00);
INSERT INTO `user` VALUES (9, '1', 'd9b41da3a65f9556e4b4ae2ba3e84dee', 't+4j69LeAGda6ysxDBJzcA==', 'user', '九龙湖校区', '1386', 1000.00);
INSERT INTO `user` VALUES (10, '711201', '004a1cad6aade69b280a795f5f1693eb', 'mdy9KxXG7gCXQMRCLXA7jg==', 'user', '九龙湖校区', '1387', 1000.00);
INSERT INTO `user` VALUES (11, '2', 'c3e388bf13b5ab3ea7248f7fec210967', 'I28pmxA98EUElAc0N/cz9A==', 'user', '九龙湖校区', '1388', 1000.00);
INSERT INTO `user` VALUES (12, '3', 'ad031f6f7a1d0592d59f83bea873d08e', 'LuVZdMYvf2uVFQUB1+f8+A==', 'user', '九龙湖桃园', '992736', 1225.80);
INSERT INTO `user` VALUES (15, '4', '345afdfaec2c84d9980f6bd5e932039a', 'URmdaq36h4WyzOI3UUAGLA==', 'user', '1', '1', 877.20);
INSERT INTO `user` VALUES (16, '5', 'cac474b9c7bb03374032c82b989aef0b', 'DNLWHkrMCKt1GWW8CXQyIw==', 'user', '1', 'hugzhubi', 914.60);

SET FOREIGN_KEY_CHECKS = 1;
