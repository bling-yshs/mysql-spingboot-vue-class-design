package com.yshs.backend.mapper;

import com.yshs.backend.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    @Select("SHOW COLUMNS FROM employee")
    List<String> getTableColumns();

}
