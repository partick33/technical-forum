/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3308
 Source Schema         : technical_forum

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 18/04/2022 17:53:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `forum_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛id',
  `category_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类id',
  `category_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名字',
  `category_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类url',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_category`(`category_id`) USING BTREE COMMENT '分类id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (8, '7dcad29517544e1abb8267a07dcea3e2', '9b37916c0fb14b5098a34525925f7ca8', '后端', '/backend', 1, '2022-04-15 15:06:26', 'admin', '2022-04-15 15:11:21', 'admin');
INSERT INTO `t_category` VALUES (9, '7dcad29517544e1abb8267a07dcea3e2', 'c1a3326e9e844a4288bf5289064c8de9', '前端', '/frontend', 1, '2022-04-15 15:06:26', 'admin', '2022-04-15 15:11:21', 'admin');
INSERT INTO `t_category` VALUES (10, '2ffda078bd9941cd932a4eb8cc9bec4a', '9e3ed839f9c1413bbde28e1503829882', '博客', '/blog', 0, '2022-04-16 10:49:55', 'admin', '2022-04-16 10:49:55', 'admin');
INSERT INTO `t_category` VALUES (11, '9d48b51d4a474feab234212f7a9b374a', 'fda55590f44544799c158fecdf54e49f', '专栏', '/blogs', 0, '2022-04-16 10:50:49', 'admin', '2022-04-16 10:50:49', 'admin');
INSERT INTO `t_category` VALUES (12, '6a8e211c18404920b330bc87010debca', '6784dbd760a641a497464cf2d929a846', '分类', '', 0, '2022-04-16 10:56:11', 'admin', '2022-04-16 10:56:11', 'admin');

-- ----------------------------
-- Table structure for t_ele_forum
-- ----------------------------
DROP TABLE IF EXISTS `t_ele_forum`;
CREATE TABLE `t_ele_forum`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `forum_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛id',
  `forum_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛名字',
  `forum_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛地址',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_forum`(`forum_id`) USING BTREE COMMENT '论坛id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ele_forum
-- ----------------------------
INSERT INTO `t_ele_forum` VALUES (9, '1a9c24b25ee94fbf8949577fd83d066c', 'csdn', 'https://blog.csdn.net', 1, '2022-04-15 11:55:25', 'admin', '2022-04-15 11:55:25', 'admin');
INSERT INTO `t_ele_forum` VALUES (10, '7dcad29517544e1abb8267a07dcea3e2', '掘金', 'https://juejin.cn', 1, '2022-04-15 11:57:32', 'admin', '2022-04-15 13:18:53', 'admin');
INSERT INTO `t_ele_forum` VALUES (11, '2ffda078bd9941cd932a4eb8cc9bec4a', '开源中国', 'https://www.oschina.net', 0, '2022-04-16 10:47:21', 'admin', '2022-04-16 10:47:21', 'admin');
INSERT INTO `t_ele_forum` VALUES (12, '9d48b51d4a474feab234212f7a9b374a', '思否', 'https://segmentfault.com', 0, '2022-04-16 10:47:54', 'admin', '2022-04-16 10:47:54', 'admin');
INSERT INTO `t_ele_forum` VALUES (13, '6a8e211c18404920b330bc87010debca', '博客园', 'https://www.cnblogs.com', 0, '2022-04-16 10:48:23', 'admin', '2022-04-16 10:48:23', 'admin');

-- ----------------------------
-- Table structure for t_ele_forum_category
-- ----------------------------
DROP TABLE IF EXISTS `t_ele_forum_category`;
CREATE TABLE `t_ele_forum_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `category_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛id',
  `forum_nav_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛分类id',
  `forum_nav` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛分类名字',
  `forum_nav_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛分类地址',
  `final_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '论坛最终地址',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ category`(`category_id`) USING BTREE COMMENT '分类id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 279 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ele_forum_category
-- ----------------------------

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `role_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色id',
  `role_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE COMMENT '角色id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '15d9e311443f435e84dd4b18c5f7497a', 'sys', 0, '2022-04-11 00:24:28', 'admin', '2022-04-11 00:24:28', 'admin');
INSERT INTO `t_role` VALUES (2, '8c2a0833e09a4d179bd74a7eb5786069', 'normal', 0, '2022-04-11 00:24:47', 'admin', '2022-04-11 00:24:47', 'admin');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `user_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `user_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名字',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, '76c0dd343b644143836ecdae9fd70592', 'admin1', '49ba59abbe56e057', 0, '2022-04-11 00:12:59', 'admin', '2022-04-11 00:12:59', 'admin');
INSERT INTO `t_user` VALUES (2, '4eaf7fa261ce43238ad191c5f010a589', 'admin2', '49ba59abbe56e057', 0, '2022-04-11 00:22:04', 'admin', '2022-04-11 00:22:04', 'admin');
INSERT INTO `t_user` VALUES (3, 'b9fa3e846d5d4287a8c68f4c992e37fe', 'admin3', '49ba59abbe56e057', 0, '2022-04-11 00:22:09', 'admin', '2022-04-11 00:22:09', 'admin');
INSERT INTO `t_user` VALUES (4, '8221723ce5be473ea65bb4446892008a', 'admin4', '49ba59abbe56e057', 0, '2022-04-11 00:22:12', 'admin', '2022-04-11 00:22:12', 'admin');
INSERT INTO `t_user` VALUES (5, '6da37c7071bf47a0a6861f16ada0dfef', 'admin5', '49ba59abbe56e057', 0, '2022-04-11 00:22:15', 'admin', '2022-04-11 00:22:15', 'admin');
INSERT INTO `t_user` VALUES (6, '9cda969b52c24d85a55537ffffbd8894', 'admin6', '49ba59abbe56e057', 0, '2022-04-11 00:22:31', 'admin', '2022-04-11 00:22:31', 'admin');
INSERT INTO `t_user` VALUES (7, 'a74ec4e5d3c5474f8c6fe62077f30cd5', 'admin7', '49ba59abbe56e057', 0, '2022-04-11 17:29:16', 'admin', '2022-04-11 17:29:16', 'admin');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `user_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户id',
  `role_id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色id',
  `role_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 0-否 1-是',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`user_id`) USING BTREE COMMENT '用户id索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, '76c0dd343b644143836ecdae9fd70592', '15d9e311443f435e84dd4b18c5f7497a', 'sys', 0, '2022-04-11 17:02:44', 'admin', '2022-04-11 17:02:44', 'admin');
INSERT INTO `t_user_role` VALUES (2, '76c0dd343b644143836ecdae9fd70592', '8c2a0833e09a4d179bd74a7eb5786069', 'normal', 0, '2022-04-11 17:02:44', 'admin', '2022-04-11 17:02:44', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
