package com.yshs.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class EmployeeVo {

    private Integer empId;

    private String name;

    private String gender;

    private String idCard;

    private String phone;

    /**
     * 职位名称
     */
    private String posName;

    /**
     * 部门名称
     */
    private String deptName;
}
