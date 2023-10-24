package com.yshs.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yshs.backend.entity.Employee;
import com.yshs.backend.mapper.EmployeeMapper;
import com.yshs.backend.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Override
    public Employee getByName(String deptManagerName) {
        //根据姓名查询员工
        LambdaQueryWrapper<Employee> eq = new LambdaQueryWrapper<Employee>().eq(Employee::getName, deptManagerName);
        return this.getOne(eq);
    }
}
