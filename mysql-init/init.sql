-- 创建员工信息表
CREATE DATABASE design;
use design;
CREATE TABLE employee
(
    emp_id  INT         NOT NULL, -- 员工编号
    name    VARCHAR(20) NOT NULL, -- 姓名
    gender  VARCHAR(20)  NOT NULL, -- 性别
    id_card VARCHAR(30) NOT NULL, -- 身份证号
    phone   VARCHAR(20) NOT NULL, -- 手机号码
    pos_id  INT         NOT NULL, -- 职位编号
    dept_id INT         NOT NULL, -- 所在部门编号
    PRIMARY KEY (emp_id)          -- 主键约束
);

-- 创建部门信息表
CREATE TABLE department
(
    dept_id      INT         NOT NULL,                      -- 部门编号
    dept_name    VARCHAR(20) NOT NULL,                      -- 部门名称
    dept_manager INT         NOT NULL,                      -- 部门主管编号
    PRIMARY KEY (dept_id),                                  -- 主键约束
    FOREIGN KEY (dept_manager) REFERENCES employee (emp_id) -- 外键约束，引用员工信息表的员工编号
);

-- 创建部门职位表
CREATE TABLE position
(
    pos_id   INT         NOT NULL,                        -- 职位编号
    pos_name VARCHAR(20) NOT NULL,                        -- 职位名称
    dept_id  INT         NOT NULL,                        -- 部门编号
    PRIMARY KEY (pos_id),                                 -- 主键约束
    FOREIGN KEY (dept_id) REFERENCES department (dept_id) -- 外键约束，引用部门信息表的部门编号
);

-- 创建工资表
CREATE TABLE salary
(
    bill_id     INT            NOT NULL,              -- 账单编号
    emp_id      INT            NOT NULL,              -- 员工编号
    base_salary DECIMAL(10, 2) NOT NULL,              -- 基本工资
    insurance   DECIMAL(10, 2) NOT NULL,              -- 五险一金
    bonus       DECIMAL(10, 2) NOT NULL,              -- 绩效奖金
    subsidy     DECIMAL(10, 2) NOT NULL,              -- 补贴
    overtime    DECIMAL(10, 2) NOT NULL,              -- 加班费
    PRIMARY KEY (bill_id),                            -- 主键约束
    FOREIGN KEY (emp_id) REFERENCES employee (emp_id) -- 外键约束，引用员工信息表的员工编号
);


-- 创建登录账号表
CREATE TABLE user
(
    username varchar(20) NOT NULL,
    password varchar(20) NOT NULL,
    PRIMARY KEY (username) -- 主键约束
);


-- 插入员工信息表的数据
INSERT INTO employee
VALUES (1, '张三', '男', '110101199001011234', '13800000001', 1, 1),
       (2, '李四', '女', '110101199001021345', '13800000002', 1, 1),
       (3, '王五', '男', '110101199001031456', '13800000003', 1, 1),
       (4, '赵六', '女', '110101199001041567', '13800000004', 2, 1),
       (5, '孙七', '男', '110101199001051678', '13800000005', 3, 2),
       (6, '周八', '女', '110101199001061789', '13800000006', 3, 2),
       (7, '吴九', '男', '110101199001071890', '13800000007', 3, 2),
       (8, '郑十', '女', '110101199001081901', '13800000008', 4, 2),
       (9, '陈一一', '男', '110101199001091012', '13800000009', 5, 3),
       (10, '林一二', '女', '110101199001101123', '13800000010', 6, 3),
       (11, '张十一', '男', '110101199001111234', '13800000011', 1, 1),
       (12, '李十二', '女', '110101199001121345', '13800000012', 1, 1),
       (13, '王十三', '男', '110101199001131456', '13800000013', 1, 1),
       (14, '赵十四', '女', '110101199001141567', '13800000014', 1, 1),
       (15, '孙十五', '男', '110101199001151678', '13800000015', 1, 1),
       (16, '周十六', '女', '110101199001161789', '13800000016', 1, 1),
       (17, '吴十七', '男', '110101199001171890', '13800000017', 1, 1),
       (18, '郑十八', '女', '110101199001181901', '13800000018', 1, 1),
       (19, '陈十九', '男', '110101199001191012', '13800000019', 1, 1),
       (20, '林二十', '女', '110101199001201123', '13800000020', 1, 1);

INSERT INTO department
VALUES (1, '研发部', 4),
       (2, '销售部', 8),
       (3, '人事部', 10);

INSERT INTO position
VALUES (1, '研发工程师', 1),
       (2, '研发主管', 1),
       (3, '销售专员', 2),
       (4, '销售主管', 2),
       (5, '人事专员', 3),
       (6, '人事主管', 3);

INSERT INTO salary
VALUES (1, 1, 8000.00, 1200.00, 500.00, 300.00, 200.00),
       (2, 2, 7500.00, 1100.00, 450.00, 250.00, 150.00),
       (3, 3, 7000.00, 1000.00, 400.00, 200.00, 100.00),
       (4, 4, 9000.00, 1300.00, 550.00, 350.00, 250.00),
       (5, 5, 6000.00, 900.00, 350.00, 150.00, 50.00),
       (6, 6, 5500.00, 800.00, 300.00, 100.00, 0.00),
       (7, 7, 5000.00, 700.00, 250.00, 50.00, 0.00),
       (8, 8, 8500.00, 1200.00, 500.00, 300.00, 200.00),
       (9, 9, 6500.00, 1000.00, 400.00, 200.00, 100.00),
       (10, 10, 7000.00, 1100.00, 450.00, 250.00, 150.00);

INSERT INTO user
VALUES ('admin', 'password'),
       ('user', 'password');