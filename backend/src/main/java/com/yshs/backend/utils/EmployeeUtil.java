package com.yshs.backend.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yshs.backend.entity.Department;
import com.yshs.backend.entity.Employee;
import com.yshs.backend.entity.Position;
import com.yshs.backend.entity.vo.EmployeeVo;
import com.yshs.backend.service.IDepartmentService;
import com.yshs.backend.service.IPositionService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeUtil {
    @Resource
    IPositionService positionService;
    @Resource
    IDepartmentService departmentService;

    public EmployeeVo convertToEmployeeVo(Employee employee) {
        EmployeeVo employeeVo = new EmployeeVo();
        BeanUtils.copyProperties(employee, employeeVo);
        // 获取职位名称
        Integer posId = employee.getPosId();
        Position position = positionService.getById(posId);
        String posName = position.getPosName();
        employeeVo.setPosName(posName);
        // 获取部门名称
        Integer deptId = employee.getDeptId();
        Department department = departmentService.getById(deptId);
        String deptName = department.getDeptName();
        employeeVo.setDeptName(deptName);
        return employeeVo;
    }

    public Employee convertToEmployee(EmployeeVo employeeVo) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeVo, employee);
        LambdaQueryWrapper<Department> deptEq = new LambdaQueryWrapper<Department>().eq(Department::getDeptName, employeeVo.getDeptName());
        Department department = departmentService.getOne(deptEq);
        Integer deptId = department.getDeptId();
        employee.setDeptId(deptId);
        LambdaQueryWrapper<Position> posEq = new LambdaQueryWrapper<Position>().eq(Position::getPosName, employeeVo.getPosName());
        Position position = positionService.getOne(posEq);
        Integer posId = position.getPosId();
        employee.setPosId(posId);
        return employee;
    }

    public Boolean isDeptPosMatch(Employee employee) {
        Integer posId = employee.getPosId();
        Position position = positionService.getById(posId);
        return position.getDeptId().equals(employee.getDeptId());
    }
}