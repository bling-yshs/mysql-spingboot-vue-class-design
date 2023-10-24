package com.yshs.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yshs.backend.entity.Department;
import com.yshs.backend.mapper.DepartmentMapper;
import com.yshs.backend.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Override
    public Department getByName(String departmentName) {
        //根据部门名称查询部门
        LambdaQueryWrapper<Department> eq = new LambdaQueryWrapper<Department>().eq(Department::getDeptName, departmentName);
        return this.getOne(eq);
    }
}
