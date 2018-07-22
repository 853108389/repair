/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : campus_repair

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-09-16 12:34:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `image_paths`
-- ----------------------------
DROP TABLE IF EXISTS `image_paths`;
CREATE TABLE `image_paths` (
  `img_file_id` varchar(11) NOT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`img_file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of image_paths
-- ----------------------------

-- ----------------------------
-- Table structure for `repair_info`
-- ----------------------------
DROP TABLE IF EXISTS `repair_info`;
CREATE TABLE `repair_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `thing` varchar(255) DEFAULT NULL,
  `describes` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `userName` varchar(255) NOT NULL,
  `orders` varchar(255) DEFAULT NULL,
  `img_file_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of repair_info
-- ----------------------------
INSERT INTO `repair_info` VALUES ('1', '电灯', '不亮', '第一食堂', '年糕', '17805005900', '2017-08-29 21:02:37', '未维修', '118.934346', '32.118428', '17805005900', '20170925178708', '20170914162126');
INSERT INTO `repair_info` VALUES ('2', '床', '塌了', '菊苑#21-601宿舍', '年糕', '17805005900', '2017-09-14 11:48:54', '维修中', '120.934346', '32.118428', '17805005900', '20170925121778', '20170912162126');
INSERT INTO `repair_info` VALUES ('3', '床', '栏杆掉了', '竹苑#14-502宿舍', '年糕', '17805005900', '2017-09-15 15:02:20', '未维修', '120.934346', '32.118428', 'admin', '20170925121708', '20170912162126');
INSERT INTO `repair_info` VALUES ('4', '空调', '排水管坏了', '竹苑#21-400宿舍', '年糕', '17805005900', '2017-08-10 15:01:36', '未维修', '120.934346', '32.118428', 'a', '20170921121741', '20170915162126');
INSERT INTO `repair_info` VALUES ('5', '电灯', '坏了', '教5-312', '年糕', '17805005900', '2017-09-20 15:07:33', '未维修', '120.934346', '32.118428', 'a', '20170925125741', '20170915162120');
INSERT INTO `repair_info` VALUES ('6', '电灯', '炸了', '教5-401', '年糕', '17805005900', '2017-09-20 15:07:33', '未维修', '118.929161', '32.11367', 'guest', '20170925121741', '20170915164448');
INSERT INTO `repair_info` VALUES ('7', '圈存机', '无法存钱', '第三食堂', '年糕', '17805005900', '2017-09-20 15:07:33', '未维修', '118.929161', '32.11367', 'guest', '20170915121741', '20170915162122');
INSERT INTO `repair_info` VALUES ('8', '电灯', '灭了', '竹苑#14-501宿舍', '年糕', '17805005900', '2017-09-20 15:07:33', '未维修', '118.929161', '32.11367', 'guest', '20170915161741', '20170915162111');
INSERT INTO `repair_info` VALUES ('9', '电灯', '不亮了', '第三食堂', '年糕', '17805005900', '2017-09-13 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '20170915161748', '20170915161748');
INSERT INTO `repair_info` VALUES ('10', '电灯', '灭了', '竹苑#14-505宿舍', '年糕', '17805005900', '2017-09-15 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '20170915162143', '20170915162143');
INSERT INTO `repair_info` VALUES ('11', '电灯', '一直亮', '地生学科楼213', '年糕', '17805005900', '2017-09-28 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '20170915162143', '20170915162143');
INSERT INTO `repair_info` VALUES ('12', '电灯', '灭了', '第三食堂', '年糕', '17805005900', '2017-09-15 19:48:04', '维修中', '118.929161', '32.11367', 'guest', '20170915162143', '20170915162143');
INSERT INTO `repair_info` VALUES ('13', '风扇', '不能转了', '竹苑#14-500宿舍', '年糕', '17805005900', '2017-09-15 19:48:04', '已维修', '118.929161', '32.11367', 'guest', '20170915162818', '20170915162818');
INSERT INTO `repair_info` VALUES ('14', '桌椅', '丢了', '计算机软件学科楼#321', '年糕', '17805005900', '2017-09-14 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '201709158657096', '20170915162178');
INSERT INTO `repair_info` VALUES ('15', '插座', '坏了', '第二食堂', '年糕', '17805005900', '2017-09-29 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '201709158637096', '20170917762126');
INSERT INTO `repair_info` VALUES ('16', '插座', '不好使', '梅苑#14-200宿舍', '年糕', '17805005900', '2017-09-15 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '201709158637086', '20170915192126');
INSERT INTO `repair_info` VALUES ('17', '空调', '不能制冷', '竹苑#14-500宿舍', '年糕', '17805005900', '2017-09-09 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '201709151637086', '20170915192126');
INSERT INTO `repair_info` VALUES ('18', '插座', '坏了', '第一食堂', '年糕', '13192648737', '2017-09-22 19:48:04', '已维修', '118.929161', '32.11367', 'guest', '20170915163706', '20170915163706');
INSERT INTO `repair_info` VALUES ('19', '插座', '进水了', '图书馆一楼', '年糕', '13192648737', '2017-09-15 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '20170915164031', '20170915164031');
INSERT INTO `repair_info` VALUES ('20', '刷卡机', '不能刷卡', '第一食堂', '我', '13192648737', '2017-09-06 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '20170915193906', '20170915193906');
INSERT INTO `repair_info` VALUES ('21', '插座', '坏了', '大学生服务中心', '回家', '12131648794', '2017-09-15 19:48:04', '未维修', '118.929161', '32.11367', 'guest', '20170915194804', '20170915194804');
INSERT INTO `repair_info` VALUES ('22', '圈存机', '无法存钱', '第三食堂', '年糕', '12134645727', '2017-09-15 20:20:46', '已维修', '118.999999', '32.11367', 'a', '20170915202046', '20170915202046');
INSERT INTO `repair_info` VALUES ('23', '桌椅', '桌子坏了', '第一食堂二楼', '我', '13196253701', '2017-09-16 11:22:52', '未维修', '118.935789', '32.115348', 'a', '20170916112252', '20170916112252');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', 'admin');
INSERT INTO `user` VALUES ('2', '17805005900', '123456');
INSERT INTO `user` VALUES ('3', '15948998571', '123456');
INSERT INTO `user` VALUES ('4', '15948998570', '123456');
INSERT INTO `user` VALUES ('5', '13705005900', '1111');
INSERT INTO `user` VALUES ('6', 'a', 'a');
INSERT INTO `user` VALUES ('7', 'guest', 'guest');
