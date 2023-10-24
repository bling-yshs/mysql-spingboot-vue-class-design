package com.yshs.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2023-10-03
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("user")
public class User {

    @TableId("username")
    private String username;

    @TableField("password")
    private String password;
}
