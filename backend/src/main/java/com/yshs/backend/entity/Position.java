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
@TableName("position")
public class Position {

    @TableId("pos_id")
    private Integer posId;

    @TableField("pos_name")
    private String posName;

    @TableField("dept_id")
    private Integer deptId;
}
