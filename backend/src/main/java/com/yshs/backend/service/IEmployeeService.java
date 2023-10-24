package com.yshs.backend.service;

import com.yshs.backend.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
public interface IEmployeeService extends IService<Employee> {

    Employee getByName(String deptManagerName);
}
