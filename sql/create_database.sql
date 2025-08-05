# 数据库初始化
# @author xucheng
# @since 2025-7-8

-- 创建数据库
CREATE DATABASE IF NOT EXISTS xcoj DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE xcoj;

-- 用户表
CREATE TABLE user (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户id',
    username VARCHAR(256) NOT NULL COMMENT '用户名',
    password VARCHAR(512) NOT NULL COMMENT '用户密码(加密后)',
    userRole VARCHAR(256) DEFAULT 'user' COMMENT '用户权限',
    acceptedNum INT DEFAULT 0 COMMENT '用户通过题目数',
    submitNum INT DEFAULT 0 COMMENT '用户提交题目数',
    createTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '用户创建时间',
    isDelete TINYINT DEFAULT 0 COMMENT '是否删除',
    UNIQUE KEY uq_user_name (username)
) COMMENT='用户表';

-- 题目表
CREATE TABLE problem (
    problemId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '题目id',
    title VARCHAR(512) NOT NULL COMMENT '题目标题',
    content TEXT NOT NULL COMMENT '题目描述',
    solution TEXT COMMENT '题解',
    tags varchar(1024) COMMENT '题目标签',
    timeLimit int NOT NULL COMMENT '题目时间限制(MS)',
    memoryLimit int NOT NULL COMMENT '空间限制(KB)',
    judgeCase text NOT NULL COMMENT '判题数据(json)',
    acceptedNum INT DEFAULT 0 COMMENT '通过数',
    submitNum INT DEFAULT 0 COMMENT '提交数',
    userId BIGINT NOT NULL COMMENT '创建人id',
    createTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updateTime DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    isHidden TINYINT DEFAULT 1 COMMENT '是否隐藏',
    isDelete TINYINT DEFAULT 0 COMMENT '是否删除',
    INDEX idx_problem (userId)
) COMMENT='题目表';

-- 提交记录表
CREATE TABLE submission (
    submissionId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '提交id',
    userId BIGINT NOT NULL COMMENT '用户id',
    problemId BIGINT NOT NULL COMMENT '题目id',
    lang varchar(64) NOT NULL COMMENT '编程语言',
    code TEXT NOT NULL COMMENT '提交代码',
    judgeResult VARCHAR(256) DEFAULT 'In queue' COMMENT '判题结果',
    usedTime INT DEFAULT 0 COMMENT '时间使用',
    usedMemory INT DEFAULT 0 COMMENT '空间使用',
    createTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    isDelete TINYINT DEFAULT 0 COMMENT '是否删除',
    INDEX idx_userId (userId),
    INDEX idx_problemId (problemId)
) COMMENT='提交记录表';

-- 用户通过表
CREATE TABLE user_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    userId BIGINT NOT NULL COMMENT '用户id',
    problemId BIGINT NOT NULL COMMENT '题目id',
    isAc TINYINT NOT NULL COMMENT '是否通过',
    createTime DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_userId (userId),
    INDEX idx_problemId (problemId),
    UNIQUE KEY uq_user_problem (userId, problemId)
) COMMENT='用户通过题目表';