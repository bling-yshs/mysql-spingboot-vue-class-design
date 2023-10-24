package com.yshs.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yshs.backend.entity.Employee;
import com.yshs.backend.entity.RestBean;
import com.yshs.backend.entity.Salary;
import com.yshs.backend.entity.vo.EmployeeVo;
import com.yshs.backend.mapper.EmployeeMapper;
import com.yshs.backend.service.IEmployeeService;
import com.yshs.backend.service.ISalaryService;
import com.yshs.backend.utils.EmployeeUtil;
import com.yshs.backend.utils.NameStyleConversionUtil;
import com.yshs.backend.utils.checkFieldUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/api/employee")
public class EmployeeController {

    @Resource
    EmployeeUtil employeeUtil;

    //@Resource
    //IDepartmentService departmentService;
    //
    //@Resource
    //IPositionService positionService;
    @Resource
    ISalaryService salaryService;

    @Resource
    NameStyleConversionUtil nameStyleUtil;
    @Resource
    IEmployeeService employeeService;

    @Resource
    EmployeeMapper employeeMapper;

    /**
     * 将employee转换成employeeVo
     */
    @GetMapping("/getList")
    public RestBean<List<EmployeeVo>> getList(@RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        List<EmployeeVo> employeeVoList = new ArrayList<>();
        List<Employee> employeeList;
        if (current == null || size == null) {
            employeeList = employeeService.list();
        } else {
            Page<Employee> page = employeeService.page(Page.of(current, size));
            employeeList = page.getRecords();
        }
        for (Employee employee : employeeList) {
            EmployeeVo employeeVo = employeeUtil.convertToEmployeeVo(employee);
            employeeVoList.add(employeeVo);
        }
        return RestBean.success(employeeVoList);
    }


    @GetMapping("/getSpecifiedList")
    public RestBean<List<EmployeeVo>> getSpecifiedList(@RequestParam String field, @RequestParam String value) {
        if (!checkFieldUtil.isValidField(Employee.class, field)) {
            return RestBean.forbidden("数据库中不存在该字段");
        }
        String newField = nameStyleUtil.convertToUnderscoreCase(field);
        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>().eq(newField, value);
        List<Employee> employeeList = employeeService.list(wrapper);
        if (employeeList.isEmpty()) {
            return RestBean.notfound("未从数据库找到对应内容");
        }
        List<EmployeeVo> employeeVoList = new ArrayList<>();
        for (Employee employee : employeeList) {
            EmployeeVo employeeVo = employeeUtil.convertToEmployeeVo(employee);
            employeeVoList.add(employeeVo);
        }
        return RestBean.success(employeeVoList);
    }

    @GetMapping("/getTotalNumber")
    public RestBean<Long> getTotal() {
        long count = employeeService.count();
        return RestBean.success(count);
    }

    @GetMapping("/getTableColumns")
    public RestBean<List<String>> getTableColumns() {
        List<String> tableColumns = employeeMapper.getTableColumns();
        List<String> res = nameStyleUtil.convertToCamelCase(tableColumns);
        return RestBean.success(res);
    }

    @PutMapping("/update")
    public RestBean<String> update(@RequestBody EmployeeVo employeeVo) {
        Employee employee = employeeUtil.convertToEmployee(employeeVo);
        if (!employeeUtil.isDeptPosMatch(employee)) {
            return RestBean.forbidden("职位和部门不匹配");
        }
        boolean b = employeeService.updateById(employee);
        if (b) {
            return RestBean.success();
        } else {
            return RestBean.forbidden("无法更新数据");
        }
    }

    @DeleteMapping("/deleteById")
    public RestBean<String> deleteById(@RequestParam Integer empId) {
        LambdaQueryWrapper<Salary> eq = new LambdaQueryWrapper<Salary>().eq(Salary::getEmpId, empId);
        salaryService.remove(eq);
        boolean b = employeeService.removeById(empId);
        if (b) {
            return RestBean.success();
        } else {
            return RestBean.forbidden("无法删除数据");
        }
    }

    @PostMapping("/add")
    public RestBean<String> add(@RequestBody EmployeeVo employeeVo) {
        Integer receiveId = employeeVo.getEmpId();
        LambdaQueryWrapper<Employee> eq = new LambdaQueryWrapper<Employee>().eq(Employee::getEmpId, receiveId);
        long count = employeeService.count(eq);
        if (count > 0) {
            return RestBean.forbidden("该员工号已存在");
        }
        Employee employee = employeeUtil.convertToEmployee(employeeVo);
        if (!employeeUtil.isDeptPosMatch(employee)){
            return RestBean.forbidden("职位和部门不匹配");
        }
        boolean b = employeeService.save(employee);
        if (b) {
            return RestBean.success();
        } else {
            return RestBean.forbidden("未知错误");
        }
    }

    //获取所有员工的名字
    @GetMapping("/getAllEmployeeNameList")
    public RestBean<List<String>> getAllEmployeeNameList(){
        List<Employee> employeeList = employeeService.list();
        List<String> list = employeeList
                .stream()
                .map(Employee::getName)
                .toList();
        return RestBean.success(list);
    }
}
