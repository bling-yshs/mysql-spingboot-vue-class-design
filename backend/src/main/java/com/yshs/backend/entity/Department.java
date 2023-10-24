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
@TableName("department")
public class Department {

    @TableId("dept_id")
    private Integer deptId;

    @TableField("dept_name")
    private String deptName;

    @TableField("dept_manager")
    private Integer deptManager;
}
