package com.yshs.backend.service;

import com.yshs.backend.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
public interface IDepartmentService extends IService<Department> {

    Department getByName(String departmentName);
}
