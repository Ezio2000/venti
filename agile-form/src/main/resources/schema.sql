CREATE TABLE guarantee (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    guarantee_number VARCHAR(50) NOT NULL COMMENT '保函编号，唯一标识',
    beneficiary VARCHAR(100) NOT NULL COMMENT '受益人',
    guaranteed_party VARCHAR(100) NOT NULL COMMENT '被担保人',
    project_name VARCHAR(200) NOT NULL COMMENT '项目名称',
    guarantee_amount DECIMAL(15,2) NOT NULL COMMENT '担保金额',
    guarantee_deadline DATETIME NOT NULL COMMENT '担保截止日期',
    guarantor VARCHAR(100) NOT NULL COMMENT '担保人',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_guarantee_number (guarantee_number) COMMENT '保函编号唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保函信息表';

CREATE TABLE guarantee_verification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    guarantee_number VARCHAR(50) NOT NULL COMMENT '保函编号',
    security_code VARCHAR(70) NOT NULL COMMENT '防伪码',
    status TINYINT(1) DEFAULT 1 NOT NULL COMMENT '状态：1-有效，0-无效',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_security_code (security_code) COMMENT '防伪码唯一索引',
    UNIQUE KEY uk_guarantee_number (guarantee_number) COMMENT '保函编号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保函查询验证表';