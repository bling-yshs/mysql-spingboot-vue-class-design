package com.yshs.backend.mapper;

import com.yshs.backend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-10-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
