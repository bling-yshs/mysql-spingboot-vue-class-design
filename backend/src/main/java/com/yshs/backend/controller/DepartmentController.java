package com.yshs.backend.controller;

import com.yshs.backend.entity.Department;
import com.yshs.backend.entity.Employee;
import com.yshs.backend.entity.RestBean;
import com.yshs.backend.entity.vo.DepartmentVo;
import com.yshs.backend.service.IDepartmentService;
import com.yshs.backend.service.IEmployeeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2023-09-26
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Resource
    IDepartmentService departmentService;

    @Resource
    IEmployeeService employeeService;

    @GetMapping("/getNameList")
    public RestBean<List<String>> getNameList() {
        List<String> departmentNameList = departmentService.list()
                .stream()
                .map(Department::getDeptName)
                .toList();
        return RestBean.success(departmentNameList);
    }

    @GetMapping("/getList")
    public RestBean<List<DepartmentVo>> getList() {
        List<Department> departmentList = departmentService.list();

        //创建DepartmentVoList
        List<DepartmentVo> departmentVoList = departmentList.stream()
                .map(department -> {
                    DepartmentVo departmentVo = new DepartmentVo();
                    departmentVo.setDeptId(department.getDeptId())
                            .setDeptName(department.getDeptName())
                            .setDeptManagerName(employeeService.getById(department.getDeptManager()).getName());
                    return departmentVo;
                })
                .toList();
        return RestBean.success(departmentVoList);
    }

    //更新，传入DepartmentVo
    @PutMapping("/update")
    public RestBean<String> update(@RequestBody DepartmentVo departmentVo) {
        Department department = departmentService.getById(departmentVo.getDeptId());
        department.setDeptName(departmentVo.getDeptName());
        department.setDeptManager(employeeService.getByName(departmentVo.getDeptManagerName()).getEmpId());
        departmentService.updateById(department);
        return RestBean.success("更新成功");
    }

    //Get,传入部门id，返回部门内所有员工的名称
    @GetMapping("/getDepartmentEmployeeNameList")
    public RestBean<List<String>> getDepartmentEmployeeNameList(@RequestParam Integer deptId) {
        List<String> employeeNameList = employeeService.list()
                .stream()
                .filter(employee -> employee.getDeptId().equals(deptId))
                .map(Employee::getName)
                .toList();
        return RestBean.success(employeeNameList);
    }

    @PostMapping("/add")
    public RestBean<String> add(@RequestBody DepartmentVo departmentVo) {
        Department department = new Department();
        //判断部门编号是否已存在
        Integer deptId = departmentVo.getDeptId();
        long count = departmentService.lambdaQuery().eq(Department::getDeptId, deptId).count();
        if (count > 0) {
            return RestBean.forbidden("该部门编号已存在");
        }
        department.setDeptId(deptId);
        department.setDeptName(departmentVo.getDeptName());
        department.setDeptManager(employeeService.getByName(departmentVo.getDeptManagerName()).getEmpId());
        departmentService.save(department);
        return RestBean.success("添加成功");
    }
}
