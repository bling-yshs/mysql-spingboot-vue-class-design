package com.yshs.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@TableName("employee")
public class Employee {

    @TableId("emp_id")
    private Integer empId;

    @TableField("name")
    private String name;

    @TableField("gender")
    private String gender;

    @TableField("id_card")
    private String idCard;

    @TableField("phone")
    private String phone;

    @TableField("pos_id")
    private Integer posId;

    @TableField("dept_id")
    private Integer deptId;
}
