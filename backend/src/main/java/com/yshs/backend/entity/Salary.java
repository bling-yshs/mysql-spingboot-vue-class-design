package com.yshs.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;

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
@TableName("salary")
public class Salary {

    @TableId("bill_id")
    private Integer billId;

    @TableField("emp_id")
    private Integer empId;

    @TableField("base_salary")
    private BigDecimal baseSalary;

    @TableField("insurance")
    private BigDecimal insurance;

    @TableField("bonus")
    private BigDecimal bonus;

    @TableField("subsidy")
    private BigDecimal subsidy;

    @TableField("overtime")
    private BigDecimal overtime;
}
